package com.github.artemzi.controllers;

import com.github.artemzi.services.TaskService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HandlerServlet extends HttpServlet {
    private String responseStatus;
    private TaskService service;
    private static final Logger LOGGER = Logger.getLogger(HandlerServlet.class.getName());

    public HandlerServlet() {
        this.service = new TaskService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String task = request.getParameter("task"); // get passed task
        if (task.isEmpty()) {
            setResponseStatus("failed");
            LOGGER.error("Cannot read task from ajax in get");
        } else {
            service.addTask(task);
            setResponseStatus("success");
            LOGGER.info("Successfully add task");
        }

        createResponse(response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String task = req.getParameter("task");
        if (task.isEmpty()) {
            setResponseStatus("failed");
            LOGGER.error("Cannot read task from ajax in post");
        } else {
            service.deleteTask(task);
            setResponseStatus("success");
            LOGGER.info("Successfully delete task");
        }

        createResponse(resp);
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    private void createResponse(HttpServletResponse resp) {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        try {
            resp.getWriter().write(getResponseStatus());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
