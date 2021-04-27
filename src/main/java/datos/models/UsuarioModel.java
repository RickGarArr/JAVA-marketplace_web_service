package datos.models;

import java.util.Date;

public class UsuarioModel {
    private int id_usuario;
    private String email;
    private String telefono;
    private String password;
    private String tipo;
    private Date fecha_creacion;

    public UsuarioModel() {
    }
    
    public UsuarioModel(int id_usuario) {
        this.id_usuario = id_usuario;
    }
    
    public UsuarioModel(String tipo) {
        this.tipo = tipo;
    }

    public UsuarioModel(String email, String telefono, String password, String tipo) {
        this.email = email;
        this.telefono = telefono;
        this.password = password;
        this.tipo = tipo;
    }

    public UsuarioModel(int id_usuario, String email, String telefono, String password, String tipo, Date fecha_creacion) {
        this(email, telefono, password, tipo);
        this.id_usuario = id_usuario;
        this.fecha_creacion = fecha_creacion;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    @Override
    public String toString() {
        return "UsuarioModel{" + "id_usuario=" + id_usuario + ", email=" + email + ", telefono=" + telefono + ", password=" + password + ", tipo=" + tipo + ", fecha_creacion=" + fecha_creacion + '}';
    }
    
}
