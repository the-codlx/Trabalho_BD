package model;
import java.sql.Date;

import org.bson.types.ObjectId;

public class Relatorio {
    
    private int id_relatorio;
    private ObjectId id_pedido;
    private Date data_geracao;
    private String conteudo;

    public Relatorio (ObjectId id_pedido, String conteudo) 
    {

        this.id_pedido = id_pedido;
        this.conteudo = conteudo;

    }

    public int getId_relatorio() {
        return id_relatorio;
    }

    public void setId_relatorio(int id_relatorio) {
        this.id_relatorio = id_relatorio;
    }

    public ObjectId getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(ObjectId id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Date getData_geracao() {
        return data_geracao;
    }

    public void setData_geracao(Date data_geracao) {
        this.data_geracao = data_geracao;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    

}
