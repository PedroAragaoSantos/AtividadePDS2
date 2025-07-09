package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Usuario;
import model.UsuarioDAO;
import util.AlertaUtil;

public class ListagemUsuariosController {

    Stage stageListagemUsuarios;
    Usuario usuario;
    ObservableList<Usuario> lista;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnFechar;

    @FXML
    private TableView<Usuario> tabelaUsuarios;

    @FXML
    private TextField txtPesquisar;

    @FXML
    void btnCadastrarClick(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/view/CadastroUsuarios.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        Stage telaCadastroUsuarios = new Stage();

        CadastroUsuariosController cadc = loader.getController();

        cadc.setStage(telaCadastroUsuarios);

        telaCadastroUsuarios.setOnShown(evento -> {
            cadc.ajustarElementosJanela(null);
        });
        
        cadc.setOnUsuarioSalvo(() -> {
            try{
                carregarUsuariosTabela();
            } catch (SQLException ex){
                
            }
        });

        Scene scene = new Scene(root);

        telaCadastroUsuarios.setTitle("Cadastro de Usuários");
        telaCadastroUsuarios.setScene(scene);
        telaCadastroUsuarios.show();
    }

    @FXML
    void btnFecharClick(ActionEvent event) {
        stageListagemUsuarios.close();
    }

    void setStage(Stage telaListagemUsuarios) {
        this.stageListagemUsuarios = telaListagemUsuarios;
    }

    void ajustarElementosJanela() throws SQLException {
        carregarUsuariosTabela();
    }

    //Método para listar usuarios no TableView
    private void carregarUsuariosTabela() throws SQLException {
        lista = FXCollections.observableArrayList(listarUsuarios());
        if (!lista.isEmpty()) {
            tabelaUsuarios.getColumns().clear();

            TableColumn<Usuario, Number> colunaID
                    = new TableColumn<>("ID");
            colunaID.setCellValueFactory(u -> u.getValue().idProperty());
            colunaID.setPrefWidth(40);

            TableColumn<Usuario, String> colunaNome
                    = new TableColumn<>("Nome");
            colunaNome.setCellValueFactory(u
                    -> u.getValue().nomeProperty());
            colunaNome.setStyle("-fx-alignment: CENTER;");

            TableColumn<Usuario, String> colunaFone
                    = new TableColumn<>("Telefone");
            colunaFone.setCellValueFactory(u
                    -> u.getValue().foneProperty());

            TableColumn<Usuario, String> colunaLogin
                    = new TableColumn<>("Login");
            colunaLogin.setCellValueFactory(u
                    -> u.getValue().loginProperty());

            TableColumn<Usuario, String> colunaPerfil
                    = new TableColumn<>("Perfil");
            colunaPerfil.setCellValueFactory(u
                    -> u.getValue().perfilProperty());
            
             TableColumn<Usuario, String> colunaEmail
                    = new TableColumn<>("Email");
            colunaEmail.setCellValueFactory(u
                    -> u.getValue().emailProperty());
            
            TableColumn<Usuario, LocalDate> colunaAniversario
                    = new TableColumn<>("Aniversário");
            colunaAniversario.setCellValueFactory(u
                    -> u.getValue().aniversarioProperty());
            
           
            tabelaUsuarios.getColumns().addAll(colunaID,
                    colunaNome, colunaFone, colunaLogin,
                    colunaPerfil, colunaEmail, colunaAniversario);

//            tabelaUsuarios.setItems(lista);
            FilteredList<Usuario> listaFiltrada = new
                FilteredList<>(lista, p -> true);
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            txtPesquisar.textProperty().addListener((obs, oldVal, newVal) -> {
                listaFiltrada.setPredicate(usuario -> {
                    if(newVal == null || newVal.isEmpty()){
                        return true;
                    }
                    String filtro = newVal.toLowerCase();
                    
                    return usuario.getNome().toLowerCase().contains(filtro)
                            || usuario.getLogin().toLowerCase().contains(filtro)
                            || usuario.getFone().toLowerCase().contains(filtro)
                            || usuario.getPerfil().toLowerCase().contains(filtro)
                            || usuario.getEmail().toLowerCase().contains(filtro)
                            || (usuario.getAniversario() != null && usuario.getAniversario().toLocalDate().format(dtf).toLowerCase().contains(filtro));
                });
            });
                SortedList<Usuario> listaOrdenada = new SortedList<>(listaFiltrada);
                listaOrdenada.comparatorProperty().
                        bind(tabelaUsuarios.comparatorProperty());
                tabelaUsuarios.setItems(listaOrdenada);

        } else {
            AlertaUtil.mostrarErro("Erro", "Erro ao carregar usuários");
        }
    }
    
        @FXML
    void TableViewClick(MouseEvent event) throws IOException {
        if (event.getClickCount() == 1) {
            this.usuario = tabelaUsuarios.getSelectionModel().getSelectedItem();
            if (this.usuario != null) {
                URL url = new File("src/main/java/view/CadastroUsuarios.fxml").toURI().toURL();
                FXMLLoader loader = new FXMLLoader(url);
                Parent root = loader.load();

                Stage telaCadastroUsuarios = new Stage();

                CadastroUsuariosController cadc = loader.getController();

                cadc.setStage(telaCadastroUsuarios);

                telaCadastroUsuarios.setOnShown(evento -> {
                    cadc.ajustarElementosJanela(this.usuario);
                });
                
                cadc.setOnUsuarioSalvo(() -> {
                    try{
                        atualizarUsuariosTabela();
                    } catch (SQLException ex){
                        
                    }
                });

                Scene scene = new Scene(root);

                telaCadastroUsuarios.setTitle("Cadastro de Usuários");
                telaCadastroUsuarios.setScene(scene);
                telaCadastroUsuarios.show();
            }
        }
    }


    //Método para buscar do banco de dados
    private ObservableList<Usuario> listarUsuarios() throws SQLException {
        UsuarioDAO dao = new UsuarioDAO();
        return dao.selecionarUsuarios();
    }

    
    
    private void atualizarUsuariosTabela() throws SQLException{
        lista = FXCollections.observableArrayList(listarUsuarios());
        tabelaUsuarios.setItems(lista); //Vem do BD
    }

}
