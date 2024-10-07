package model;

import java.sql.Timestamp;

public class Pedido {
    
    private int id_pedido;
    private int id_cliente;
    private int id_pagamento;
    private Timestamp data_pedido;
    private double valor_total;
    private Status status;

    public enum Status {
        PENDENTE,
        PAGO,
        ENVIADO

    }

    public Pedido(int id_cliente, int id_pagamento, double valor_total) {

        this.id_cliente = id_cliente;
        this.id_pagamento = id_pagamento;
        this.valor_total = valor_total;
        this.data_pedido = new Timestamp(System.currentTimeMillis());
        this.status = status.PENDENTE;

    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_pagamento() {
        return id_pagamento;
    }

    public void setId_pagamento(int id_pagamento) {
        this.id_pagamento = id_pagamento;
    }

    public Timestamp getData_pedido() {
        return data_pedido;
    }

    public void setData_pedido(Timestamp data_pedido) {
        this.data_pedido = data_pedido;
    }

    public double getValor_total() {
        return valor_total;
    }

    public void setValor_total(double valor_total) {
        this.valor_total = valor_total;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    

}
