package model.data.mysql;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.Vendas;
import model.data.DAOUtils;
import model.data.VendasDAO;
import model.data.mysql.utils.MySQLConnectionFactory;

public class MySQLVendasDAO implements VendasDAO {

    @Override
    public void save(Vendas venda) throws ModelException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = MySQLConnectionFactory.getConnection();

            String sqlInsert = "INSERT INTO vendas (data_venda, valor_total) VALUES (?, ?);";

            preparedStatement = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(venda.getDataVenda()));
            preparedStatement.setDouble(2, venda.getValorTotal());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                venda.setId(generatedKeys.getInt(1));
            }

        } catch (SQLException sqle) {
            DAOUtils.sqlExceptionTreatement("Erro ao inserir venda no BD.", sqle);
        } finally {
            DAOUtils.close(preparedStatement);
            DAOUtils.close(connection);
        }
    }

    @Override
    public void update(Vendas venda) throws ModelException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = MySQLConnectionFactory.getConnection();

            String sqlUpdate = "UPDATE vendas SET data_venda = ?, valor_total = ? WHERE id = ?;";

            preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(venda.getDataVenda()));
            preparedStatement.setDouble(2, venda.getValorTotal());
            preparedStatement.setInt(3, venda.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException sqle) {
            DAOUtils.sqlExceptionTreatement("Erro ao atualizar venda no BD.", sqle);
        } finally {
            DAOUtils.close(preparedStatement);
            DAOUtils.close(connection);
        }
    }

    @Override
    public void delete(Vendas venda) throws ModelException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = MySQLConnectionFactory.getConnection();

            String sqlDelete = "DELETE FROM vendas WHERE id = ?;";

            preparedStatement = connection.prepareStatement(sqlDelete);
            preparedStatement.setInt(1, venda.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException sqle) {
            DAOUtils.sqlExceptionTreatement("Erro ao deletar venda no BD.", sqle);
        } finally {
            DAOUtils.close(preparedStatement);
            DAOUtils.close(connection);
        }
    }

    @Override
    public List<Vendas> findAll() throws ModelException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        List<Vendas> vendasList = new ArrayList<>();

        try {
            connection = MySQLConnectionFactory.getConnection();
            statement = connection.createStatement();

            String sqlSelect = "SELECT * FROM vendas ORDER BY data_venda DESC;";
            rs = statement.executeQuery(sqlSelect);

            while (rs.next()) {
                int id = rs.getInt("id");
                LocalDateTime dataVenda = rs.getTimestamp("data_venda").toLocalDateTime();
                double valorTotal = rs.getDouble("valor_total");

                Vendas venda = new Vendas(id, dataVenda, valorTotal);
                vendasList.add(venda);
            }

        } catch (SQLException sqle) {
            DAOUtils.sqlExceptionTreatement("Erro ao listar vendas do BD.", sqle);
        } finally {
            DAOUtils.close(rs);
            DAOUtils.close(statement);
            DAOUtils.close(connection);
        }

        return vendasList;
    }

    @Override
    public Vendas findById(int id) throws ModelException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Vendas venda = null;

        try {
            connection = MySQLConnectionFactory.getConnection();

            String sqlSelect = "SELECT * FROM vendas WHERE id = ?;";
            preparedStatement = connection.prepareStatement(sqlSelect);
            preparedStatement.setInt(1, id);

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                LocalDateTime dataVenda = rs.getTimestamp("data_venda").toLocalDateTime();
                double valorTotal = rs.getDouble("valor_total");

                venda = new Vendas(id, dataVenda, valorTotal);
            }

        } catch (SQLException sqle) {
            DAOUtils.sqlExceptionTreatement("Erro ao buscar venda por ID no BD.", sqle);
        } finally {
            DAOUtils.close(rs);
            DAOUtils.close(preparedStatement);
            DAOUtils.close(connection);
        }

        return venda;
    }
}
