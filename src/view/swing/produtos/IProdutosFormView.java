package view.swing.produtos;

import model.Produtos;

public interface IProdutosFormView {
    Produtos getProdutoFromForm();
    void setProdutoInForm(Produtos produto);
    void showInfoMessage(String msg);
    void showErrorMessage(String msg);
    void close();
}
