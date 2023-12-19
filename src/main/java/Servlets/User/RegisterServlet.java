package Servlets.User;

import Handlers.UserHandler;
import Objects.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        if(httpSession.getAttribute("loggedUser") == null){
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/statics/register-page.html");
            requestDispatcher.forward(req, resp);
        }else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/statics/main-store.html");
            requestDispatcher.forward(req, resp);
        }

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        HttpSession httpSession = req.getSession();
        if(httpSession.getAttribute("loggedUser") == null) {
            if(!UserHandler.checkUser(username)){
                UserHandler.addUser(new User(username, password));
                httpSession.setAttribute("loggedUser", UserHandler.getUser(username));
                httpSession.setMaxInactiveInterval(60*60*24);
                resp.sendRedirect("/statics/main-store.html");
            }
            else {
                PrintWriter out = resp.getWriter();
                out.write("Username is already taken");
                resp.sendRedirect("/Register");                 // mistake mb
            }
            resp.sendRedirect("/statics/main-store.html");
        }
    }
}
