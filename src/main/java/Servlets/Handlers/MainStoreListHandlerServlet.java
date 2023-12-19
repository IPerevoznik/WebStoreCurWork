package Servlets.Handlers;

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
import java.util.ArrayList;

@WebServlet("/MainStoreListHandler")
public class MainStoreListHandlerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductHandler productHandler = ProductHandler.getInstance();
        ArrayList<Product> products = productHandler.getProducts();

        Gson gson = new Gson();
        String productsJson = gson.toJson(products);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(productsJson);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        User user = (User) httpSession.getAttribute("loggedUser");
        if (user != null) {
            String productName = req.getParameter("productName");
            ProductHandler productHandler = ProductHandler.getInstance();
            Product product = productHandler.getProductByName(productName);
            if (product != null) {
                UserHandler.addProduct(user.getName(),product);
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {

            }
        } else {

        }
    }
}
