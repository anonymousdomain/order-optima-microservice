package et.com.gebeya.inventory_management.config;

import et.com.gebeya.inventory_management.security.RoleHeaderAuthenticationFilter;
import et.com.gebeya.inventory_management.security.RoleHeaderAuthenticationProvider;
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
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/api/v1/products/*/stock",
            "/api/v1/products/category/*",
            "/api/v1/products/search",
            "/api/v1/products/byIds",
            "/api/v1/products/all",
            "/api/v1/products/get/*"

    };
    protected static final String [] SYSTEM_MATCHERS = {

            "/api/v1/products/get/{id}",
            "/api/v1/products/order",
            "/api/v1/products/{productId}/stocK",
    };

    protected static final String [] VENDOR_MATCHERS = {
            "/api/v1/vendor/request/ask",
            "/api/v1/mpesa/payment/register",
            "/api/v1/mpesa/payment/balance/*",
            "/api/v1/mpesa/payment/update/*"
    };
    protected static final String [] RESTAURANT_MATCHERS ={
            "/api/v1/products/order",
            "/api/v1/category/all",
            "/api/v1/products/search",
            "/api/v1/products/all",
            "/api/v1/products/get/*",
            "/api/v1/products/category/*",
            "/api/v1/products/*/stock",
            "/api/v1/category/get/*",
            "/api/v1/category/all"

    };
    protected static final String [] ADMIN_MATCHERS = {
            "/api/v1/products/**",
            "/api/v1/category/**",
            "/api/v1/vendor/request/**",
            "/api/v1/mpesa/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers(UNAUTHORIZED_MATCHERS).permitAll())
                .authorizeHttpRequests(request -> request.requestMatchers(SYSTEM_MATCHERS).hasAuthority("SYSTEM"))
                .authorizeHttpRequests(request -> request.requestMatchers(VENDOR_MATCHERS).hasAuthority("VENDOR"))
                .authorizeHttpRequests(request -> request.requestMatchers(RESTAURANT_MATCHERS).hasAuthority("RESTAURANT"))
                .authorizeHttpRequests(request -> request.requestMatchers(ADMIN_MATCHERS).hasAuthority("ADMIN"))

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
        //System.out.println("unauthorized");
        return (request, response, authException) ->
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        //System.out.println("unauthorized");
        return (request, response, accessDeniedException) ->
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
