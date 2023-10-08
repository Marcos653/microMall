package authentication.modules.authentication.service.impl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenExtractionService tokenExtractionService;
    private final UserAuthenticationService userAuthenticationService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt = tokenExtractionService.extractToken(authHeader);

        if (jwt != null) {
            String userEmail = jwtService.extractUserName(jwt);
            userAuthenticationService.authenticateUser(jwt, userEmail, request);
        }

        filterChain.doFilter(request, response);
    }
}

