package web.controllers;

import datos.access.exceptions.GeneralException;
import datos.access.procedures.SolicitudProcedures;
import datos.models.DocumentosModel;
import datos.models.SolicitudModel;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import helpers.validators.exceptions.NullParameterValueException;
import helpers.DateHelper;
import helpers.files.FileUpload;
import helpers.response.SendMessage;
import helpers.validators.ValidarParametros;
import helpers.validators.ValidarPattern;
import helpers.files.FilesHelper;
import web.helpers.exceptions.InvalidPatternException;

@MultipartConfig()
public class SolicitudController {
    public static void createSolicitud(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        if(!ServletFileUpload.isMultipartContent(request)) throw new ServletException("Content type is not multipart/form-data");
            response.setContentType("application/json");
            String[] partes = {"doc_ide", "doc_rfc", "doc_dom"};
            String[] extValidas = {"pdf", "jpg", "jpeg"};
            String[] parametros = {"nombre", "email", "telefono"};
            FileUpload fu = new FileUpload("solicitudes");
            Date fecha = new Date();
            String folder = DateHelper.getDateFormat("yyyyMMddHHmmss");
        try {
            Map<String, Part> partValues = ValidarParametros.validarPartes(partes, extValidas ,request);
            Map<String, String> parameterValues = ValidarParametros.validarParametros(parametros, request);
            ValidarPattern.validarEmail(parameterValues.get("email"));
//            ValidarPattern.validarTelefono(parameterValues.get("telefono"));
            Map<String, String> filesNames = fu.multipleFileUpload(folder, partValues);
            SolicitudModel solicitud = new SolicitudModel(parameterValues.get("email"), parameterValues.get("nombre"), parameterValues.get("telefono"), fecha);
            DocumentosModel documentos = new DocumentosModel(filesNames.get("doc_ide"), filesNames.get("doc_rfc"), filesNames.get("doc_dom"));
            SolicitudProcedures solicitudProcedures = new SolicitudProcedures(documentos, solicitud);
            solicitudProcedures.crearSolicitud();
            SendMessage.sendMessages(response, 200, Arrays.asList("Solicitud Creada correctamente"));
        } catch (NullParameterValueException | InvalidPatternException | GeneralException ex) {
            FilesHelper.deleteFolder(fu.pathToSave, folder);
            SendMessage.sendErrors(response, Arrays.asList(ex.getMessage()));
        }
    }
}
