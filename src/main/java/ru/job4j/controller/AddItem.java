package ru.job4j.controller;

import ru.job4j.model.Item;
import ru.job4j.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddItem extends HttpServlet {
    private final ItemService service = ItemService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String desc = req.getParameter("item");
        service.addItem(new Item(desc, true));
    }
}
