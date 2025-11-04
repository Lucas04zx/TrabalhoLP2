package view.swing.vendas;

import controller.SalesController;
import model.Vendas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class VendasListView extends JDialog implements IVendasListView {
    private SalesController controller;
    private final VendasTableModel tableModel = new VendasTableModel();
    private final JTable table = new JTable(tableModel);

    public VendasListView(JFrame parent) {
        super(parent, "Vendas", true);
        this.controller = new SalesController();
        this.controller.setVendasListView(this);

        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(table);
        table.setRowHeight(32);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

        JButton addButton = new JButton("Adicionar Venda");
        addButton.addActionListener(e -> {
            VendasFormView form = new VendasFormView(this, null, controller);
            form.setVisible(true);
        });

        // Menu de contexto (editar/excluir)
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem editItem = new JMenuItem("Editar");
        JMenuItem deleteItem = new JMenuItem("Excluir");
        popupMenu.add(editItem);
        popupMenu.add(deleteItem);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { showPopup(e); }
            @Override
            public void mouseReleased(MouseEvent e) { showPopup(e); }

            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < table.getRowCount()) {
                        table.setRowSelectionInterval(row, row);
                        popupMenu.show(table, e.getX(), e.getY());
                    }
                }
            }
        });

        editItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Vendas venda = tableModel.getVendaAt(row);
                VendasFormView form = new VendasFormView(this, venda, controller);
                form.setVisible(true);
            }
        });

        deleteItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Vendas venda = tableModel.getVendaAt(row);
                int confirm = JOptionPane.showConfirmDialog(this, "Excluir venda?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.excluirVenda(venda);
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(addButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        controller.loadVendas();
    }

    @Override
    public void setVendasList(List<Vendas> vendas) {
        tableModel.setVendas(vendas);
    }

    @Override
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    // Atualiza a lista após cadastro, edição ou exclusão
    public void refresh() {
        controller.loadVendas();
    }

    // ===== Modelo da Tabela =====
    static class VendasTableModel extends AbstractTableModel {
        private final String[] columns = {"ID", "Data da Venda", "Valor Total (R$)"};
        private List<Vendas> vendas = new ArrayList<>();

        public void setVendas(List<Vendas> vendas) {
            this.vendas = vendas;
            fireTableDataChanged();
        }

        public Vendas getVendaAt(int row) {
            return vendas.get(row);
        }

        @Override
        public int getRowCount() { return vendas.size(); }

        @Override
        public int getColumnCount() { return columns.length; }

        @Override
        public String getColumnName(int col) { return columns[col]; }

        @Override
        public Object getValueAt(int row, int col) {
            Vendas v = vendas.get(row);
            switch (col) {
                case 0: return v.getId();
                case 1: return v.getDataVenda();
                case 2: return String.format("%.2f", v.getValorTotal());
                default: return null;
            }
        }

        @Override
        public boolean isCellEditable(int row, int col) { return false; }
    }
}
