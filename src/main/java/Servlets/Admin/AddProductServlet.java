package Servlets.Admin;

import Handlers.ProductHandler;
import Objects.Product;
import Objects.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.*;

@WebServlet("/AddProduct")
@MultipartConfig
public class AddProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        if(httpSession.getAttribute("loggedUser") != null) {
            User loggedUser = (User) httpSession.getAttribute("loggedUser");
            if(loggedUser.isAdmin()) {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/statics/admin/add-product-page.html");
                requestDispatcher.forward(req, resp);
            }
            else{
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/statics/main-store.html");
                requestDispatcher.forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product = new Product();
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String price = req.getParameter("price");
        String tag = req.getParameter("tag");
        String photoUrl = "";

        Part filePart = req.getPart("fileInput");
        if (filePart != null) {
            String fileName = getSubmittedFileName(filePart);
            if (fileName != null && !fileName.isEmpty()) {

                String uploadPath = getServletContext().getRealPath("/photo/") + File.separator + fileName;
                saveFile(filePart, uploadPath);


                photoUrl = "/photo/" + fileName;
                product.setPhotoProduct(photoUrl);
            }
        }else {photoUrl="";}

        product.setNameProduct(name);
        product.setDescription(description);
        product.setPrice(price);
        product.getTagProduct().add(tag);


        ProductHandler productHandler = ProductHandler.getInstance();
        productHandler.addProduct(product);
        productHandler.saveProductsData();


        resp.sendRedirect("/statics/main-store.html");
    }


    private void saveFile(Part part, String path) throws IOException {
        try (InputStream input = part.getInputStream(); OutputStream output = new FileOutputStream(path)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        }
    }


    private String getSubmittedFileName(Part part) {
        for (String contentDisposition : part.getHeader("content-disposition").split(";")) {
            if (contentDisposition.trim().startsWith("filename")) {
                return contentDisposition.substring(contentDisposition.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}