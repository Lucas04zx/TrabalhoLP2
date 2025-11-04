package model.data;

import java.util.List;
import model.ModelException;
import model.Vendas;

public interface VendasDAO {
    
    void save(Vendas venda) throws ModelException;
    
    void update(Vendas venda) throws ModelException;
    
    void delete(Vendas venda) throws ModelException;
    
    List<Vendas> findAll() throws ModelException;
    
    Vendas findById(int id) throws ModelException;
}
