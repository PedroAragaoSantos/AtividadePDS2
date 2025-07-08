package model;

import dal.ConexaoBD;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClienteDAO {

    // Salvar (inserir) cliente
    public void salvar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes (nome, telefone, endereco, data_nascimento) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getEndereco());
            stmt.setDate(4, cliente.getDataNascimento());

            stmt.executeUpdate();
        }
    }

    public void alterarCliente(Cliente cliente) throws SQLException {
    String sql = "UPDATE clientes SET nome = ?, telefone = ?, endereco = ?, data_nascimento = ? WHERE id = ?";

    try (Connection conn = ConexaoBD.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, cliente.getNome());
        stmt.setString(2, cliente.getTelefone());
        stmt.setString(3, cliente.getEndereco());
        stmt.setDate(4, cliente.getDataNascimento());
        stmt.setInt(5, cliente.getId()); // ESSENCIAL: usar o ID do cliente aqui

        stmt.executeUpdate();
        System.out.println("Cliente atualizado com sucesso!");
    } catch (SQLException e) {
        System.out.println("Erro ao atualizar cliente: " + e.getMessage());
        throw e;
    }
}

    
    // Alterar cliente existente
    /*public void alterar(Cliente cliente) throws SQLException {
        String sql = "UPDATE clientes SET nome = ?, telefone = ?, endereco = ?, data_nascimento = ? WHERE id = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getEndereco());
            stmt.setDate(4, cliente.getDataNascimento());
            stmt.setInt(5, cliente.getId());

            stmt.executeUpdate();
        }
    }*/

    // Excluir cliente por ID
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Buscar todos os clientes
    public ObservableList<Cliente> selecionarClientes() throws SQLException {
        ObservableList<Cliente> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM clientes";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setDataNascimento(rs.getDate("data_nascimento"));
                lista.add(cliente);
            }
        }

        return lista;
    }

    // Buscar um cliente por ID
    public Cliente selecionarCliente(int id) throws SQLException {
        Cliente cliente = null;
        String sql = "SELECT * FROM clientes WHERE id = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setDataNascimento(rs.getDate("data_nascimento"));
            }

            rs.close();
        }
        return cliente;
    }
        
    
}
