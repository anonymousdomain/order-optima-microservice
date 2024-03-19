package et.com.gebeya.user_service.config;



import et.com.gebeya.user_service.security.RoleHeaderAuthenticationFilter;
import et.com.gebeya.user_service.security.RoleHeaderAuthenticationProvider;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class SecurityConfig {

    protected static final String [] UNAUTHORIZED_MATCHERS = {
            "/api/v1/user/restaurant/register",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/api/auth/log_in",
            "/api/v1/user/vendor/register",
            "/api/v1/user/vendor/{id}/products",
            "/api/v1/user/vendor/request/**"
          

    };

    protected static final String [] ADMIN_MATCHERS = {
           
            "/api/v1/user/restaurant/approve/*",
            "/api/v1/user/restaurant/get/*",
             "/api/v1/user/restaurant/search",
             "/api/v1/user/restaurant/all",
            "/api/v1/user/vendor/**",
            "api/admin/users/**"




    };

    protected static final String [] RESTAURANT_MATCHERS = {
            "/api/v1/user/restaurant/get/*",
            "/api/v1/user/restaurant/delete/*",
    };

    protected static final String [] VENDOR_MATCHERS = {
            "/api/v1/vendor/update",
            "/api/v1/products/**"

    };





    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers(UNAUTHORIZED_MATCHERS).permitAll())
                 .authorizeHttpRequests(request -> request.requestMatchers(RESTAURANT_MATCHERS).hasAuthority("RESTAURANT"))
                .authorizeHttpRequests(request -> request.requestMatchers(ADMIN_MATCHERS).hasAuthority("ADMIN"))
               
                .authorizeHttpRequests(request -> request.requestMatchers(VENDOR_MATCHERS).hasAuthority("VENDOR"))
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .exceptionHandling(handling -> {
                    handling.authenticationEntryPoint(unauthorizedEntryPoint());
                    handling.accessDeniedHandler(accessDeniedHandler());

                })
                .addFilterBefore(new RoleHeaderAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new RoleHeaderAuthenticationProvider();
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {

        return (request, response, authException) ->
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {

        return (request, response, accessDeniedException) ->
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
