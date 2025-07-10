package model;

import dal.ConexaoBD;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemVendaDAO {
    
   public void inserirItemVenda(ItemVenda itemVenda) throws SQLException{
    String sql = "INSERT INTO item_venda(id, venda_id, produto_id, quantidade, preco_unitario) VALUES (?,?,?,?,?)";

    try(Connection conn =  ConexaoBD.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){

        stmt.setInt(1, itemVenda.getId());
        stmt.setInt(2, itemVenda.getIdVenda());
        stmt.setInt(3, itemVenda.getIdProduto());
        stmt.setInt(4, itemVenda.getQuantidade()); 
        stmt.setLong(5, itemVenda.getPrecoUnitario()); 

        stmt.executeUpdate();

        System.out.println("Item para venda inserido com sucesso!");
    }catch(SQLException e){
        System.out.println("Erro ao inserir item para venda: " + e.getMessage());
        throw e;
    }
}
    
    public void listarItemVenda() throws SQLException{
        String sql = "SELECT * FROM item_venda";
        
        try(Connection conn = ConexaoBD.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)){
                ResultSet rs = stmt.executeQuery();
                
            while(rs.next()){
                int id = rs.getInt("id");
                int idVenda = rs.getInt("venda_id");
                int idProduto = rs.getInt("produto_id");
                int quantidade = rs.getInt("quantidade");
                long precoUnitario = rs.getLong("preco_unitario");
               
                System.out.println("ID: " + id + "\nID da venda: " + idVenda + "\nID do produto: " + idProduto +
                        "\nQuantidade: " + quantidade + "\nPreço unitário: " + precoUnitario);
            }           
        }catch(SQLException e){
            System.out.println("Erro ao listar os itens para venda: " + e.getMessage());
    }
    }
    
    public boolean produtoTemVendas(int produtoId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM item_venda WHERE produto_id = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, produtoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;  // Se tiver pelo menos 1 venda, retorna true
                }
            }
        }
        return false;
    }
    
    
}
