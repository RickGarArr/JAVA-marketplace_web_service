package datos.models;

import java.util.Date;
import java.util.Objects;

public class SolicitudModel {
    private int id_solicitud;
    private String email;
    private String nombre;
    private String telefono;
    private int id_documentos;
    private String estado;
    private Date fecha_creacion;

    public SolicitudModel() {
    }

    public SolicitudModel(int id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public SolicitudModel(String estado) {
        this.estado = estado;
    }

    public SolicitudModel(String email, String nombre, String telefono, int id_documentos) {
        this.email = email;
        this.nombre = nombre;
        this.telefono = telefono;
        this.id_documentos = id_documentos;
    }

    public SolicitudModel(int id_solicitud, String email, String nombre, String telefono, int id_documentos, String estado, Date fecha_creacion) {
        this.id_solicitud = id_solicitud;
        this.email = email;
        this.nombre = nombre;
        this.telefono = telefono;
        this.id_documentos = id_documentos;
        this.estado = estado;
        this.fecha_creacion = fecha_creacion;
    }

    public int getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(int id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getId_documentos() {
        return id_documentos;
    }

    public void setId_documentos(int id_documentos) {
        this.id_documentos = id_documentos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id_solicitud;
        hash = 59 * hash + Objects.hashCode(this.email);
        hash = 59 * hash + Objects.hashCode(this.nombre);
        hash = 59 * hash + Objects.hashCode(this.telefono);
        hash = 59 * hash + this.id_documentos;
        hash = 59 * hash + Objects.hashCode(this.estado);
        hash = 59 * hash + Objects.hashCode(this.fecha_creacion);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SolicitudModel other = (SolicitudModel) obj;
        if (this.id_solicitud != other.id_solicitud) {
            return false;
        }
        if (this.id_documentos != other.id_documentos) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.telefono, other.telefono)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SolicitudModel{" + "id_solicitud=" + id_solicitud + ", email=" + email + ", nombre=" + nombre + ", telefono=" + telefono + ", id_documentos=" + id_documentos + ", estado=" + estado + ", fecha_creacion=" + fecha_creacion + '}';
    }
}
