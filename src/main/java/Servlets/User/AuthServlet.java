package Servlets.User;

import Handlers.UserHandler;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/Auth")
public class AuthServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        if(httpSession.getAttribute("loggedUser") == null){
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/statics/auth-page.html");
            requestDispatcher.forward(req, resp);
        }else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/statics/main-store.html");
            requestDispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if(!username.isEmpty() & !password.isEmpty()) {
            if(UserHandler.checkUser(username, password)) {
                httpSession.setAttribute("loggedUser", UserHandler.getUser(username));
                httpSession.setMaxInactiveInterval(60*60*24);
                resp.sendRedirect("/statics/main-store.html");
            }
            else {
                resp.sendRedirect("/Register");
            }
        }
        else {
            resp.sendRedirect("/MainStore");
        }
    }
}
