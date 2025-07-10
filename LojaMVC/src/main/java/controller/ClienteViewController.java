package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Cliente;
import model.ClienteDAO;
import util.AlertaUtil;
import java.time.LocalDate;
import javafx.scene.control.DatePicker;

public class ClienteViewController {

    @FXML
    private Button btnSalvar;

    @FXML
    private TableView<Cliente> TableViewClientes;

    @FXML
    private TableColumn<Cliente, String> colunaNome;

    @FXML
    private TableColumn<Cliente, String> colunaTelefone;

    @FXML
    private TableColumn<Cliente, String> colunaEndereco;

    @FXML
    private TableColumn<Cliente, LocalDate> colunaNascimento;

    @FXML
    private DatePicker txtDataNascimento;

    @FXML
    private TextField txtEndereco;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtTelefone;

    private Stage stage;
    private Cliente clienteAtual;

   
    @FXML
    void btnSalvarOnActionPerformed(ActionEvent event) {
        String nome = txtNome.getText();
        String telefone = txtTelefone.getText();
        String endereco = txtEndereco.getText();
        
        try {
            
            if(nome.isEmpty() || telefone.isEmpty() || endereco.isEmpty()){
            AlertaUtil.mostrarErro("Erro", "Campos obrigatórios não preenchidos");
            
            return;
            }
            
            if (clienteAtual == null) {
                clienteAtual = new Cliente();
            }

            clienteAtual.setNome(txtNome.getText());
            clienteAtual.setTelefone(txtTelefone.getText());
            clienteAtual.setEndereco(txtEndereco.getText());

            if (!txtDataNascimento.getValue().equals(null)) {
                
                clienteAtual.setDataNascimento(txtDataNascimento.getValue());
            } else {
                clienteAtual.setDataNascimento(null);
            }

            ClienteDAO dao = new ClienteDAO();
            if (clienteAtual.getId() == 0) {
                dao.inserirCliente(clienteAtual);
            } else {
                dao.atualizarCliente(clienteAtual);
            }

            AlertaUtil.mostrarInformacao("Sucesso", "Cliente salvo com sucesso!");

            limparCampos();

        } catch (Exception e) {
            AlertaUtil.mostrarErro("Erro", "Erro ao salvar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    public void ajustarElementosJanela(Cliente cliente) {
        this.clienteAtual = cliente;
        if (cliente != null) {
            txtNome.setText(cliente.getNome());
            txtTelefone.setText(cliente.getTelefone());
            txtEndereco.setText(cliente.getEndereco());

            if (cliente.getDataNascimento() != null) {
                txtDataNascimento.setValue(LocalDate.MAX);
                        cliente.getDataNascimento();               
            } else {
                txtDataNascimento.setValue(null);
            }
        }  
    }

    public void setStage(Stage telaCadastroCliente) {
        this.stage = telaCadastroCliente;
    }
    
    private void limparCampos() {
        txtNome.setText("");
        txtTelefone.setText("");
        txtEndereco.setText("");
        txtDataNascimento.setValue(null);
        clienteAtual = null;
    }
}
