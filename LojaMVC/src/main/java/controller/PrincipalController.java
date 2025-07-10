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
    private MenuItem cadastrarCliente;
    
    @FXML
    private MenuItem cadastrarProduto;

    @FXML
    private Label lblUsuario;

    @FXML
    private Menu menuAjuda;

    @FXML
    private Menu menuCadastro;

    @FXML
    private MenuItem menuFechar;
    
    @FXML
    private MenuItem menuViewClientes;

    @FXML
    private MenuItem menuRelatorioProdutos;
    
    

    @FXML
    private Menu menuRelatorios;

    @FXML
    private MenuItem menuSobre;

     @FXML
    void OnActionMenuRelatorioClientes(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/view/ListagemUsuarios.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        
        Stage telaListagemUsuarios = new Stage();
        
        ListagemUsuariosController luc = loader.getController();

        luc.setStage(telaListagemUsuarios);

        telaListagemUsuarios.setOnShown(evento -> {
            try {
                luc.ajustarElementosJanela();
            } catch (Exception ex) {
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Scene scene = new Scene(root);
        
        telaListagemUsuarios.setTitle("Listagem de Usuários");
        telaListagemUsuarios.setScene(scene);
        telaListagemUsuarios.show();
    }

    @FXML
    void OnActionMenuRelatorioProdutos(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/view/visualizarProduto.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        
        Stage telaItemVenda = new Stage();
        
        visualizarProdutoController ivc = loader.getController();

        ivc.setStage(telaItemVenda);


        Scene scene = new Scene(root);
        
        telaItemVenda.setTitle("Itens a venda");
        telaItemVenda.setScene(scene);
        telaItemVenda.show();

    }
    
    @FXML
    void OnActionMenuViewClientes(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/view/visualizarClientes.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        
        Stage telaItemVenda = new Stage();
        
        visualizarClientesController ivc = loader.getController();

        ivc.setStage(telaItemVenda);
        telaItemVenda.setOnShown(evento -> {
            try {
                ivc.ajustarElementosJanela();
            } catch (Exception ex) {
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Scene scene = new Scene(root);
        
        telaItemVenda.setTitle("ver clientes");
        telaItemVenda.setScene(scene);
        telaItemVenda.show();

    }
    
    @FXML
    void menuCadastroProdutoClick(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/view/ProdutoView.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        
        Stage telaCadastroProduto = new Stage();
        
        ProdutoViewController luc = loader.getController();

        luc.setStage(telaCadastroProduto);

        telaCadastroProduto.setOnShown(evento -> {
            try {
                luc.ajustarElementosJanela();
            } catch (Exception ex) {
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Scene scene = new Scene(root);
        
        telaCadastroProduto.setTitle("Cadastro de produto");
        telaCadastroProduto.setScene(scene);
        telaCadastroProduto.show();
        

    }

   @FXML
   void OnActionCadastrarCliente(ActionEvent event) throws IOException{
       URL url = new File("src/main/java/view/ClienteView.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        
        Stage telaClienteView = new Stage();
        
        ClienteViewController cvc = loader.getController();

        cvc.setStage(telaClienteView);

        telaClienteView.setOnShown(evento -> {
        try {
            cvc.ajustarElementosJanela(null);  
        } catch (Exception ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    });
        Scene scene = new Scene(root);
        
        telaClienteView.setTitle("Cadastro de clientes");
        telaClienteView.setScene(scene);
        telaClienteView.show();
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
        lblUsuario.setText(dados.get(0));
        if (dados.get(1).equals("admin")) {            
            menuRelatorios.setDisable(false);
        } else {
            menuRelatorios.setDisable(true);
        }
    }
    
    

}
