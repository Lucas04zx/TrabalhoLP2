package view.swing.produtos;

import controller.ProductController;
import model.Produtos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProdutosListView extends JDialog implements IProdutosListView {
    private ProductController controller;
    private final ProdutosTableModel tableModel = new ProdutosTableModel();
    private final JTable table = new JTable(tableModel);

    public ProdutosListView(JFrame parent) {
        super(parent, "Produtos", true);
        this.controller = new ProductController();
        this.controller.setProdutosListView(this);

        setSize(700, 400);
        setLocationRelativeTo(null);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setRowHeight(36);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

        JButton addButton = new JButton("Adicionar Produto");
        addButton.addActionListener(e -> {
            ProdutosFormView form = new ProdutosFormView(this, null, controller);
            form.setVisible(true);
        });

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
                Produtos produto = tableModel.getProdutoAt(row);
                ProdutosFormView form = new ProdutosFormView(this, produto, controller);
                form.setVisible(true);
            }
        });

        deleteItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Produtos produto = tableModel.getProdutoAt(row);
                int confirm = JOptionPane.showConfirmDialog(this, "Excluir produto?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.excluirProduto(produto);
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(addButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        controller.loadProdutos();
    }

    @Override
    public void setProdutoList(List<Produtos> produtos) {
        tableModel.setProdutos(produtos);
    }

    @Override
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void refresh() {
        controller.loadProdutos();
    }

    // Tabela de produtos
    static class ProdutosTableModel extends AbstractTableModel {
        private final String[] columns = {"ID", "Nome", "Descrição", "Preço"};
        private List<Produtos> produtos = new ArrayList<>();

        public void setProdutos(List<Produtos> produtos) {
            this.produtos = produtos;
            fireTableDataChanged();
        }

        public Produtos getProdutoAt(int row) {
            return produtos.get(row);
        }

        @Override public int getRowCount() { return produtos.size(); }
        @Override public int getColumnCount() { return columns.length; }
        @Override public String getColumnName(int col) { return columns[col]; }

        @Override
        public Object getValueAt(int row, int col) {
            Produtos p = produtos.get(row);
            switch (col) {
                case 0: return p.getId();
                case 1: return p.getNome();
                case 2: return p.getDescricao();
                case 3: return p.getPreco();
                default: return null;
            }
        }

        @Override public boolean isCellEditable(int row, int col) { return false; }
    }
}
