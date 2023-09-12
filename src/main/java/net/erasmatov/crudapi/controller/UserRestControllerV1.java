package net.erasmatov.crudapi.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.erasmatov.crudapi.annotation.ExcludeAnnotationExclusionStrategy;
import net.erasmatov.crudapi.model.User;
import net.erasmatov.crudapi.service.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

import static net.erasmatov.crudapi.utils.ServletUtil.getPathFromUrl;
import static net.erasmatov.crudapi.utils.ServletUtil.parseRequestBody;

public class UserRestControllerV1 extends HttpServlet {
    private final UserService userService = new UserService();
    ;
    private Gson configuredGson;

    @Override
    public void init() {
        configuredGson = new GsonBuilder()
                .addSerializationExclusionStrategy(ExcludeAnnotationExclusionStrategy.getStrategy()).create();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int idFromPath = getPathFromUrl(req);
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("text/json");

        if (idFromPath != 0) {
            User user = userService.getUserById(idFromPath);
            if (Objects.isNull(user)) {
                resp.sendError(404);
            } else {
                respWriter.print(configuredGson.toJson(user));
            }
        } else {
            respWriter.print(configuredGson.toJson(userService.getAllUsers()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = userService.saveUser(configuredGson.fromJson(parseRequestBody(req), User.class));
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("text/json");
        respWriter.print(configuredGson.toJson(user));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int idFromPath = getPathFromUrl(req);
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("text/json");

        if (idFromPath != 0) {
            User user = userService.getUserById(idFromPath);
            if (Objects.isNull(user)) {
                resp.sendError(404);
            } else {
                User dataUserFromReq = configuredGson.fromJson(parseRequestBody(req), User.class);

                if (Objects.nonNull(dataUserFromReq.getUsername()))
                    user.setUsername(dataUserFromReq.getUsername());

                if (Objects.nonNull(dataUserFromReq.getStatus()))
                    user.setStatus(dataUserFromReq.getStatus());

                respWriter.print(configuredGson.toJson(userService.updateUser(user)));
            }
        } else {
            resp.sendError(404);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int idFromPath = getPathFromUrl(req);
        PrintWriter respWriter = resp.getWriter();

        if (idFromPath != 0) {
            User user = userService.getUserById(idFromPath);
            if (Objects.isNull(user)) {
                resp.sendError(404);
            } else {
                userService.deleteUserById(idFromPath);
                resp.setStatus(204);
            }
        } else {
            resp.sendError(404);
        }
    }

}
