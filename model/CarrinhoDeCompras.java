package model;

import java.util.ArrayList;

public class CarrinhoDeCompras {
    
    private int id_carrinho;
    private int id_cliente;
    private String status;
    private int data_criacao;
    private int id_itens_do_carrinho;


    public CarrinhoDeCompras(int id_cliente,int id_itens_do_carrinho) {

        this.id_cliente = id_cliente;
        this.id_itens_do_carrinho = id_itens_do_carrinho;
        this.status = "ativo";

    }


    public int getId_carrinho() {
        return id_carrinho;
    }


    public void setId_carrinho(int id_carrinho) {
        this.id_carrinho = id_carrinho;
    }


    public int getId_cliente() {
        return id_cliente;
    }


    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }


    public int getData_criacao() {
        return data_criacao;
    }


    public void setData_criacao(int data_criacao) {
        this.data_criacao = data_criacao;
    }


    public int getId_itens_do_carrinho() {
        return id_itens_do_carrinho;
    }


    public void setId_itens_do_carrinho(int id_itens_do_carrinho) {
        this.id_itens_do_carrinho = id_itens_do_carrinho;
    }


    

}