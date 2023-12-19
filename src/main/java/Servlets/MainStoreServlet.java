package Servlets;

import Objects.Product;
import Handlers.ProductHandler;
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

@WebServlet("/MainStore")
public class MainStoreServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        User user = (User) httpSession.getAttribute("loggedUser");

        if (user != null) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/statics/main-store.html");
        requestDispatcher.forward(req,resp);
}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        User loggedUser = (User) httpSession.getAttribute("loggedUser");
        if (loggedUser != null) {
            if (loggedUser.isAdmin()) {
                resp.sendRedirect("/statics/LkAdmin-page.html");
            } else {
                resp.sendRedirect("/statics/LkUser-page.html");
            }
        } else {
            resp.sendRedirect("/statics/auth-page.html");
        }
    }
}