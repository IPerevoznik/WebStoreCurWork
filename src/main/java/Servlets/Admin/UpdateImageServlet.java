package Servlets.Admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/UpdateImageServlet")
@MultipartConfig
public class UpdateImageServlet extends HttpServlet {
    @Override

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        for (Part filePart : req.getParts()) {
            if (filePart.getName().equals("image")) {
                String originalFileName = getSubmittedFileName(filePart);
                String fileExtension = getFileExtension(originalFileName);
                String fileName = "main-welcome-page" + fileExtension;

                String appPath = req.getServletContext().getRealPath("");
                String saveDirectory = appPath + "/photo/";
                String savePath = saveDirectory + fileName;

                // Удаляем старый файл, если он существует
                File oldFile = new File(savePath);
                if (oldFile.exists()) {
                    oldFile.delete();
                }

                try (InputStream input = filePart.getInputStream(); OutputStream output = new FileOutputStream(savePath)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = input.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }
                }
            }
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private String getSubmittedFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
    }
}