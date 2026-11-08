package br.senac.tads.dsw.dados_pessoais.entidade;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id; 
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity 
@Table(name = "tb_usuarios_sistema")
public class UsuarioSistemaEntity {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer usuarioSistema_id;

    @Column(nullable = false, unique = true, length = 64)
    private String username;

    @Column (nullable = false, length = 255)
    private String senha;

    

    public Integer getUsuarioSistema_id() {
        return usuarioSistema_id;
    }



    public void setUsuarioSistema_id(Integer usuarioSistema_id) {
        this.usuarioSistema_id = usuarioSistema_id;
    }



    public String getUsername() {
        return username;
    }



    public void setUsername(String username) {
        this.username = username;
    }



    public String getSenha() {
        return senha;
    }



    public void setSenha(String senha) {
        this.senha = senha;
    }



    public Set<RoleEntity> getCargos() {
        return cargos;
    }



    public void setCargos(Set<RoleEntity> cargos) {
        this.cargos = cargos;
    }



    @ManyToMany(
        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        fetch = FetchType.EAGER
    )
    @JoinTable(
        name = "tb_usuarios_roles",                          
        joinColumns = @JoinColumn(name = "usuarioSistema_id"),             
        inverseJoinColumns = @JoinColumn(name = "role_id")  
    )
    private Set<RoleEntity> cargos = new HashSet<>();
}
