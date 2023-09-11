package net.erasmatov.crudapi.controller;

import net.erasmatov.crudapi.utils.FlywayUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class UtilController extends HttpServlet {
    @Override
    public void init() throws ServletException {
        FlywayUtil.flywayMigrate();
        super.init();
    }

}
