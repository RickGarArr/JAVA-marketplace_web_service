package datos.access;

import datos.access.exceptions.DuplicateEntryException;
import datos.models.UsuarioModel;
import java.sql.Connection;
import datos.conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioAccess {
    
    private Connection conexionTransaccional = null;
    private final String INSERT = "INSERT INTO USUARIOS(email, telefono, password, tipo) VALUES (?,?,?,?)";
    
    public UsuarioAccess() {}
    
    public UsuarioAccess(Connection conexion) {
        this.conexionTransaccional = conexion;
    }
    
    public int insertUsuario(UsuarioModel usuario) throws DuplicateEntryException {
        Connection conexion = this.conexionTransaccional == null ? Conexion.getConexion() : this.conexionTransaccional;
        PreparedStatement preparedStatement = null;
        int result = 0;
        try {
            preparedStatement = conexion.prepareStatement(INSERT);
            preparedStatement.setString(1, usuario.getEmail());
            preparedStatement.setString(2, usuario.getTelefono());
            preparedStatement.setString(3, usuario.getPassword());
            preparedStatement.setString(4, usuario.getTipo());
            result = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                if (ex.getMessage().contains("email")) {
                    throw new DuplicateEntryException("El email ya está registrado");
                } else if (ex.getMessage().contains("telefono")) {
                    throw new DuplicateEntryException("El telefono ya está registrado");
                }
            } else {
                ex.printStackTrace(System.out);
            }
        } finally {
            Conexion.close(preparedStatement);
            if (this.conexionTransaccional == null) {
                Conexion.close(conexion);
            }
        }
        return result;
    }

}
