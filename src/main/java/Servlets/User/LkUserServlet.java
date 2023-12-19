package Servlets.User;

import Handlers.UserHandler;
import Objects.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/LkUser")
public class LkUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession httpSession = req.getSession();
        User loggedUser = (User) httpSession.getAttribute("loggedUser");
        if (loggedUser != null) {
        try (BufferedReader reader = req.getReader()) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String json = sb.toString();


            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

            String email = jsonObject.get("email").getAsString();
            String address = jsonObject.get("address").getAsString();


            UserHandler.setAddress(loggedUser.getName(), address);
            UserHandler.setPostAddress(loggedUser.getName(), email);

            System.out.println("Email: " + email);
            System.out.println("Address: " + address);

            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }else {
            resp.sendRedirect("/statics/auth-page.html");
        }
    }
}