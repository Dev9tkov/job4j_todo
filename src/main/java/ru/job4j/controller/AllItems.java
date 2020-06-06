package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.job4j.model.Item;
import ru.job4j.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

public class AllItems extends HttpServlet {
    private final ItemService service = ItemService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/json");

        List<Item> items = service.findAllItems();
        PrintWriter pw = resp.getWriter();
        ObjectMapper map = new ObjectMapper();
        ArrayNode arrayNode = map.createArrayNode();

        for (Item val : items) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = format.format(val.getCreated());
            ObjectNode objectNode = map.createObjectNode();

            objectNode.put("id", val.getId());
            objectNode.put("desc", val.getDesc());
            objectNode.put("created", date);
            objectNode.put("done", val.isDone());
            arrayNode.add(objectNode);
        }
        String json = map.writeValueAsString(arrayNode);
        pw.append(json);
        pw.flush();
    }
}
