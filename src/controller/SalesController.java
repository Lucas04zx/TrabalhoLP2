package controller;

import java.util.List;
import model.Vendas;
import model.ModelException;
import model.data.DAOFactory;
import model.data.VendasDAO;
import view.swing.vendas.IVendasFormView;
import view.swing.vendas.IVendasListView;

public class SalesController {

    private final VendasDAO vendasDAO = DAOFactory.createVendasDAO();
    private IVendasListView vendasListView;
    private IVendasFormView vendasFormView;

    // ===== Listagem =====
    public void loadVendas() {
        try {
            List<Vendas> vendas = vendasDAO.findAll();
            vendasListView.setVendasList(vendas);
        } catch (ModelException e) {
            vendasListView.showMessage("Erro ao carregar vendas: " + e.getMessage());
        }
    }

    // ===== Salvar ou atualizar =====
    public void saveOrUpdate(boolean isNew) {
        Vendas venda = vendasFormView.getVendaFromForm();
        if (venda == null) return; // erro de conversão já tratado no form

        try {
            venda.validate();
        } catch (IllegalArgumentException e) {
            vendasFormView.showErrorMessage("Erro de validação: " + e.getMessage());
            return;
        }

        try {
            if (isNew) {
                vendasDAO.save(venda);
            } else {
                vendasDAO.update(venda);
            }
            vendasFormView.showInfoMessage("Venda salva com sucesso!");
            vendasFormView.close();
        } catch (ModelException e) {
            vendasFormView.showErrorMessage("Erro ao salvar: " + e.getMessage());
        }
    }

    // ===== Excluir =====
    public void excluirVenda(Vendas venda) {
        try {
            vendasDAO.delete(venda);
            vendasListView.showMessage("Venda excluída!");
            loadVendas();
        } catch (ModelException e) {
            vendasListView.showMessage("Erro ao excluir: " + e.getMessage());
        }
    }

    // ===== Setters para as views =====
    public void setVendasFormView(IVendasFormView vendasFormView) {
        this.vendasFormView = vendasFormView;
    }

    public void setVendasListView(IVendasListView vendasListView) {
        this.vendasListView = vendasListView;
    }
}
