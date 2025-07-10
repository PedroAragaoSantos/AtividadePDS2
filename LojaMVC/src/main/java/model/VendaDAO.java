package model;

import dal.ConexaoBD;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VendaDAO {
    
    public void listarVendas() throws SQLException{
        String sql = "SELECT * FROM venda";
        
        try(Connection conn = ConexaoBD.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)){
                ResultSet rs = stmt.executeQuery();
                
            while(rs.next()){
                int id = rs.getInt("id");
                Date dataCompra = rs.getDate("data_compra");
                long valor = rs.getLong("valor_total");
                int idCliente = rs.getInt("cliente_id");
                
                System.out.println("ID: " + id + "\nData de compra: " + dataCompra + "\nValor total: " + valor +
                        "\nID do cliente: " + idCliente);
            }           
        }catch(SQLException e){
            System.out.println("Erro ao listar produtos: " + e.getMessage());
    }
    }
    
}
