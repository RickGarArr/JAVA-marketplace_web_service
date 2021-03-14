package web;

import datos.dao.CategoriaDAO;
import datos.dao.ICategoriaDAO;
import datos.dao.exceptions.DuplicateEntryException;
import datos.dao.exceptions.EmptyResultSetException;
import datos.respuesta.Respuesta;
import dominio.dto.CategoriaDTO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.Arrays;
import dominio.response.Error;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.*;

@WebServlet("/categoria")
public class CategoriaServlet extends HttpServlet {

    private final ICategoriaDAO categoriaDAO = new CategoriaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        if (request.getParameter("idCategoria") == null) {
            Error.sendError(response, Arrays.asList("El Id de la categoria es necesario"));
        } else {
            try {
                CategoriaDTO categoria = categoriaDAO.selectById(new CategoriaDTO(Integer.parseInt(request.getParameter("idCategoria"))));
                PrintWriter out = response.getWriter();
                JsonObjectBuilder ob = Json.createObjectBuilder();
                ob.add("idCategoria", categoria.getIdCategoria());
                ob.add("nombre", categoria.getNombre());
                ob.add("descripcion", categoria.getDescripcion());
                ob.add("estaActiva", categoria.isActiva());
                JsonObject categoriaJson = ob.build();
                ob.add("ok", true);
                ob.add("categoria", categoriaJson);
                Writer writer = new StringWriter();
                Json.createWriter(writer).writeObject(ob.build());
                String jsonString = writer.toString();
                out.print(jsonString);
            } catch (EmptyResultSetException ex) {
                Error.sendError(response, Arrays.asList(ex.getMessage()));
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json");
        List<String> errores = new ArrayList<>();
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String estaActiva = request.getParameter("estaActiva");
        
        if (nombre == null || nombre.trim().equals("")) {
            errores.add("El campo {nombre} es obligatorio");
        }
        if (descripcion == null || descripcion.trim().equals("")) {
            errores.add("El campo {descripcion} es obligatorio");
        }
        if (estaActiva == null || estaActiva.trim().equals("")) {
            errores.add("El campo {estaActiva} es obligatorio");
        }
        
        if (!errores.isEmpty()) {
            Error.sendError(response, errores);
        } else {
            try {
                CategoriaDTO categoria = new CategoriaDTO(nombre, Boolean.parseBoolean(estaActiva), descripcion);
                Respuesta respuesta = categoriaDAO.insert(categoria);
                JsonObjectBuilder ob = Json.createObjectBuilder();
                JsonArrayBuilder ab = Json.createArrayBuilder();
                Arrays.asList(respuesta.getMensaje()).forEach(mensaje -> {
                    ab.add(mensaje);
                });
                ob.add("ok", respuesta.EsExitoso());
                ob.add("response", ab.build());
                PrintWriter out = response.getWriter();
                Writer writer = new StringWriter();
                Json.createWriter(writer).writeObject(ob.build());
                out.print(writer.toString());
            } catch (DuplicateEntryException | IOException ex) {
                Error.sendError(response, Arrays.asList(ex.getMessage()));
            }            
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json");
        String idCategoria = request.getParameter("idCategoria");
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String estaActiva = request.getParameter("estaActiva");
        if (idCategoria == null || idCategoria.trim().equals("")) {
            Error.sendError(response, Arrays.asList("El parametro idCategoria es obligatorio"));
        } else {
            try {
                CategoriaDTO categoriaDB = categoriaDAO.selectById(new CategoriaDTO(Integer.parseInt(idCategoria)));
                if (nombre != null && !nombre.trim().equals("")){
                    categoriaDB.setNombre(nombre);
                }
                if (descripcion != null && !descripcion.trim().equals("")){
                    categoriaDB.setDescripcion(descripcion);
                }
                if (estaActiva != null && !estaActiva.trim().equals("")){
                    categoriaDB.setEstaActiva(Boolean.parseBoolean(estaActiva));
                }
                Respuesta respuesta = categoriaDAO.update(categoriaDB);
                JsonObjectBuilder ob = Json.createObjectBuilder();
                JsonArrayBuilder ab = Json.createArrayBuilder();
                for(String mensaje : respuesta.getMensaje()) {
                    ab.add(mensaje);
                }
                ob.add("ok", respuesta.EsExitoso());
                ob.add("resultado", ab.build());
                PrintWriter out = response.getWriter();
                Writer writer = new StringWriter();
                Json.createWriter(writer).writeObject(ob.build());
                out.print(writer.toString());
            } catch (EmptyResultSetException | DuplicateEntryException ex ) {
                Error.sendError(response, Arrays.asList(ex.getMessage()));
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        String[] idCategorias = request.getParameterValues("idCategoria");
        if (idCategorias.length == 0) {
            Error.sendError(response, Arrays.asList("El idCategoria es obligatorio"));
        } else {
            JsonObjectBuilder ob = Json.createObjectBuilder();
            JsonArrayBuilder ab = Json.createArrayBuilder();
            for(String idCategoria : idCategorias) {
                CategoriaDTO categoria = new CategoriaDTO(Integer.parseInt(idCategoria));                
                Respuesta respuesta = categoriaDAO.delete(categoria);
                Arrays.asList(respuesta.getMensaje()).forEach(mensaje -> {
                    ab.add(mensaje);
                });
                ob.add("ok", respuesta.EsExitoso());
                ob.add("resultado", ab.build());
            }
            try {
                PrintWriter out = response.getWriter();
                Writer writer = new StringWriter();
                Json.createWriter(writer).writeObject(ob.build());
                String jsonString = writer.toString();
                out.print(jsonString);
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }
}
