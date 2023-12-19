package Servlets;

import Handlers.ProductHandler;
import Handlers.UserHandler;
import Objects.Product;
import Objects.User;
import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/Basket")
public class BasketPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        User user = (User) httpSession.getAttribute("loggedUser");

        if (user != null){
            ArrayList<Product> basketUserProduct = new ArrayList<>(user.getBasket());
            Gson gson = new Gson();
            String userProductJson = gson.toJson(basketUserProduct);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(userProductJson);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productName = req.getParameter("product");

        HttpSession httpSession = req.getSession();
        User user = (User) httpSession.getAttribute("loggedUser");

        if (user != null && productName != null) {
            if (UserHandler.removeProduct(user.getName(), productName)) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {

            }
        } else {

        }
    }
}
