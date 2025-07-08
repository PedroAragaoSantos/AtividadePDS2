package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Cliente;
import model.ClienteDAO;
import util.AlertaUtil;

public class ClienteViewController {

    private Cliente clienteSelecionado;
    private final DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    ObservableList<Cliente> listaClientes;

    @FXML private Button btnSalvar;
    @FXML private TextField txtDataNascimento;
    @FXML private TextField txtEndereco;
    @FXML private TextField txtNome;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtPesquisar;
    @FXML private Button btnExcluir;
    @FXML private TableView<Cliente> tabelaClientes;

    @FXML
    void initialize() {
        try {
            carregarClientesTabela();
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
    }
    
@FXML
void onClickExcluir(ActionEvent event) {
    Cliente clienteSelecionado = tabelaClientes.getSelectionModel().getSelectedItem();

    if (clienteSelecionado == null) {
        AlertaUtil.mostrarErro("Erro", "Selecione um cliente para excluir.");
        return;
    }

    try {
        ClienteDAO dao = new ClienteDAO();
        dao.excluir(clienteSelecionado.getId());

        AlertaUtil.mostrarInformacao("Sucesso", "Cliente excluído com sucesso!");
        carregarClientesTabela();
        limparCampos();

    } catch (SQLException e) {
        AlertaUtil.mostrarErro("Erro ao excluir", e.getMessage());
        e.printStackTrace();
    }
}

    @FXML
    void onClickSalvar(ActionEvent event) throws SQLException {
        String nome = txtNome.getText();
        String telefone = txtTelefone.getText();
        String endereco = txtEndereco.getText();
        String dataTexto = txtDataNascimento.getText();

        Date dataNascimento = null;

        if (!dataTexto.isEmpty()) {
            try {
                LocalDate localDate = LocalDate.parse(dataTexto, formatoData);
                dataNascimento = Date.valueOf(localDate);
            } catch (DateTimeParseException e) {
                AlertaUtil.mostrarErro("Data inválida", "Use o formato dd/MM/yyyy.");
                return;
            }
        }

        if (nome.isEmpty() || telefone.isEmpty() || endereco.isEmpty()) {
            AlertaUtil.mostrarErro("Erro", "Todos os campos devem ser preenchidos.");
            return;
        }

        ClienteDAO dao = new ClienteDAO();

        if (clienteSelecionado == null) {
            Cliente cliente = new Cliente();
            cliente.setNome(nome);
            cliente.setTelefone(telefone);
            cliente.setEndereco(endereco);
            cliente.setDataNascimento(dataNascimento);

            dao.salvar(cliente);
            AlertaUtil.mostrarInformacao("Sucesso", "Cliente salvo com sucesso!");
        } else {
            clienteSelecionado.setNome(nome);
            clienteSelecionado.setTelefone(telefone);
            clienteSelecionado.setEndereco(endereco);
            clienteSelecionado.setDataNascimento(dataNascimento);

            dao.alterarCliente(clienteSelecionado);
            AlertaUtil.mostrarInformacao("Sucesso", "Cliente editado com sucesso!");
            clienteSelecionado = null;
            btnSalvar.setText("Salvar");
        }

        limparCampos();
        carregarClientesTabela();
    }

    private void limparCampos() {
        txtNome.clear();
        txtTelefone.clear();
        txtEndereco.clear();
        txtDataNascimento.clear();
    }

    private void carregarClientesTabela() throws SQLException {
        ClienteDAO dao = new ClienteDAO();
        listaClientes = FXCollections.observableArrayList(dao.selecionarClientes());

        if (!listaClientes.isEmpty()) {
            tabelaClientes.getColumns().clear();

            TableColumn<Cliente, Number> colunaID = new TableColumn<>("ID");
            colunaID.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getId()));
            colunaID.setPrefWidth(40);

            TableColumn<Cliente, String> colunaNome = new TableColumn<>("Nome");
            colunaNome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome()));
            colunaNome.setStyle("-fx-alignment: CENTER;");

            TableColumn<Cliente, String> colunaTelefone = new TableColumn<>("Telefone");
            colunaTelefone.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTelefone()));

            TableColumn<Cliente, String> colunaEndereco = new TableColumn<>("Endereço");
            colunaEndereco.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEndereco()));

            TableColumn<Cliente, String> colunaNascimento = new TableColumn<>("Nascimento");
            colunaNascimento.setCellValueFactory(c -> {
                Date nascimento = c.getValue().getDataNascimento();
                String dataFormatada = (nascimento != null) ? nascimento.toLocalDate().format(formatoData) : "";
                return new SimpleStringProperty(dataFormatada);
            });

            tabelaClientes.getColumns().addAll(
                colunaID, colunaNome, colunaTelefone, colunaEndereco, colunaNascimento
            );

            FilteredList<Cliente> listaFiltrada = new FilteredList<>(listaClientes, p -> true);

            txtPesquisar.textProperty().addListener((obs, oldVal, newVal) -> {
                listaFiltrada.setPredicate(cliente -> {
                    if (newVal == null || newVal.isEmpty()) return true;

                    String filtro = newVal.toLowerCase();
                    return cliente.getNome().toLowerCase().contains(filtro)
                        || cliente.getTelefone().toLowerCase().contains(filtro)
                        || cliente.getEndereco().toLowerCase().contains(filtro)
                        || (cliente.getDataNascimento() != null && cliente.getDataNascimento().toLocalDate().format(formatoData).toLowerCase().contains(filtro));
                });
            });

            SortedList<Cliente> listaOrdenada = new SortedList<>(listaFiltrada);
            listaOrdenada.comparatorProperty().bind(tabelaClientes.comparatorProperty());
            tabelaClientes.setItems(listaOrdenada);

        } else {
            AlertaUtil.mostrarErro("Erro", "Nenhum cliente encontrado.");
        }
    }

    @FXML
    void TableViewClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            clienteSelecionado = tabelaClientes.getSelectionModel().getSelectedItem();
            if (clienteSelecionado != null) {
                preencherCamposParaEdicao(clienteSelecionado);
            }
        }
    }

    private void preencherCamposParaEdicao(Cliente cliente) {
        txtNome.setText(cliente.getNome());
        txtTelefone.setText(cliente.getTelefone());
        txtEndereco.setText(cliente.getEndereco());

        if (cliente.getDataNascimento() != null) {
            txtDataNascimento.setText(cliente.getDataNascimento().toLocalDate().format(formatoData));
        } else {
            txtDataNascimento.setText("");
        }

        btnSalvar.setText("Editar");
    }
}
