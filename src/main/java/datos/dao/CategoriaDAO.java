package datos.dao;

import datos.Conexion;
import datos.dao.exceptions.DuplicateEntryException;
import datos.dao.exceptions.EmptyResultSetException;
import datos.respuesta.Respuesta;
import dominio.dto.CategoriaDTO;
import java.sql.*;
import java.util.*;

public class CategoriaDAO implements ICategoriaDAO{
    
    private final String SELECT_ALL = "SELECT idCategoria, nombre, descripcion, estaActiva "
            + "FROM categorias";
    private final String SELECT_BY_STATE = "SELECT idCategoria, nombre, descripcion, estaActiva "
            + "FROM categorias where estaActiva = ?";
    
    private final String SELECT_BY_ID = "SELECT idCategoria, nombre, descripcion, estaActiva "
            + "FROM categorias where idCategoria = ?";

    private final String INSERT = "INSERT INTO categorias(nombre, descripcion, estaActiva) "
            + "VALUES(?, ?, ?)";
    
    private final String DELETE = "DELETE FROM categorias WHERE idCategoria = ?";
    
    private final String UPDATE = "UPDATE categorias SET nombre=?, descripcion=?, estaActiva=? WHERE idCategoria=?";
    
    @Override
    public List<CategoriaDTO> selectAll() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<CategoriaDTO> categorias = new ArrayList<>();
        try {    
            connection = Conexion.getConexion();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL);
            while(resultSet.next()) {
                CategoriaDTO categoria = new CategoriaDTO(
                        resultSet.getInt("idCategoria"),
                        resultSet.getString("nombre"),
                        resultSet.getBoolean("estaActiva"),
                        resultSet.getString("descripcion")
                );
                categorias.add(categoria);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(resultSet);
            Conexion.close(statement);
            Conexion.close(connection);
        }
        return categorias;
    }

    @Override
    public List<CategoriaDTO> selectByState(boolean estado) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<CategoriaDTO> categorias = new ArrayList<>();
        try {    
            connection = Conexion.getConexion();
            preparedStatement = connection.prepareStatement(SELECT_BY_STATE);
            preparedStatement.setBoolean(1, estado);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                CategoriaDTO categoria = new CategoriaDTO(
                        resultSet.getInt("idCategoria"),
                        resultSet.getString("nombre"),
                        resultSet.getBoolean("estaActiva"),
                        resultSet.getString("descripcion")
                );
                categorias.add(categoria);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(resultSet);
            Conexion.close(preparedStatement);
            Conexion.close(connection);
        }
        return categorias;
    }

    @Override
    public CategoriaDTO selectById(CategoriaDTO categoria) throws EmptyResultSetException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {    
            connection = Conexion.getConexion();
            preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1, categoria.getIdCategoria());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next() == false) {
                throw new EmptyResultSetException("El id no se encontró");
            } else {
                resultSet.absolute(1);
                categoria.setNombre(resultSet.getString("nombre"));
                categoria.setEstaActiva(resultSet.getBoolean("estaActiva"));
                categoria.setDescripcion(resultSet.getString("descripcion"));
            }
        } catch (SQLException ex) {
//            ex.printStackTrace(System.out);
            System.out.println(ex.getMessage());
        } finally {
            Conexion.close(resultSet);
            Conexion.close(preparedStatement);
            Conexion.close(connection);
        }
        return categoria;
    }

    @Override
    public Respuesta insert(CategoriaDTO categoria) throws DuplicateEntryException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Respuesta respuesta = null;
        try {
            connection = Conexion.getConexion();
            preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, categoria.getNombre());
            preparedStatement.setString(2, categoria.getDescripcion());
            preparedStatement.setBoolean(3, categoria.isActiva());
            int rows = preparedStatement.executeUpdate();
            respuesta = new Respuesta(true, "Registro exitoso, rows afectados: " + rows);
        } catch(SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                throw new DuplicateEntryException("El Nombre de la Categoria ya esta registrado");
            }
            respuesta = new Respuesta(false, "Error al insertar en la base de datos", "Error: " + ex.getErrorCode() + ", " + ex.getMessage());
        } finally {
            Conexion.close(preparedStatement);
            Conexion.close(connection);
        }
        return respuesta;
    }

    @Override
    public Respuesta update(CategoriaDTO categoria) throws DuplicateEntryException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Respuesta respuesta = null;
        try {
            connection = Conexion.getConexion();
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, categoria.getNombre());
            preparedStatement.setString(2, categoria.getDescripcion());
            preparedStatement.setBoolean(3, categoria.isActiva());
            preparedStatement.setInt(4, categoria.getIdCategoria());
            int rows = preparedStatement.executeUpdate();
            respuesta = new Respuesta(true, "Registro Actualizado Correctemente, rows afected: " + rows);
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                throw new DuplicateEntryException("El Nombre de la Categoria ya esta registrado");
            }
            respuesta = new Respuesta(false, "Error al actualizar", "Error: " + ex.getMessage() +", codigo" + ex.getErrorCode());
        }
        return respuesta;
    }

    @Override
    public Respuesta delete(CategoriaDTO categoria) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Respuesta respuesta = null;
        try {
            connection = Conexion.getConexion();
            preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setInt(1, categoria.getIdCategoria());
            int rows = preparedStatement.executeUpdate();
            respuesta = new Respuesta(true, "Registros Eliminado Correctamente");
        } catch(SQLException ex) {
            ex.printStackTrace(System.out);
            respuesta = new Respuesta(false, ex.getMessage() + ", " + ex.getErrorCode());
        } finally {
            Conexion.close(preparedStatement);
            Conexion.close(connection);
        }
        return respuesta;
    }
    
}
