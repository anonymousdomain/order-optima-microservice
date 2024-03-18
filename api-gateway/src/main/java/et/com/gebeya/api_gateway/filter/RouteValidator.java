package et.com.gebeya.api_gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/api/v1/auth/**",
            "/eureka",
            "/v3/api-docs",
            "/swagger-ui/index.html#/**",
            "/api/v1/user/vendor/register",
            "/api/v1/user/restaurant/register",
            "/api/v1/user/vendor/{id}/products",
            "/api/v1/products/all",
            "/api/v1/products/get/**"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}