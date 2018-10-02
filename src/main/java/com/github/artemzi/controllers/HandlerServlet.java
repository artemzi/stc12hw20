package com.github.artemzi.controllers;

import com.github.artemzi.pojo.Task;
import com.github.artemzi.services.TaskService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HandlerServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(HandlerServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String text = "success";
        String task = request.getParameter("task"); // get passed task
        // TODO do something, ex: write to database
        TaskService service = new TaskService();
        service.addTask(task);

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(text);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String task = req.getParameter("task");
        // TODO do something, ex: write to database
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        try {
            resp.getWriter().write("success");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
