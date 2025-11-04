package model;

import java.math.BigDecimal;

public class Produtos {
    private int id;
    private String nome;
    private String descricao;
    private BigDecimal preco;

    // Construtor padrão
    public Produtos() {}

    // Construtor com id
    public Produtos(int id) {
        this.id = id;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    // Validação dos campos
    public void validate() {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do produto não pode ser vazio.");
        }
        if (preco == null || preco.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O preço do produto deve ser maior ou igual a zero.");
        }
    }

    @Override
    public String toString() {
        return nome;
    }
}
