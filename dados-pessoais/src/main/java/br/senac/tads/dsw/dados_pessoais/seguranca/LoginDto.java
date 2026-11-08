package br.senac.tads.dsw.dados_pessoais.seguranca;

// Representa o corpo da requisição POST /login
public class LoginDto {

    private String username;
    private String password;

    public LoginDto() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
