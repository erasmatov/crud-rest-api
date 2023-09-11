package net.erasmatov.crudapi.controller;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.erasmatov.crudapi.annotation.Exclude;
import net.erasmatov.crudapi.model.User;
import net.erasmatov.crudapi.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static net.erasmatov.crudapi.utils.ServletUtil.getPathFromUrl;
import static net.erasmatov.crudapi.utils.ServletUtil.parseRequestBody;

public class UserRestControllerV1 extends HttpServlet {
    private final UserService userService = new UserService();;
    private Gson configuredGson;

    @Override
    public void init() {
        ExclusionStrategy strategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                return fieldAttributes.getAnnotation(Exclude.class) != null;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        };

        configuredGson = new GsonBuilder()
                .addSerializationExclusionStrategy(strategy).create();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int idFromPath = getPathFromUrl(req);
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("text/json");

        if (idFromPath != 0) {
            User user = userService.getUserById(idFromPath);
            if (user == null) {
                resp.sendError(404);
            }
            respWriter.print(configuredGson.toJson(user));
        }

        if (idFromPath == 0) {
            List<User> userList = userService.getAllUsers();
            respWriter.print(configuredGson.toJson(userList));
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
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

}
