package datos.access;

import datos.access.result.InsertResult;
import datos.models.DocumentosModel;
import datos.conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DocumentosAccess {

    private final String SELECT_ALL = "SELECT id_documentos, doc_ide, doc_rfc, doc_dom FROM documentos";
    private final String SELECT_BY_ID = "SELECT id_documentos, doc_ide, doc_rfc, doc_dom FROM documentos where id_documentos = ?";
    private final String INSERT = "INSERT INTO documentos (doc_ide, doc_rfc, doc_dom) VALUES (?,?,?)";
    private final String UPDATE = "UPDATE documentos SET doc_ide = ?, doc_rfc = ?, doc_dom = ? WHERE id_documentos = ?";
    private final String DELETE = "DELETE FROM documentos WHERE id_documentos = ?";
    
    private Connection conexionTransaccional = null;

    public DocumentosAccess() {
    }

    public DocumentosAccess(Connection connection) {
        this.conexionTransaccional = connection;
    }

    public List<DocumentosModel> selectAll() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<DocumentosModel> documentosList = new ArrayList<>();
        try {
            connection = this.conexionTransaccional == null ? Conexion.getConexion() : this.conexionTransaccional;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                DocumentosModel documentos = new DocumentosModel(
                        resultSet.getInt("id_documentos"),
                        resultSet.getString("doc_ide"),
                        resultSet.getString("doc_rfc"),
                        resultSet.getString("doc_dom"));
                documentosList.add(documentos);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(resultSet);
            Conexion.close(statement);
            if (this.conexionTransaccional == null) {
                Conexion.close(connection);
            }
        }
        return documentosList;
    }
    
    public DocumentosModel selectById(DocumentosModel documentos) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.conexionTransaccional == null ? Conexion.getConexion() : this.conexionTransaccional;
            preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1, documentos.getId_documentos());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.first()) {
                documentos.setDoc_ide(resultSet.getString("doc_ide"));
                documentos.setDoc_rfc(resultSet.getString("doc_rfc"));
                documentos.setDoc_dom(resultSet.getString("doc_dom"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(resultSet);
            Conexion.close(preparedStatement);
            if (this.conexionTransaccional == null) {
                Conexion.close(connection);
            }
        }
        return documentos;
    }

    public InsertResult insert(DocumentosModel documentos) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        InsertResult insertResult = null;
        try {
            connection = this.conexionTransaccional == null ? Conexion.getConexion() : this.conexionTransaccional;
            preparedStatement = connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, documentos.getDoc_ide());
            preparedStatement.setString(2, documentos.getDoc_rfc());
            preparedStatement.setString(3, documentos.getDoc_dom());
            int affectedRows = preparedStatement.executeUpdate();
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (affectedRows == 0) throw new SQLException("Error al crear los documentos");
            int insertedId = generatedKeys.first() ? generatedKeys.getInt(1) : 0;
            if (insertedId == 0 ) throw new SQLException("Error al crear los documentos");
            insertResult = new InsertResult(affectedRows, insertedId);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(generatedKeys);
            Conexion.close(preparedStatement);
            if (this.conexionTransaccional == null) {
                Conexion.close(connection);
            }
        }
        return insertResult;
    }
    
    public int update(DocumentosModel documentos) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affectedRows = 0;
        try {
            connection = this.conexionTransaccional == null ? Conexion.getConexion() : this.conexionTransaccional;
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, documentos.getDoc_ide());
            preparedStatement.setString(2, documentos.getDoc_rfc());
            preparedStatement.setString(3, documentos.getDoc_dom());
            preparedStatement.setInt(4, documentos.getId_documentos());
            affectedRows = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(preparedStatement);
            if(this.conexionTransaccional == null) {
                Conexion.close(connection);
            }
        }
        return affectedRows;
    }
    
    public int delete(DocumentosModel documentos) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affectedRows = 0;
        try {
            connection = this.conexionTransaccional == null ? Conexion.getConexion() : this.conexionTransaccional;
            preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setInt(1, documentos.getId_documentos());
            affectedRows = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(preparedStatement);
            if(this.conexionTransaccional == null) {
                Conexion.close(connection);
            }
        }
        return affectedRows;
    }
}
