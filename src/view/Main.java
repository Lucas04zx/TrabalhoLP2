package view;

import java.util.List;

import model.User;
import model.data.DAOFactory;
import model.data.UserDAO;

public class Main {

    public static void main(String[] args) throws Exception {

        UserDAO userDAO = DAOFactory.createUserDAO();

        // Listagem de usuários
        List<User> users = userDAO.findAll();
        for (User user : users) {
            System.out.println("ID: " + user.getId());
            System.out.println("Nome: " + user.getNome());
            System.out.println("Sexo: " + user.getSexo());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Senha: " + user.getSenha());
            System.out.println("-------------------------");
        }
    }
}
