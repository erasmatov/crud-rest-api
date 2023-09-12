package net.erasmatov.crudapi.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.erasmatov.crudapi.annotation.ExcludeAnnotationExclusionStrategy;
import net.erasmatov.crudapi.model.Event;
import net.erasmatov.crudapi.service.EventService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

import static net.erasmatov.crudapi.utils.ServletUtil.getPathFromUrl;

public class EventRestControllerV1 extends HttpServlet {
    private final EventService eventService = new EventService();
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
            Event event = eventService.getEventById(idFromPath);
            if (Objects.isNull(event)) {
                resp.sendError(404);
            } else {
                respWriter.print(configuredGson.toJson(event));
            }
        } else {
            respWriter.print(configuredGson.toJson(eventService.getAllEvents()));
        }
    }

}
