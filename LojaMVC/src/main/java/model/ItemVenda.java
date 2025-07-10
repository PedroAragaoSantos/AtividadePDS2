package model;

public class ItemVenda {
    private int id;
    private int quantidade;
    private long precoUnitario;
    
    private int idVenda;
    private int idProduto;

    public ItemVenda() {
    }

    public ItemVenda(int id, int quantidade, long precoUnitario, int idVenda, int idProduto) {
        this.id = id;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.idVenda = idVenda;
        this.idProduto = idProduto;
    }
    
    public ItemVenda(int quantidade, long precoUnitario, int idVenda, int idProduto) {
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.idVenda = idVenda;
        this.idProduto = idProduto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public long getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(long precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }
     
}
