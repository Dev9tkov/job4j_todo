package ru.job4j.controller;

import ru.job4j.model.Item;
import ru.job4j.model.User;
import ru.job4j.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class AddItem extends HttpServlet {
    private final ItemService service = ItemService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String desc = req.getParameter("item");
        User user = new User();
        user.setId(Integer.valueOf(id));
        service.addItem(new Item(desc, true, user));
    }
}
