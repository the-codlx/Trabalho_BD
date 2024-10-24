package model;

import java.sql.Timestamp;

public class Pedido {
    
    private int id_pedido;
    private int id_cliente;
    private int id_carrinho;
    private double valor_total;
    private String status;



    public Pedido(int id_cliente, int id_carrinho_de_compras, double valor_total) {

        this.id_cliente = id_cliente;
        this.id_carrinho = id_carrinho_de_compras;
        this.valor_total = valor_total;
        this.status = "pendente";

    }



    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
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


    public double getValor_total() {
        return valor_total;
    }

    public void setValor_total(double valor_total) {
        this.valor_total = valor_total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    

}
