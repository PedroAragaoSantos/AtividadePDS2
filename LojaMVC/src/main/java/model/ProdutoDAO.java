package model;

import dal.ConexaoBD;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    
    public void inserirProduto(Produto produto) throws SQLException{
    String sql = "INSERT INTO produto (descricao, valor, quantidade_estoque) VALUES (?,?,?)";

    try (Connection conn =  ConexaoBD.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){

        stmt.setString(1, produto.getDescricao());
        stmt.setDouble(2, produto.getValor());
        stmt.setInt(3, produto.getQuantidadeEstoque());

        stmt.executeUpdate();
        System.out.println("Produto cadastrado com sucesso!");

    }catch(SQLException e){
        System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        throw e;
    }
}
    
     public List<Produto> listarTodosProdutos() throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while(rs.next()) {
                Produto p = new Produto(
                    rs.getInt("id"),
                    rs.getString("descricao"),
                    rs.getLong("valor"),
                    rs.getInt("quantidade_estoque")
                );
                produtos.add(p);
            }
        }
        return produtos;
    }
     
     public void excluirProduto(int id) throws SQLException {
    String sql = "DELETE FROM produto WHERE id = ?"; 

    try (Connection conn = ConexaoBD.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);
        stmt.executeUpdate();
        System.out.println("Produto excluído com sucesso!");
    } catch (SQLException e) {
        System.out.println("Erro ao excluir produto: " + e.getMessage());
        throw e;
    }
}
     
     public void atualizarProduto(Produto produto) throws SQLException {
    String sql = "UPDATE produto SET descricao = ?, valor = ?, quantidade_estoque = ? WHERE id = ?";
    
    try (Connection conn = ConexaoBD.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, produto.getDescricao());
        stmt.setDouble(2, produto.getValor());
        stmt.setInt(3, produto.getQuantidadeEstoque());
        stmt.setInt(4, produto.getId());
        
        int rows = stmt.executeUpdate();
        if (rows > 0) {
            System.out.println("Produto atualizado com sucesso!");
        } else {
            System.out.println("Produto não encontrado para atualizar.");
        }
        
    } catch (SQLException e) {
        System.out.println("Erro ao atualizar produto: " + e.getMessage());
        throw e;
    }
}
}
    

