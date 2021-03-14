package dominio.dto;
public class CategoriaDTO {
    private int idCategoria;
    private String nombre;
    private boolean estaActiva;
    private String descripcion;
    
    public CategoriaDTO() { }
    
    public CategoriaDTO(int idCategoria) {
        this.idCategoria = idCategoria;
    }
    
    public CategoriaDTO(String nombre, boolean estaActiva, String descripcion){
        this.nombre = nombre;
        this.estaActiva = estaActiva;
        this.descripcion = descripcion;
    }
    
    public CategoriaDTO(int idCategoria, String nombre, boolean estaActiva, String descripcion) {
        this(nombre, estaActiva, descripcion);
        this.idCategoria = idCategoria;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isActiva() {
        return estaActiva;
    }

    public void setEstaActiva(boolean estaActiva) {
        this.estaActiva = estaActiva;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
