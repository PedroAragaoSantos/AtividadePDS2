package model;

import dal.ConexaoBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO extends GenericDAO{
    
    public void inserirCliente(Cliente cliente) throws SQLException{
        String sql = "INSERT INTO cliente(nome, telefone, endereco, data_nascimento) VALUES (?,?,?,?)";
        
        try(Connection conn =  ConexaoBD.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)){
           
            save(sql, cliente.getNome(), cliente.getTelefone(), cliente.getEndereco(), cliente.getDataNascimento());
            
            stmt.executeUpdate();
            
            System.out.println("Cliente inserido com sucesso!");
        }catch(SQLException e){
            System.out.println("Erro ao inserir cliente: " + e.getMessage());
        }
        }
    
     public void atualizarCliente(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente SET nome = ?, telefone = ?, endereco = ?, data_nascimento = ? WHERE id = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            update(sql, cliente.getId(), cliente.getNome(), cliente.getTelefone(), cliente.getEndereco(), cliente.getDataNascimento());

            stmt.executeUpdate();

            System.out.println("Cliente atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
            throw e;
        }
    }

    public List<Cliente> selecionarClientes() throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                LocalDate dataNascimento = LocalDate.parse(rs.getString("data_nascimento"));
                    
                    Cliente object = new Cliente(rs.getInt("idCliente"), rs.getString("nome"), rs.getString("telefone"),
                        rs.getString("endereco"), dataNascimento);
                    
                    lista.add(object);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao selecionar clientes: " + e.getMessage());
            throw e;
        }

        return lista;
    }
    
    public int idClientes() throws SQLException {
        String sql = "SELECT * FROM cliente";
        int n = 0;
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                n++;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao selecionar clientes: " + e.getMessage());
            throw e;
        }

        return n + 1;
    }
}