package model.data.mysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.User;
import model.UserGender;
import model.data.DAOUtils;
import model.data.UserDAO;
import model.data.mysql.utils.MySQLConnectionFactory;

public class MySQLUserDAO implements UserDAO {

    @Override
    public void save(User user) throws ModelException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = MySQLConnectionFactory.getConnection();

            String sqlInsert = "INSERT INTO users (nome, sexo, email, senha) VALUES (?, ?, ?, ?);";
            preparedStatement = connection.prepareStatement(sqlInsert);

            preparedStatement.setString(1, user.getNome());
            preparedStatement.setString(2, user.getSexo().toString());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getSenha());

            preparedStatement.executeUpdate();

        } catch (SQLException sqle) {
            DAOUtils.sqlExceptionTreatement("Erro ao inserir user no BD.", sqle);
        } finally {
            DAOUtils.close(preparedStatement);
            DAOUtils.close(connection);
        }
    }

    @Override
    public void update(User user) throws ModelException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = MySQLConnectionFactory.getConnection();

            String sqlUpdate = "UPDATE users SET nome = ?, sexo = ?, email = ?, senha = ? WHERE id = ?;";
            preparedStatement = connection.prepareStatement(sqlUpdate);

            preparedStatement.setString(1, user.getNome());
            preparedStatement.setString(2, user.getSexo().toString());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getSenha());
            preparedStatement.setInt(5, user.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException sqle) {
            DAOUtils.sqlExceptionTreatement("Erro ao atualizar user no BD.", sqle);
        } finally {
            DAOUtils.close(preparedStatement);
            DAOUtils.close(connection);
        }
    }

    @Override
    public void delete(User user) throws ModelException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = MySQLConnectionFactory.getConnection();

            String sqlDelete = "DELETE FROM users WHERE id = ?;";
            preparedStatement = connection.prepareStatement(sqlDelete);
            preparedStatement.setInt(1, user.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException sqle) {
            DAOUtils.sqlExceptionTreatement("Erro ao deletar user no BD.", sqle);
        } finally {
            DAOUtils.close(preparedStatement);
            DAOUtils.close(connection);
        }
    }

    @Override
    public List<User> findAll() throws ModelException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        List<User> usersList = new ArrayList<>();

        try {
            connection = MySQLConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM users ORDER BY nome;");

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                UserGender sexo = UserGender.valueOf(rs.getString("sexo"));
                String email = rs.getString("email");
                String senha = rs.getString("senha");

                User user = new User(id);
                user.setNome(nome);
                user.setSexo(sexo);
                user.setEmail(email);
                user.setSenha(senha);

                usersList.add(user);
            }

        } catch (SQLException sqle) {
            DAOUtils.sqlExceptionTreatement("Erro ao carregar users do BD.", sqle);
        } finally {
            DAOUtils.close(rs);
            DAOUtils.close(statement);
            DAOUtils.close(connection);
        }

        return usersList;
    }

    @Override
    public User findById(int id) throws ModelException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        User user = null;

        try {
            connection = MySQLConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?;");
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("nome");
                UserGender sexo = UserGender.valueOf(rs.getString("sexo"));
                String email = rs.getString("email");
                String senha = rs.getString("senha");

                user = new User(id);
                user.setNome(nome);
                user.setSexo(sexo);
                user.setEmail(email);
                user.setSenha(senha);
            }

        } catch (SQLException sqle) {
            DAOUtils.sqlExceptionTreatement("Erro ao buscar user por id no BD.", sqle);
        } finally {
            DAOUtils.close(rs);
            DAOUtils.close(preparedStatement);
            DAOUtils.close(connection);
        }

        return user;
    }
    
    @Override
    public User findByEmail(String email) throws ModelException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        User user = null;

        try {
            connection = MySQLConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE email = ?;");
            preparedStatement.setString(1, email);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                UserGender sexo = UserGender.valueOf(rs.getString("sexo"));
                String senha = rs.getString("senha");

                user = new User(id);
                user.setNome(nome);
                user.setSexo(sexo);
                user.setEmail(email);
                user.setSenha(senha);
            }
        } catch (SQLException sqle) {
            DAOUtils.sqlExceptionTreatement("Erro ao buscar user por email no BD.", sqle);
        } finally {
            DAOUtils.close(rs);
            DAOUtils.close(preparedStatement);
            DAOUtils.close(connection);
        }

        return user;
    }

}
