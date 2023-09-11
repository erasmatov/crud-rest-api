package net.erasmatov.crudapi.controller;

import com.google.gson.Gson;
import net.erasmatov.crudapi.model.File;
import net.erasmatov.crudapi.service.FileService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static net.erasmatov.crudapi.utils.ServletUtil.getPathFromUrl;

public class FileRestControllerV1 extends HttpServlet {
    private final FileService fileService = new FileService();
    private Gson gson;
    private String fileUploadPath;

    @Override
    public void init() {
        gson = new Gson();
        fileUploadPath = getServletContext().getInitParameter("file-upload-path");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int idFromPath = getPathFromUrl(req);
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("text/json");

        if (idFromPath != 0) {
            File file = fileService.getFileById(idFromPath);
            if (file == null) {
                resp.sendError(404);
            }
            respWriter.print(gson.toJson(file));
        }

        if (idFromPath == 0) {
            List<File> fileList = fileService.getAllFiles();
            respWriter.print(gson.toJson(fileList));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        File file = fileService.uploadFile(req, fileUploadPath);
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("text/json");

        if (file != null) {
            respWriter.print(gson.toJson(file));
        } else {
            resp.sendError(404);
        }
    }

}
