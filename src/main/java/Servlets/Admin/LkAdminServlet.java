package Servlets.Admin;

import Objects.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/LkAdmin")
public class LkAdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        if(httpSession.getAttribute("loggedUser") != null) {
            User loggedUser = (User) httpSession.getAttribute("loggedUser");
            if(loggedUser.isAdmin()) {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/statics/LkAdmin-page.html");
                requestDispatcher.forward(req, resp);
            }
            else{
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/statics/LkUser-page.html");
                requestDispatcher.forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
