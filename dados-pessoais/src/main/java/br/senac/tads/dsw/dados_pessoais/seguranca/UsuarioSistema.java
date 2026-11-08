package br.senac.tads.dsw.dados_pessoais.seguranca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// IMPORTANTE: Adicionar os imports do Jackson
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

// Representa um usuário no contexto do Spring Security.
public class UsuarioSistema implements UserDetails {
    
    private static final long serialVersionUID = 1L;

    private final String username;
    private final String password; 
    private List<SimpleGrantedAuthority> authorities; 

    public UsuarioSistema(String username, String password, List<SimpleGrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // Renomeia o campo no JSON de "password" para "senhaCodificada"
    @Override
    @JsonProperty("senhaCodificada")
    public String getPassword() {
        return password;
    }

    // Ignora o método original para não gerar uma lista de objetos [{"authority": "..."}]
    @Override
    @JsonIgnore 
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    // Método customizado para gerar a lista apenas com as Strings ("ROLE_ADMIN", etc)
    @JsonProperty("roles")
    public List<String> getRoles() {
        List<String> rolesStr = new ArrayList<>();
        if (authorities != null) {
            for (GrantedAuthority auth : authorities) {
                rolesStr.add(auth.getAuthority());
            }
        }
        return rolesStr;
    }

    // Os quatro métodos abaixo são ignorados pelo Jackson com @JsonIgnore
    // Assim, eles não aparecerão na resposta da API
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true; 
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true; 
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true; 
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true; 
    }
}