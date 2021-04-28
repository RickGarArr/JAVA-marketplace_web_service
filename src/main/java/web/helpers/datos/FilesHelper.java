package web.helpers.datos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Part;

public class FilesHelper {

    public static void WriteFile(String filePath, Part part) {
        OutputStream out = null;
        InputStream fileContent = null;
        try {
            out = new FileOutputStream(new File(filePath));
            fileContent = part.getInputStream();
            int read = 0;
            final byte[] bytes = new byte[1024];
            while ((read = fileContent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FilesHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FilesHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(FilesHelper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (fileContent != null) {
                try {
                    fileContent.close();
                } catch (IOException ex) {
                    Logger.getLogger(FilesHelper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void deleteFolder(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (final File file : files) {
                deleteFolder(file);
            }
        }
        dir.delete();
    }
    
    public static void deleteFolder(String dir) {
        File folder = new File(dir);
        File[] files = folder.listFiles();
        if (files != null) {
            for (final File file : files) {
                deleteFolder(file);
            }
        }
        folder.delete();
    }
    
    public static void deleteFolder(String ...dirs) {
        String folderPath = "";
        for(String path: dirs) {
            folderPath += folderPath.equals("") ? path : File.separator + path;
        }
        File folder = new File(folderPath);
        System.out.println("borrando carpeta: " + folder.getPath());
        File[] files = folder.listFiles();
        if (files != null) {
            for (final File file : files) {
                deleteFolder(file);
            }
        }
        folder.delete();
    }
    public static void deleteFile(String ...dirs) {
        String folderPath = "";
        for(String path: dirs) {
            folderPath += folderPath.equals("") ? path : File.separator + path;
        }
        File file = new File(folderPath);
        System.out.println("borrando archivo: " + file.getPath());
        if(file.exists()) file.delete();
    }
}
