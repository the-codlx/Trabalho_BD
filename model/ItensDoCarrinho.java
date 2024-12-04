package model;

import org.bson.types.ObjectId;

public class ItensDoCarrinho {
    
    private int id_itens_do_carrinho;
    private ObjectId id_carrinho;
    private ObjectId id_produto;
    private int quantidade;

    public ItensDoCarrinho(ObjectId id_carrinho, ObjectId id_produto, int quantidade) 
    {

        this.id_carrinho = id_carrinho;
        this.id_produto = id_produto; 
        this.quantidade = quantidade;

    }

    public int getId_itens_do_carrinho() {
        return id_itens_do_carrinho;
    }

    public void setId_itens_do_carrinho(int id_itens_do_carrinho) {
        this.id_itens_do_carrinho = id_itens_do_carrinho;
    }

    public ObjectId getId_carrinho() {
        return id_carrinho;
    }

    public void setId_carrinho(ObjectId id_carrinho) {
        this.id_carrinho = id_carrinho;
    }

    public ObjectId getId_produto() {
        return id_produto;
    }

    public void setId_produto(ObjectId id_produto) {
        this.id_produto = id_produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    

}
