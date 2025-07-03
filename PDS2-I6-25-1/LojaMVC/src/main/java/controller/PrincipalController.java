package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import util.AlertaUtil;

public class PrincipalController {

    private Stage stagePrincipal;

    @FXML
    private Label lblUsuario;

    @FXML
    private Menu menuAjuda;

    @FXML
    private Menu menuCadastro;

    @FXML
    private MenuItem menuCadastroUsuarios;

    @FXML
    private MenuItem menuFechar;

    @FXML
    private MenuItem menuRelatorioUsuarios;

    @FXML
    private Menu menuRelatorios;

    @FXML
    private MenuItem menuSobre;

    @FXML
    void menuCadastroUsuariosClick(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/view/ListagemUsuarios.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        
        Stage telaListagemUsuarios = new Stage();
        
        ListagemUsuariosController luc = loader.getController();

        luc.setStage(telaListagemUsuarios);

        telaListagemUsuarios.setOnShown(evento -> {
            try {
                luc.ajustarElementosJanela();
            } catch (SQLException ex) {
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Scene scene = new Scene(root);
        
        telaListagemUsuarios.setTitle("Listagem de Usuários");
        telaListagemUsuarios.setScene(scene);
        telaListagemUsuarios.show();
    }

    @FXML
    void menuFecharClick(ActionEvent event) {
        Optional<ButtonType> resultado = AlertaUtil.mostrarConfirmacao("Atenção", "Tem certeza que deseja fechar a aplicação?");
        if(resultado.isPresent()){
            ButtonType botaoPressionado = resultado.get();
            if(botaoPressionado == ButtonType.OK){
                 System.exit(0);
            }
        }
        
       
    }

    void setStage(Stage telaPrincipal) {
        this.stagePrincipal = telaPrincipal;
    }

    void ajustarElementosJanela(ArrayList<String> dados) {
        System.out.println("Aqui chegam os parâmetros do login "
                + dados.get(0) + " - " + dados.get(1));
        lblUsuario.setText(dados.get(0));
        if (dados.get(1).equals("admin")) {
            System.out.println("Acesso completo");
        } else {
            System.out.println("Acesso restrito");
            menuRelatorios.setDisable(true);
        }
    }

}
