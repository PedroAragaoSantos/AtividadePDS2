package model;

public class Produto {
    private int id;
    private String descricao;
    private long valor;
    private int quantidadeEstoque;
    
    public Produto(){
        
    }

    public Produto(int id, String descricao, long valor, int quantidadeEstoque) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.quantidadeEstoque = quantidadeEstoque;
    }
    
    public Produto(String descricao, long valor, int quantidadeEstoque) {
        this.descricao = descricao;
        this.valor = valor;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValor(long valor) {
        this.valor = valor;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }
    
}
