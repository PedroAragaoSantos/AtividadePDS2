package model;

import java.sql.Date;

public class Venda {
    private int id;
    private Date dataCompra;
    private long valorTotal;
    
    private int idCliente;

    public Venda() {
    }

    public Venda(int id, Date dataCompra, long valorTotal, int idCliente) {
        this.id = id;
        this.dataCompra = dataCompra;
        this.valorTotal = valorTotal;
        this.idCliente = idCliente;
    }
    
    public Venda(Date dataCompra, long valorTotal, int idCliente) {
        this.dataCompra = dataCompra;
        this.valorTotal = valorTotal;
        this.idCliente = idCliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public long getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(long valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
      
}
