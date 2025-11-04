package view.swing;

import view.swing.user.UserListView;
import view.swing.vendas.VendasListView;
import view.swing.produtos.ProdutosListView; // <- import do Produtos

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainView extends JFrame {
    private static final long serialVersionUID = 1L;

    public MainView() {
        setTitle("Facebook CRUD - Swing");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        // Menu Usuários
        JMenu menu = new JMenu("Usuários");
        JMenuItem userListItem = new JMenuItem("Listar Usuários");
        userListItem.addActionListener(e -> new UserListView(this).setVisible(true));
        menu.add(userListItem);
        menuBar.add(menu);

        // Menu Vendas
        JMenu vendasMenu = new JMenu("Vendas");
        JMenuItem vendasListItem = new JMenuItem("Listar Vendas");
        vendasListItem.addActionListener(e -> new VendasListView(this).setVisible(true));
        vendasMenu.add(vendasListItem);
        menuBar.add(vendasMenu);

        // Menu Produtos
        JMenu produtosMenu = new JMenu("Produtos");
        JMenuItem produtosListItem = new JMenuItem("Listar Produtos");
        produtosListItem.addActionListener(e -> new ProdutosListView(this).setVisible(true));
        produtosMenu.add(produtosListItem);
        menuBar.add(produtosMenu);

        // Adiciona um menu vazio para empurrar o próximo menu para a direita
        menuBar.add(Box.createHorizontalGlue());

        // Menu "Sair" alinhado à direita
        JMenu menuSair = new JMenu("...");
        JMenuItem sairItem = new JMenuItem("Fechar o sistema");
        sairItem.addActionListener(e -> System.exit(0));
        menuSair.add(sairItem);
        menuBar.add(menuSair);

        setJMenuBar(menuBar);

        JLabel label = new JLabel("Seja bem-vindo!", SwingConstants.CENTER);

        // Painel com padding
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(32, 32, 32, 32)); // padding de 32px
        contentPanel.add(label, BorderLayout.CENTER);

        setContentPane(contentPanel);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Tela de login antes da tela principal
        SwingUtilities.invokeLater(() -> {
            LoginView login = new LoginView();
            login.setVisible(true);
            if (login.isAuthenticated()) {
                MainView mainView = new MainView();
                mainView.setVisible(true);
                mainView.setExtendedState(JFrame.MAXIMIZED_BOTH);
            } else {
                System.exit(0);
            }
        });
    }
}
