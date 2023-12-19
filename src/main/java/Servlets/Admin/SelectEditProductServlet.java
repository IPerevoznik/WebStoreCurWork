package Servlets.Admin;

import Handlers.ProductHandler;
import Objects.Product;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/SelectEditProduct")
public class SelectEditProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductHandler productHandler = ProductHandler.getInstance();
        ArrayList<Product> products = productHandler.getProducts();

        Gson gson = new Gson();
        String productsJson = gson.toJson(products);

        resp.setContentType("application/json");
        resp.getWriter().write(productsJson);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String requestData = req.getReader().lines().collect(Collectors.joining());
        Map<String, String> data = gson.fromJson(requestData, Map.class);
        String productName = data.get("productName");

        req.getSession().setAttribute("selectedProductName", productName); // Сохраняем имя выбранного продукта в сессии

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}