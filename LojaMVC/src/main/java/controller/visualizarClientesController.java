package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Cliente;
import model.ClienteDAO;
import util.AlertaUtil;

public class visualizarClientesController {
    
    Stage stage;
    public void setStage(Stage stage){
        this.stage = stage;
    }

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

    public void ajustarElementosJanela() {
        carregarClientesTabela();   
    }    
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public void carregarClientesTabela() {
        try {
            ClienteDAO dao = new ClienteDAO();
            ObservableList<Cliente> lista = FXCollections.observableArrayList(dao.selecionarClientes());

            colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome") );
            colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
            colunaEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
            colunaNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
            colunaNascimento.setCellFactory(column -> new TableCell<Cliente, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(formatter));
                }
            }
        });
            TableViewClientes.setItems(lista);
        } catch (SQLException e) {
            AlertaUtil.mostrarErro("Erro", "Erro ao carregar clientes: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
