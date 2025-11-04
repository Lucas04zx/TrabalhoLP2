package view.swing.vendas;

import controller.SalesController;
import model.Vendas;

import javax.swing.*;

public interface IVendasFormView {
    Vendas getVendaFromForm();
    void setVendaInForm(Vendas venda);
    void showInfoMessage(String msg);
    void showErrorMessage(String msg);
    void close();
}
