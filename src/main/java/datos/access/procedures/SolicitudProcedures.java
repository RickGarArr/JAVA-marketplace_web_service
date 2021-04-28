package datos.access.procedures;

import datos.access.DocumentosAccess;
import datos.access.SolicitudAccess;
import datos.access.exceptions.DuplicateEntryException;
import datos.access.exceptions.GeneralException;
import datos.access.result.InsertResult;
import datos.conexion.Conexion;
import datos.models.DocumentosModel;
import datos.models.SolicitudModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SolicitudProcedures {
    private DocumentosModel docuemtos;
    private SolicitudModel solicitud;
    
    public SolicitudProcedures() {
    }
    
    public SolicitudProcedures(DocumentosModel documentos, SolicitudModel solicitud) {
        this.docuemtos = documentos;
        this.solicitud = solicitud;
    }

    public DocumentosModel getDocuemtos() {
        return docuemtos;
    }

    public void setDocuemtos(DocumentosModel docuemtos) {
        this.docuemtos = docuemtos;
    }

    public SolicitudModel getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudModel solicitud) {
        this.solicitud = solicitud;
    }
    
    public InsertResult crearSolicitud() throws GeneralException {
        InsertResult insertSolicitud = null;
        Connection connection = null;
        try {
            connection = Conexion.getConexion();
            if (connection.getAutoCommit()) {
                connection.setAutoCommit(false);
            }
            DocumentosAccess documentosAccess = new DocumentosAccess(connection);
            InsertResult insertDocumentos = documentosAccess.insert(this.docuemtos);
            this.solicitud.setId_documentos(insertDocumentos.getInsertedId());
            SolicitudAccess solicitudAccess =  new SolicitudAccess(connection);
            insertSolicitud = solicitudAccess.insert(this.solicitud);
            connection.commit();
        } catch (SQLException | DuplicateEntryException ex) {
            if (connection != null) try {
                connection.rollback();
            } catch (SQLException ex1) {
                throw new GeneralException("Error al deshacer los cambios");
            }
            if (ex instanceof DuplicateEntryException) throw new GeneralException(ex.getMessage());
            throw new GeneralException("Error al crear la solicitud");
        } finally {
            Conexion.close(connection);
        }
        return insertSolicitud;
    }
}
