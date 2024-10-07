package model;

public class Produto {
    
    private int id_produto;
    private String nome;
    private String descricao;
    private double preco;
    private int quatidade_estoque;
    private String categoria;
    
    public Produto(String nome, String descricao, double preco, int quatidade_estoque,
            String categoria)
            {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quatidade_estoque = quatidade_estoque;
        this.categoria = categoria;
    }

    public Produto() {

        this.nome = "";
        this.descricao = "";
        this.preco = 0;
        this.quatidade_estoque = 0;
        this.categoria = "";
        
    }

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuatidade_estoque() {
        return quatidade_estoque;
    }

    public void setQuatidade_estoque(int quatidade_estoque) {
        this.quatidade_estoque = quatidade_estoque;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    

}
