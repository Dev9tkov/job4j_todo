package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.job4j.model.Item;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Item> items = service.findAllItems();
        PrintWriter pw = resp.getWriter();
        ObjectMapper map = new ObjectMapper();
        String json = map.writeValueAsString(items);
        pw.append(json);
        pw.flush();
    }
}
