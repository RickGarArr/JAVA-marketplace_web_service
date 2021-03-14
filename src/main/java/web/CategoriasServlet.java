package web;

import datos.dao.CategoriaDAO;
import datos.dao.ICategoriaDAO;
import dominio.dto.CategoriaDTO;
import java.io.*;
import java.util.*;
import javax.json.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/categorias")
public class CategoriasServlet extends HttpServlet{
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        ICategoriaDAO categoriaDAO = new CategoriaDAO();
        List<CategoriaDTO> categorias;
        
        categorias = request.getParameter("estado") == null ?  categoriaDAO.selectAll() : 
                categoriaDAO.selectByState(Boolean.parseBoolean(request.getParameter("estado")));
        
        JsonObjectBuilder ob = Json.createObjectBuilder();
        JsonArrayBuilder ab = Json.createArrayBuilder();
        categorias.forEach(categoria ->{
            ob.add("idCategoria", categoria.getIdCategoria());
            ob.add("nombre", categoria.getNombre());
            ob.add("estaActiva", categoria.isActiva());
            ob.add("descripcion", categoria.getDescripcion());
            ab.add(ob.build());
        });
        ob.add("total", categorias.size());
        ob.add("categorias", ab.build());
        PrintWriter out;
        Writer writer;
        try {
            out = response.getWriter();
            writer = new StringWriter();
            Json.createWriter(writer).writeObject(ob.build());
            String jsonString = writer.toString();
            out.print(jsonString);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
