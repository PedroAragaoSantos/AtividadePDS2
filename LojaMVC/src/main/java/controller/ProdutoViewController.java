package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Produto;
import model.ProdutoDAO;
import util.AlertaUtil;

public class ProdutoViewController {

    @FXML
    private Button btnCadastrarProduto;

    @FXML
    private TextArea txtDescricaoProduto;

    @FXML
    private TextField txtfdQuantidadeEstoque;

    @FXML
    private TextField txtfdValor;
    private Stage stage;
    
    @FXML
    void OnActionCadastrarProdutos(ActionEvent event) {
    try {
        String descricao = txtDescricaoProduto.getText();
        String valorTexto = txtfdValor.getText();
        String quantidadeTexto = txtfdQuantidadeEstoque.getText();

        if (descricao.isEmpty() || valorTexto.isEmpty() || quantidadeTexto.isEmpty()) {
            AlertaUtil.mostrarErro("Erro", "Preencha todos os campos obrigatórios.");
            return; 
        }

        long valor = Long.parseLong(valorTexto);
        int quantidadeEstoque = Integer.parseInt(quantidadeTexto);

        Produto produto = new Produto(descricao, valor, quantidadeEstoque);

        ProdutoDAO produtoDAO = new ProdutoDAO();
        produtoDAO.inserirProduto(produto);

        AlertaUtil.mostrarInformacao("Sucesso", "Produto cadastrado com sucesso!");
        System.out.println("Produto cadastrado com sucesso!");

        txtDescricaoProduto.clear();
        txtfdValor.clear();
        txtfdQuantidadeEstoque.clear();

        stage.close();

    } catch (NumberFormatException e) {
        AlertaUtil.mostrarErro("Erro", "Valor ou quantidade inválidos. Use apenas números.");
        System.out.println("Erro ao converter valor ou quantidade: " + e.getMessage());
    } catch (Exception e) {
        AlertaUtil.mostrarErro("Erro", "Erro ao cadastrar produto: " + e.getMessage());
        System.out.println("Erro ao cadastrar produto: " + e.getMessage());
    }
}

    void setStage(Stage telaCadastroProduto) {
        this.stage = telaCadastroProduto;
    }

    void ajustarElementosJanela() {
    }

}
