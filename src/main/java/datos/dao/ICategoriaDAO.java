package datos.dao;

import datos.dao.exceptions.DuplicateEntryException;
import datos.dao.exceptions.EmptyResultSetException;
import datos.respuesta.Respuesta;
import dominio.dto.CategoriaDTO;
import java.util.List;

public interface ICategoriaDAO {
    
    List<CategoriaDTO> selectAll();
    
    List<CategoriaDTO> selectByState(boolean estado);
    
    CategoriaDTO selectById(CategoriaDTO categoria) throws EmptyResultSetException;
    
    Respuesta insert(CategoriaDTO categoria) throws DuplicateEntryException ;
    
    Respuesta update(CategoriaDTO categoria) throws DuplicateEntryException;
    
    Respuesta delete(CategoriaDTO categoria);
}
