package datos.respuesta;
public class Respuesta {
    private boolean esExitoso;
    private String[] mensajes;
    
    public Respuesta(boolean esExitoso, String... mensajes) {
        this.esExitoso = esExitoso;
        this.mensajes = mensajes;
    }

    public boolean EsExitoso() {
        return esExitoso;
    }

    public void setEsExitoso(boolean esExitoso) {
        this.esExitoso = esExitoso;
    }

    public String[] getMensaje() {
        return mensajes;
    }

    public void setMensaje(String[] mensaje) {
        this.mensajes = mensaje;
    }
    
    
}
