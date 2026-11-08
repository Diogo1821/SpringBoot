package br.senac.tads.dsw.dados_pessoais.seguranca;

import br.senac.tads.dsw.dados_pessoais.entidade.RoleEntity;
import br.senac.tads.dsw.dados_pessoais.entidade.UsuarioSistemaEntity;
import br.senac.tads.dsw.dados_pessoais.repositorio.UsuarioSistemaRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioSistemaService implements UserDetailsService {
    
    // 1. Injeta o repositório no construtor (e remove o mapa em memória)
    private final UsuarioSistemaRepository usuarioSistemaRepository;

    public UsuarioSistemaService(UsuarioSistemaRepository usuarioSistemaRepository) {
        this.usuarioSistemaRepository = usuarioSistemaRepository;
    }

    // Chamado pelo Spring Security quando precisa verificar as credenciais de um usuário.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // 2. Busca a entidade pelo username e lança exceção se não encontrar
        UsuarioSistemaEntity entity = usuarioSistemaRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        // 3. lista de SimpleGrantedAuthority
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (entity.getCargos() != null) {
            for (RoleEntity role : entity.getCargos()) {
                // Convertendo cada RoleEntity para SimpleGrantedAuthority
                authorities.add(new SimpleGrantedAuthority(role.getNome()));
            }
        }

        return new UsuarioSistema(
                entity.getUsername(),
                entity.getSenha(),
                authorities
        );
    }
}