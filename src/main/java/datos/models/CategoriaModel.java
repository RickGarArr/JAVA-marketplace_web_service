package datos.models;

import java.util.Objects;

public class CategoriaModel {
    private int id_categoria;
    private String nombre;
    private String descripcion;
    private boolean esta_activa;

    public CategoriaModel() {
    }

    public CategoriaModel(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public CategoriaModel(boolean esta_activa) {
        this.esta_activa = esta_activa;
    }

    public CategoriaModel(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public CategoriaModel(int id_categoria, String nombre, String descripcion, boolean esta_activa) {
        this(nombre, descripcion);
        this.id_categoria = id_categoria;
        this.esta_activa = esta_activa;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEsta_activa() {
        return esta_activa;
    }

    public void setEsta_activa(boolean esta_activa) {
        this.esta_activa = esta_activa;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id_categoria;
        hash = 59 * hash + Objects.hashCode(this.nombre);
        hash = 59 * hash + Objects.hashCode(this.descripcion);
        hash = 59 * hash + (this.esta_activa ? 1 : 0);
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
        final CategoriaModel other = (CategoriaModel) obj;
        if (this.id_categoria != other.id_categoria) {
            return false;
        }
        if (this.esta_activa != other.esta_activa) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CategoriaModel{" + "id_categoria=" + id_categoria + ", nombre=" + nombre + ", descripcion=" + descripcion + ", esta_activa=" + esta_activa + '}';
    }
        
}
