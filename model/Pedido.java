package model;

import java.sql.Timestamp;

import org.bson.types.ObjectId;

public class Pedido {
    
    private ObjectId id_pedido;
    private ObjectId id_cliente;
    private ObjectId id_carrinho;
    private double valor_total;
    private String status;



    public Pedido(ObjectId id_cliente, ObjectId id_carrinho_de_compras, double valor_total) {

        this.id_cliente = id_cliente;
        this.id_carrinho = id_carrinho_de_compras;
        this.valor_total = valor_total;
        this.status = "pendente";

    }



    public ObjectId getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(ObjectId id_pedido) {
        this.id_pedido = id_pedido;
    }

    public ObjectId getId_carrinho() {
        return id_carrinho;
    }

    public void setId_carrinho(ObjectId id_carrinho) {
        this.id_carrinho = id_carrinho;
    }

    public ObjectId getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(ObjectId id_cliente) {
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
