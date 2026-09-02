package dev.jorge.projects.gossipuerj.Component;

import dev.jorge.projects.gossipuerj.config.JWTUserData;
import dev.jorge.projects.gossipuerj.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final AuthService authService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authorizeHeader = request.getHeader("Authorization");
        if(Strings.isNotEmpty(authorizeHeader) && authorizeHeader.startsWith("Bearer ")){
            String token = authorizeHeader.substring("Bearer ".length());
            Optional<JWTUserData> optUser = authService.validateSessionToken(token);
            if(optUser.isPresent()) {
                JWTUserData userData = optUser.get();

                List<SimpleGrantedAuthority> authorities = userData.roles().stream().map(SimpleGrantedAuthority::new).toList();

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userData, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        }
        else {
            filterChain.doFilter(request, response);
        }
    }
}