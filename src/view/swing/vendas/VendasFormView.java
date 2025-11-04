package view.swing.vendas;

import controller.SalesController;
import model.Vendas;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

class VendasFormView extends JDialog implements IVendasFormView {
    private final JTextField dataVendaField = new JTextField(20);
    private final JTextField valorTotalField = new JTextField(10);
    private final JButton saveButton = new JButton("Salvar");
    private final JButton closeButton = new JButton("Fechar");
    private SalesController controller;
    private final boolean isNew;
    private final VendasListView parent;
    private Vendas venda;

    public VendasFormView(VendasListView parent, Vendas venda, SalesController controller) {
        super(parent, true);
        this.controller = controller;
        this.controller.setVendasFormView(this);

        this.parent = parent;
        this.venda = venda;
        this.isNew = (venda == null);

        setTitle(isNew ? "Nova Venda" : "Editar Venda");
        setSize(350, 180);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campo Data da Venda
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Data da Venda:"), gbc);
        gbc.gridx = 1;
        add(dataVendaField, gbc);
        dataVendaField.setEditable(false); // gerada automaticamente

        // Campo Valor Total
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Valor Total (R$):"), gbc);
        gbc.gridx = 1;
        add(valorTotalField, gbc);

        // Botões
        JPanel btnPanel = new JPanel();
        btnPanel.add(saveButton);
        btnPanel.add(closeButton);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        if (!isNew) setVendaInForm(venda);
        else dataVendaField.setText(LocalDateTime.now().toString());

        saveButton.addActionListener(e -> controller.saveOrUpdate(isNew));
        closeButton.addActionListener(e -> close());
    }

    @Override
    public Vendas getVendaFromForm() {
        if (venda == null) venda = new Vendas();
        venda.setDataVenda(LocalDateTime.now());
        try {
            venda.setValorTotal(Double.parseDouble(valorTotalField.getText()));
        } catch (NumberFormatException e) {
            showErrorMessage("O valor total deve ser numérico.");
            return null;
        }
        return venda;
    }

    @Override
    public void setVendaInForm(Vendas venda) {
        dataVendaField.setText(venda.getDataVenda().toString());
        valorTotalField.setText(String.format("%.2f", venda.getValorTotal()));
    }

    @Override
    public void showInfoMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void close() {
        parent.refresh();
        dispose();
    }
}
