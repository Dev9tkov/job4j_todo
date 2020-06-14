package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.job4j.model.Item;
import ru.job4j.model.User;
import ru.job4j.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class AllActiveItem extends HttpServlet {
    private final ItemService service = ItemService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/index.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        List<Item> items = new ArrayList<>();
        if (service.haveAnyItems(user.getId())) {
            items = service.findActiveItems(user.getId());
        }
        PrintWriter pw = resp.getWriter();
        ObjectMapper map = new ObjectMapper();
        String json = map.writeValueAsString(items);
        pw.append(json);
        pw.flush();
    }
}
