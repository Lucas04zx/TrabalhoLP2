package controller;

import java.util.List;
import model.Produtos;
import model.ModelException;
import model.data.DAOFactory;
import model.data.ProdutosDAO;
import view.swing.produtos.IProdutosFormView;
import view.swing.produtos.IProdutosListView;

public class ProductController {
    private final ProdutosDAO produtosDAO = DAOFactory.createProdutosDAO();
    private IProdutosListView produtosListView;
    private IProdutosFormView produtosFormView;

    // Listagem
    public void loadProdutos() {
        try {
            List<Produtos> produtos = produtosDAO.findAll();
            produtosListView.setProdutoList(produtos);
        } catch (ModelException e) {
            produtosListView.showMessage("Erro ao carregar produtos: " + e.getMessage());
        }
    }

    // Salvar ou atualizar
    public void saveOrUpdate(boolean isNew) {
        Produtos produto = produtosFormView.getProdutoFromForm();
        if (produto == null) return; // caso de erro de preço

        try {
            produto.validate();
        } catch (IllegalArgumentException e) {
            produtosFormView.showErrorMessage("Erro de validação: " + e.getMessage());
            return;
        }

        try {
            if (isNew) {
                produtosDAO.save(produto);
            } else {
                produtosDAO.update(produto);
            }
            produtosFormView.showInfoMessage("Produto salvo com sucesso!");
            produtosFormView.close();
        } catch (ModelException e) {
            produtosFormView.showErrorMessage("Erro ao salvar: " + e.getMessage());
        }
    }

    // Excluir
    public void excluirProduto(Produtos produto) {
        try {
            produtosDAO.delete(produto);
            produtosListView.showMessage("Produto excluído!");
            loadProdutos();
        } catch (ModelException e) {
            produtosListView.showMessage("Erro ao excluir: " + e.getMessage());
        }
    }

    public void setProdutosFormView(IProdutosFormView produtosFormView) {
        this.produtosFormView = produtosFormView;
    }

    public void setProdutosListView(IProdutosListView produtosListView) {
        this.produtosListView = produtosListView;
    }
}
