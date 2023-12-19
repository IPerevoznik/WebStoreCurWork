package Servlets;

import Handlers.UserHandler;
import Objects.User;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/MakingPurchase")
public class MakingPurchaseServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");

        User user = UserHandler.getUser(username);

        JsonObject userData = new JsonObject();
        userData.addProperty("email", user.getEmailPost());
        userData.addProperty("address", user.getDeliveryAddress());

        resp.setContentType("application/json");
        resp.getWriter().write(userData.toString());
    }
}