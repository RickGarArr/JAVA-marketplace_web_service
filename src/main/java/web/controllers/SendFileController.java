package web.controllers;

import datos.access.SolicitudAccess;
import datos.access.exceptions.GeneralException;
import datos.models.SolicitudModel;
import helpers.DateHelper;
import helpers.files.FileUpload;
import helpers.response.SendMessage;
import helpers.validators.ValidarParametros;
import helpers.validators.exceptions.InvalidParameterValueException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;

public class SendFileController {

    public static void sendFile(HttpServletRequest request, HttpServletResponse response) {
        // crear un linked map para agregr los url parameters
        Map<String, Object> expectedParams = new LinkedHashMap<>();
        expectedParams.put("id_solicitud", 0);
        expectedParams.put("fileName", "");
        try {
            Map<String, Object> urlParameterValues = ValidarParametros.validarURLParametros(expectedParams, request);
            SolicitudAccess solicitudAccess = new SolicitudAccess();
            SolicitudModel solicitud = solicitudAccess.selectById(new SolicitudModel((Integer) urlParameterValues.get("id_solicitud")));
            System.out.println(solicitud);
            String folder = DateHelper.getDateFormat(solicitud.getFecha_creacion(), "yyyyMMddHHmmss");
            ServletContext ctx = request.getServletContext();
            FileUpload fu = new FileUpload("solicitudes");
            File file = fu.getFile(folder, (String) urlParameterValues.get("fileName"));
            String mimeType = ctx.getMimeType(file.getAbsolutePath());
            response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
            try ( InputStream fis = new FileInputStream(file);  ServletOutputStream os = response.getOutputStream()) {
                byte[] bufferData = new byte[1024];
                int read = 0;
                while ((read = fis.read(bufferData)) != -1) {
                    os.write(bufferData, 0, read);
                }
                os.flush();
//            System.out.println("folder: ");
//            urlParameterValues.forEach((String key, Object value) -> {
//                System.out.println("key: " + key + ", value " + value);
//            });
            }
        } catch (GeneralException | InvalidParameterValueException ex) {
            SendMessage.sendErrors(response, Arrays.asList(ex.getMessage()));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
