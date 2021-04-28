package web.helpers;

import web.helpers.datos.FilesHelper;
import javax.servlet.http.*;
import javax.servlet.http.HttpServlet;
import java.io.*;
import java.nio.file.*;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUpload extends HttpServlet {
    
//    private ServletFileUpload uploader ;
    public final String pathToSave;

    public FileUpload(String path) {
        Path localPath = Paths.get(System.getProperty("user.dir"));
        String rootPath = localPath.getParent().toString();
        this.pathToSave = Paths.get(rootPath, File.separatorChar + "marketplace" + File.separatorChar + "uploads" + File.separatorChar + path + File.separatorChar).toString();
        File filesDir = new File(this.pathToSave);
        if (!filesDir.exists()) filesDir.mkdirs();
//        DiskFileItemFactory fileFactory = new DiskFileItemFactory();
//        fileFactory.setRepository(filesDir);
//        this.uploader = new ServletFileUpload(fileFactory);
    }

    public String fileUpload(String folder, Part part) throws NoSuchAlgorithmException, IOException {
        String nombre = GenerarNombreUnico.generarNombreUnico();
        String ext = part.getSubmittedFileName().split("\\.")[part.getSubmittedFileName().split("\\.").length - 1].toLowerCase();
        //dif
        String folderToSave = this.pathToSave + File.separator + folder;
        File folderFile = new File(folderToSave);
        if(!folderFile.exists()) folderFile.mkdirs();
        String fileName = nombre + "." + ext;
        String pathFile = folderFile.getPath() + File.separator + fileName;
        FilesHelper.WriteFile(pathFile, part);
        return fileName;
    }
    
    public Map<String, String> multipleFileUpload(Date date, Map<String, Part> partValues ) {
        SimpleDateFormat format = new SimpleDateFormat("YYYYMMddhhmmss");
        String folder = format.format(date);
        Map<String, String> filesNames = new HashMap<>();
        partValues.forEach((String fieldName, Part part) -> {
            try {
                String newName = this.fileUpload(folder, part);
                filesNames.put(fieldName, newName);
            } catch (NoSuchAlgorithmException | IOException ex) {
                ex.printStackTrace(System.out);
            }
        });
        return filesNames;
    }
    
    public Map<String, String> multipleFileUpload(String folder, Map<String, Part> partValues ) {
        Map<String, String> filesNames = new HashMap<>();
        partValues.forEach((String fieldName, Part part) -> {
            try {
                String newName = this.fileUpload(folder, part);
                filesNames.put(fieldName, newName);
            } catch (NoSuchAlgorithmException | IOException ex) {
                ex.printStackTrace(System.out);
            }
        });
        return filesNames;
    }
}

