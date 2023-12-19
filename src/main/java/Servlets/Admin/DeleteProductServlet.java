package Servlets.Admin;

import Handlers.ProductHandler;
import Objects.Product;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/DeleteProduct")
public class DeleteProductServlet extends HttpServlet {
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
        String productName = req.getParameter("productName");
        ProductHandler productHandler = ProductHandler.getInstance();
        productHandler.deleteProductByName(productName);


        ArrayList<Product> products = productHandler.getProducts();
        Gson gson = new Gson();
        String productsJson = gson.toJson(products);

        resp.setContentType("application/json");
        resp.getWriter().write(productsJson);
    }
}