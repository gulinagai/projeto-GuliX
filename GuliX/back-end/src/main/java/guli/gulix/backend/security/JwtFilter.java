package guli.gulix.backend.security;

import guli.gulix.backend.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);

            if (jwtUtil.validarToken(token)) {

                String email = jwtUtil.extrairEmail(token);

                var usuario = usuarioRepository.findByEmail(email).orElse(null);

                if (usuario != null) {

                    var authorities = List.of(
                            new SimpleGrantedAuthority(usuario.getRole().name())
                    );

                    var auth = new UsernamePasswordAuthenticationToken(
                            usuario,                     // principal
                            null,                        // senha (geralmente null depois do login)
                            authorities                  // roles/permissões
                    );

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
// Dentro do Spring, agora pode acessar:
//
// authentication.getPrincipal()
//
// e isso retorna:
//
// Usuario

// Por isso isso funciona:
// @PreAuthorize("... #usuarioId == authentication.principal.id")