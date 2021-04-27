package web.helpers;

import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUpload extends HttpServlet {
    
    private ServletFileUpload uploader = null;
    private String pathToSave;

    public FileUpload(String path) {
        Path localPath = Paths.get(System.getProperty("user.dir"));
        String rootPath = localPath.getParent().toString();
        this.pathToSave = Paths.get(rootPath, File.separatorChar + "marketplace" + File.separatorChar + "uploads" + File.separatorChar + path + File.separatorChar).toString();
        File filesDir = new File(this.pathToSave);
        if (!filesDir.exists()) filesDir.mkdirs();
        DiskFileItemFactory fileFactory = new DiskFileItemFactory();
        fileFactory.setRepository(filesDir);
        this.uploader = new ServletFileUpload(fileFactory);
    }

    public void fileUpload(Part part) throws ServletException, IOException {
        part.write(this.pathToSave + File.separator + part.getName());
    }
}

