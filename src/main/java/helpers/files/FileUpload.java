package helpers.files;

import datos.access.exceptions.GeneralException;
import helpers.GenerarNombreUnico;
import javax.servlet.http.*;
import javax.servlet.http.HttpServlet;
import java.io.*;
import java.nio.file.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class FileUpload extends HttpServlet {
    
    public final String pathToSave;

    public FileUpload(String path) {
        // obtiene el path 
        Path localPath = Paths.get(System.getProperty("user.dir"));
        // convierte el path en string
        String rootPath = localPath.getParent().toString();
        // crea el path a salvar dependiendo de la carpeta que se reciba en el constructor
        this.pathToSave = Paths.get(rootPath, File.separatorChar + "marketplace" + File.separatorChar + "uploads" + File.separatorChar + path + File.separatorChar).toString();
        // crea un nuevo File para crear la carpeta en caso de que no exista
        File filesDir = new File(this.pathToSave);
        if (!filesDir.exists()) filesDir.mkdirs();
    }

    // metodo para guardar unicamente un archivo, necesita un nombre de folder y un Part
    public String fileUpload(String folder, Part part) throws NoSuchAlgorithmException, IOException {
        // genera un nombre unico para cada archivo
        String nombre = GenerarNombreUnico.generarNombreUnico();
        // extrae la extencion de cada archivo en minusculas
        String ext = part.getSubmittedFileName().split("\\.")[part.getSubmittedFileName().split("\\.").length - 1].toLowerCase();
        // se crea el string con el directorio de la carpeta a guardar
        String folderToSave = this.pathToSave + File.separator + folder;
        // se crea un nuevo File
        File folderFile = new File(folderToSave);
        // si no existe el folder lo crea
        if(!folderFile.exists()) folderFile.mkdirs();
        // se unen las partes nombre y extencion para guardar el archivo
        String fileName = nombre + "." + ext;
        // se genera el path to save incluyendo el archivo
        String pathFile = folderFile.getPath() + File.separator + fileName;
        // utilizo el helper para escribir el archivo en el directorio especificado
        FilesHelper.WriteFile(pathFile, part);
        // regresa el nombre del archivo para guardarlo en base de datos
        return fileName;
    }
    
    // metodo para guardar multiples archivos en una carpeta, recibe un map con el nombre del campo y las partes a guardar
    public Map<String, String> multipleFileUpload(String folder, Map<String, Part> partValues ) {
        // se inicializa in map para retornarlo con el campo y nombre nuevo
        Map<String, String> filesNames = new HashMap<>();
        // se recorrre en un ciclo foreach el map
        partValues.forEach((String fieldName, Part part) -> {
            try {
                // se utiliza el metodo de la misma clase para escribir un archivo
                String newName = this.fileUpload(folder, part);
                // por cada part se agrega al map el campo y el nuevo nombre
                filesNames.put(fieldName, newName);
            } catch (NoSuchAlgorithmException | IOException ex) {
                ex.printStackTrace(System.out);
            }
        });
        return filesNames;
    }
    
    public File getFile(String folder, String fileName) throws GeneralException {
        String filePath = this.pathToSave + File.separator + folder + File.separator + fileName;
        File file = new File(filePath);
        if (!file.exists()) throw new GeneralException("El archivo solicitado no existe");
        return file;
    }
}

