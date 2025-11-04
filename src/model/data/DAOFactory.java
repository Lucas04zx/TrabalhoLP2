package model.data;

import model.data.mysql.MySQLProdutosDAO;
import model.data.mysql.MySQLUserDAO;
import model.data.mysql.MySQLVendasDAO;

public final class DAOFactory {
	
	public static UserDAO createUserDAO() {
		return new MySQLUserDAO();
	}

	public static VendasDAO createVendasDAO() {
		return new MySQLVendasDAO();
	}

	public static ProdutosDAO createProdutosDAO() {
		return new MySQLProdutosDAO();
	}
}
