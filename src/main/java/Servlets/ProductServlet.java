package Servlets;

import Handlers.ProductHandler;
import Handlers.UserHandler;
import Objects.Product;
import Objects.User;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/Product")
public class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productName = req.getParameter("name");
        ProductHandler productHandler = ProductHandler.getInstance();
        Product product = productHandler.getProductByName(productName);


        String productJson = new Gson().toJson(product);


        resp.setContentType("application/json");


        resp.getWriter().write(productJson);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        User user = (User) httpSession.getAttribute("loggedUser");
        if (user != null) {
            String productName = req.getParameter("name");
            ProductHandler productHandler = ProductHandler.getInstance();
            Product product = productHandler.getProductByName(productName);
            if (product != null) {
                UserHandler.addProduct(user.getName(), product);
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}