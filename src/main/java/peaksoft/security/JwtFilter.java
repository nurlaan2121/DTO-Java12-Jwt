package peaksoft.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import peaksoft.model.User;
import peaksoft.repository.UserRepository;

import java.io.IOException;

/**
 * @author Mukhammed Asantegin
 */

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String headerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        System.out.println("headerToken = " + headerToken);

        String bearer = "Bearer ";
        if (headerToken != null && headerToken.startsWith(bearer)) {
            String token = headerToken.substring(bearer.length());

            try {
                String email = jwtService.verifyToken(token);
                User user = userRepository.getByEmail(email);

                SecurityContextHolder.getContext()
                        .setAuthentication(
                                new UsernamePasswordAuthenticationToken(
                                        user.getEmail(),
                                        null,
                                        user.getAuthorities()
                                )
                        );

            } catch (JWTVerificationException e) {
                response.sendError(400);
            }

        }

        filterChain.doFilter(request, response);
    }
}