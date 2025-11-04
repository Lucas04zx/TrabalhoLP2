package view.swing.vendas;

import java.util.List;
import model.Vendas;

public interface IVendasListView {
    void setVendasList(List<Vendas> vendas);
    void showMessage(String msg);
}
