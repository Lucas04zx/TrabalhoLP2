package model;

public class User {
    private int id;
    private String nome;
    private UserGender sexo; // enum 'M' ou 'F'
    private String email;
    private String senha;

    public User(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public UserGender getSexo() {
        return sexo;
    }

    public void setSexo(UserGender sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    // Validação
    public void validate() {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do usuário não pode ser vazio.");
        }

        if (sexo == null) {
            throw new IllegalArgumentException("O sexo do usuário é inválido.");
        }

        if (email == null || email.isBlank() || !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("O email do usuário é inválido.");
        }

        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("A senha do usuário não pode ser vazia.");
        }
    }

    @Override
    public String toString() {
        return nome;
    }
}
