package view.swing.produtos;

import java.util.List;
import model.Produtos;

public interface IProdutosListView {
    void setProdutoList(List<Produtos> produtos);
    void showMessage(String msg);
}
