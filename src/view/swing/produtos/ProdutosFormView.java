package view.swing.produtos;

import controller.ProductController;
import model.Produtos;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

class ProdutosFormView extends JDialog implements IProdutosFormView {
    private final JTextField nomeField = new JTextField(20);
    private final JTextArea descricaoArea = new JTextArea(4, 20);
    private final JTextField precoField = new JTextField(20);
    private final JButton saveButton = new JButton("Salvar");
    private final JButton closeButton = new JButton("Fechar");

    private ProductController controller;
    private final boolean isNew;
    private final ProdutosListView parent;
    private Produtos produto;

    public ProdutosFormView(ProdutosListView parent, Produtos produto, ProductController controller) {
        super(parent, true);
        this.controller = controller;
        this.controller.setProdutosFormView(this);

        this.parent = parent;
        this.produto = produto;
        this.isNew = (produto == null);

        setTitle(isNew ? "Novo Produto" : "Editar Produto");
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nome
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        add(nomeField, gbc);

        // Descrição
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1;
        JScrollPane scrollPane = new JScrollPane(descricaoArea);
        add(scrollPane, gbc);

        // Preço
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Preço:"), gbc);
        gbc.gridx = 1;
        add(precoField, gbc);

        // Botões
        JPanel btnPanel = new JPanel();
        btnPanel.add(saveButton);
        btnPanel.add(closeButton);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        if (!isNew) setProdutoInForm(produto);

        saveButton.addActionListener(e -> controller.saveOrUpdate(isNew));
        closeButton.addActionListener(e -> close());
    }

    @Override
    public Produtos getProdutoFromForm() {
        if (produto == null) produto = new Produtos();

        produto.setNome(nomeField.getText());
        produto.setDescricao(descricaoArea.getText());

        try {
            BigDecimal preco = new BigDecimal(precoField.getText().replace(",", "."));
            produto.setPreco(preco);
        } catch (NumberFormatException e) {
            showErrorMessage("Preço inválido.");
            return null;
        }

        return produto;
    }

    @Override
    public void setProdutoInForm(Produtos produto) {
        nomeField.setText(produto.getNome());
        descricaoArea.setText(produto.getDescricao());
        precoField.setText(produto.getPreco().toString());
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
