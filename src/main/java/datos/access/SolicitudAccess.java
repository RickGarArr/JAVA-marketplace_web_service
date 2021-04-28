package datos.access;

import datos.access.exceptions.DuplicateEntryException;
import datos.access.result.InsertResult;
import datos.conexion.Conexion;
import datos.models.SolicitudModel;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SolicitudAccess {
    private final String SELECT_ALL = "SELECT id_solicitud, nombre, email, telefono, id_documentos, estado, fecha_creacion FROM solicitudes";
    private final String SELECT_BY_ID = "SELECT id_solicitud, nombre, email, telefono, id_documentos, estado, fecha_creacion FROM solicitudes WHERE id_solicitud = ?";
    private final String INSERT = "INSERT INTO solicitudes (nombre, email, telefono, id_documentos) VALUES (?,?,?,?)";
    private final String INSERT_DATE = "INSERT INTO solicitudes (nombre, email, telefono, id_documentos, fecha_creacion) VALUES (?,?,?,?,?)";
    private final String UPDATE = "UPDATE solicitudes SET nombre = ?, email = ?, telefono = ?, id_documentos = ?, estado = ?, WHERE id_solicitud = ?";
    private final String DELETE = "DELETE FROM solicitudes WHERE id_solicitud = ?";
    
    private Connection conexionTransaccional = null;

    public SolicitudAccess() {
    }

    public SolicitudAccess(Connection connection) {
        this.conexionTransaccional = connection;
    }

    public List<SolicitudModel> selectAll() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<SolicitudModel> solicitudes = new ArrayList<>();
        try {
            connection = this.conexionTransaccional == null ? Conexion.getConexion() : this.conexionTransaccional;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                SolicitudModel solicitud = new SolicitudModel(
                        resultSet.getInt("id_solicitud"),
                        resultSet.getString("nombre"),
                        resultSet.getString("email"),
                        resultSet.getString("telefono"),
                        resultSet.getInt("id_documentos"),
                        resultSet.getString("estado"),
                        resultSet.getDate("fecha_creacion"));
                solicitudes.add(solicitud);
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
        return solicitudes;
    }
    
    public SolicitudModel selectById(SolicitudModel solicitud) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.conexionTransaccional == null ? Conexion.getConexion() : this.conexionTransaccional;
            preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1, solicitud.getId_solicitud());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.first()) {
                solicitud.setNombre(resultSet.getString("nombre"));
                solicitud.setEmail(resultSet.getString("email"));
                solicitud.setTelefono(resultSet.getString("telefono"));
                solicitud.setId_documentos(resultSet.getInt("id_documentos"));
                solicitud.setEstado(resultSet.getString("estado"));
                solicitud.setFecha_creacion(resultSet.getDate("fecha_creacion"));
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
        return solicitud;
    }

    public InsertResult insert(SolicitudModel solicitud) throws DuplicateEntryException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        InsertResult insertResult = null;
        try {
            connection = this.conexionTransaccional == null ? Conexion.getConexion() : this.conexionTransaccional;
            preparedStatement = solicitud.getFecha_creacion() != null ?
                    connection.prepareStatement(INSERT_DATE, PreparedStatement.RETURN_GENERATED_KEYS)
                    : connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, solicitud.getNombre());
            preparedStatement.setString(2, solicitud.getEmail());
            preparedStatement.setString(3, solicitud.getTelefono());
            preparedStatement.setInt(4, solicitud.getId_documentos());
            if(solicitud.getFecha_creacion() != null) {
                SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
                String fecha = format.format(solicitud.getFecha_creacion());
                preparedStatement.setString(5, fecha);
            }
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) throw new SQLException("Error al crear solicitud");
            generatedKeys = preparedStatement.getGeneratedKeys();
            int insertedId = generatedKeys.first() ? generatedKeys.getInt(1) : 0;
            if (insertedId == 0 ) throw new SQLException("Error al crear solicitud");
            insertResult = new InsertResult(affectedRows, insertedId);
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                if(ex.getMessage().contains("email")) throw new DuplicateEntryException("El email ya está registrado en el sistema");
                if(ex.getMessage().contains("telefono")) throw new DuplicateEntryException("El telefono ya está registrado en el sistema");
            }
            ex.printStackTrace(System.out);
        } finally {
            if (generatedKeys != null) Conexion.close(generatedKeys);
            Conexion.close(preparedStatement);
            if (this.conexionTransaccional == null) {
                Conexion.close(connection);
            }
        }
        return insertResult;
    }
    
    public int update(SolicitudModel solicitud) throws DuplicateEntryException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affectedRows = 0;
        try {
            connection = this.conexionTransaccional == null ? Conexion.getConexion() : this.conexionTransaccional;
            // SET nombre = ?, email = ?, telefono = ?, id_documentos = ?, estado = ?, WHERE id_solicitud = ?
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, solicitud.getNombre());
            preparedStatement.setString(2, solicitud.getEmail());
            preparedStatement.setString(3, solicitud.getTelefono());
            preparedStatement.setInt(3, solicitud.getId_documentos());
            preparedStatement.setInt(4, solicitud.getId_solicitud());
            affectedRows = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                if(ex.getMessage().contains("email")) throw new DuplicateEntryException("El email ya está registrado en el sistema");
                if(ex.getMessage().contains("telefono")) throw new DuplicateEntryException("El telefono ya está registrado en el sistema");
            } else {
                ex.printStackTrace(System.out);
            }
        } finally {
            Conexion.close(preparedStatement);
            if(this.conexionTransaccional == null) {
                Conexion.close(connection);
            }
        }
        return affectedRows;
    }
    
    public int delete(SolicitudModel solicitud) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affectedRows = 0;
        try {
            connection = this.conexionTransaccional == null ? Conexion.getConexion() : this.conexionTransaccional;
            preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setInt(1, solicitud.getId_solicitud());
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
