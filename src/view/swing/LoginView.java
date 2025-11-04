package view.swing;

import model.User;
import model.data.DAOFactory;
import model.data.UserDAO;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JDialog {
    private boolean authenticated = false;
    private final JTextField emailField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);

    public LoginView() {
        setTitle("Aplicativo Mercado de Construção");
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245)); // Fundo claro

        // Painel de form central
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(245, 245, 245));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Login do Sistema", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(30, 30, 30));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        formPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passwordField, gbc);

        // Painel de botões
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(245, 245, 245));
        JButton loginBtn = new JButton("Entrar");
        loginBtn.setBackground(new Color(70, 130, 180));
        loginBtn.setForeground(Color.WHITE);
        JButton cancelBtn = new JButton("Cancelar");
        cancelBtn.setBackground(new Color(192, 57, 43));
        cancelBtn.setForeground(Color.WHITE);

        loginBtn.addActionListener(e -> login());
        cancelBtn.addActionListener(e -> {
            authenticated = false;
            dispose();
        });

        buttonsPanel.add(loginBtn);
        buttonsPanel.add(cancelBtn);

        add(formPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
    }

    private void login() {
        String email = emailField.getText().trim();
        String senha = new String(passwordField.getPassword()).trim();

        if (email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha email e senha.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            UserDAO userDAO = DAOFactory.createUserDAO();
            User user = userDAO.findByEmail(email); // Necessário criar findByEmail
            if (user != null && senha.equals(user.getSenha())) {
                authenticated = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Email ou senha inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao acessar banco: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}
