package net.erasmatov.crudapi.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class ServletUtil {
    public static int getPathFromUrl(HttpServletRequest req) {
        String[] pathFromUrl = req.getPathInfo().split("/");
        int pathlenght = 0;

        if (pathFromUrl.length != 0) {
            pathlenght = Integer.parseInt(pathFromUrl[1]);
        }
        return pathlenght;
    }

    public static String parseRequestBody(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

}
