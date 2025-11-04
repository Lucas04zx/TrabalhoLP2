package model.data;

import java.util.List;

import model.ModelException;
import model.Produtos;

public interface ProdutosDAO {
    void save(Produtos produto) throws ModelException;
    void update(Produtos produto) throws ModelException;
    void delete(Produtos produto) throws ModelException;
    List<Produtos> findAll() throws ModelException;
    Produtos findById(int id) throws ModelException;
}
