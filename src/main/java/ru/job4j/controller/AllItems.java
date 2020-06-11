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
import java.util.List;

public class AllItems extends HttpServlet {
    private final ItemService service = ItemService.getInstance();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        System.out.println(user);
        List<Item> items = service.findAllItems(user.getId());
        PrintWriter pw = resp.getWriter();
        ObjectMapper map = new ObjectMapper();
        String json = map.writeValueAsString(items);
        pw.append(json);
        pw.flush();
    }
}
