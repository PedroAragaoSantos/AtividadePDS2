package model;

import java.sql.Date;
import java.time.LocalDate;

public class Cliente {
    
    private int id;
    private String nome;
    private String telefone;
    private String endereco;
    private LocalDate dataNascimento;

    public Cliente() {
    }

    public Cliente(int id, String nome, String telefone, String endereco, LocalDate dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.dataNascimento = dataNascimento;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }  
    
    @Override
    public String toString() {
        return nome;
    }

}
