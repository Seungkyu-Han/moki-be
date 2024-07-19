package moki.manager.config.jwt;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authorization != null && authorization.startsWith("Bearer ")){
            String token = authorization.split(" ")[1];
            if(jwtTokenProvider.isAccessToken(token)){
                Integer userId = jwtTokenProvider.getUserId(token);
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority("USER"))));
            }
        }

        filterChain.doFilter(request, response);

    }
}