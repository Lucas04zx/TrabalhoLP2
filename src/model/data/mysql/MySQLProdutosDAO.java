package model.data.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.Produtos;
import model.data.DAOUtils;
import model.data.ProdutosDAO;
import model.data.mysql.utils.MySQLConnectionFactory;

public class MySQLProdutosDAO implements ProdutosDAO {

    @Override
    public void save(Produtos produto) throws ModelException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = MySQLConnectionFactory.getConnection();

            String sqlInsert = "INSERT INTO produtos (nome, descricao, preco) VALUES (?, ?, ?);";
            preparedStatement = connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, produto.getNome());
            preparedStatement.setString(2, produto.getDescricao());
            preparedStatement.setBigDecimal(3, produto.getPreco());

            preparedStatement.executeUpdate();

        } catch (SQLException sqle) {
            DAOUtils.sqlExceptionTreatement("Erro ao inserir produto no BD.", sqle);
        } finally {
            DAOUtils.close(preparedStatement);
            DAOUtils.close(connection);
        }
    }

    @Override
    public void update(Produtos produto) throws ModelException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = MySQLConnectionFactory.getConnection();

            String sqlUpdate = "UPDATE produtos SET nome = ?, descricao = ?, preco = ? WHERE id = ?;";
            preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, produto.getNome());
            preparedStatement.setString(2, produto.getDescricao());
            preparedStatement.setBigDecimal(3, produto.getPreco());
            preparedStatement.setInt(4, produto.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException sqle) {
            DAOUtils.sqlExceptionTreatement("Erro ao atualizar produto no BD.", sqle);
        } finally {
            DAOUtils.close(preparedStatement);
            DAOUtils.close(connection);
        }
    }

    @Override
    public void delete(Produtos produto) throws ModelException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = MySQLConnectionFactory.getConnection();

            String sqlDelete = "DELETE FROM produtos WHERE id = ?;";
            preparedStatement = connection.prepareStatement(sqlDelete);
            preparedStatement.setInt(1, produto.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException sqle) {
            DAOUtils.sqlExceptionTreatement("Erro ao deletar produto no BD.", sqle);
        } finally {
            DAOUtils.close(preparedStatement);
            DAOUtils.close(connection);
        }
    }

    @Override
    public List<Produtos> findAll() throws ModelException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        List<Produtos> produtosList = new ArrayList<>();

        try {
            connection = MySQLConnectionFactory.getConnection();
            statement = connection.createStatement();
            String sqlSelect = "SELECT * FROM produtos ORDER BY nome;";
            rs = statement.executeQuery(sqlSelect);

            while (rs.next()) {
                Produtos p = new Produtos(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getBigDecimal("preco"));
                produtosList.add(p);
            }

        } catch (SQLException sqle) {
            DAOUtils.sqlExceptionTreatement("Erro ao carregar produtos do BD.", sqle);
        } finally {
            DAOUtils.close(rs);
            DAOUtils.close(statement);
            DAOUtils.close(connection);
        }

        return produtosList;
    }

    @Override
    public Produtos findById(int id) throws ModelException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Produtos produto = null;

        try {
            connection = MySQLConnectionFactory.getConnection();
            String sqlSelect = "SELECT * FROM produtos WHERE id = ?;";
            preparedStatement = connection.prepareStatement(sqlSelect);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                produto = new Produtos(id);
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getBigDecimal("preco"));
            }

        } catch (SQLException sqle) {
            DAOUtils.sqlExceptionTreatement("Erro ao buscar produto por id no BD.", sqle);
        } finally {
            DAOUtils.close(rs);
            DAOUtils.close(preparedStatement);
            DAOUtils.close(connection);
        }

        return produto;
    }
}
