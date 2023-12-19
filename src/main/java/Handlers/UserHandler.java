package Handlers;

import Objects.Product;
import Objects.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class UserHandler {
    private static final String JSON_FILE_PATH = "DataUser.json";
    private static UserHandler instance = new UserHandler();
    private static ArrayList<User> usersList;
    private static Gson gson;

    public static UserHandler getInstance() {
        return instance;
    }

    private UserHandler() {
        usersList = new ArrayList<>();
        gson = new GsonBuilder().setPrettyPrinting().create();
        loadUserData();
    }

    public static void addUser(User user) {
        if (!checkUser(user.getName())) {
            usersList.add(user);
            saveUserData();
        } else {
            System.out.println("User with this name already exists");
        }
    }

    public static void saveUserData() {
        // clearFileContent();
        try (FileWriter writer = new FileWriter(JSON_FILE_PATH)) {
            gson.toJson(usersList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadUserData() {
        try (Reader reader = new FileReader(JSON_FILE_PATH)) {
            User[] usersArray = gson.fromJson(reader, User[].class);
            if (usersArray != null) {
                usersList.addAll(Arrays.asList(usersArray));
            }
        } catch (IOException e) {
            System.out.println("Error loading users from JSON: " + e.getMessage());
        }
    }

    public static User getUser(String username) {
        if (username != null) {
            for (User user : usersList) {
                if (username.equals(user.getName())) {
                    return user;
                }
            }
        }
        return null;
    }

    public static boolean checkUser(String username) {

        for (User user : usersList) {
            if (user.getName() != null && user.getName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkUser(String username, String password) {

        for (User user : usersList) {
            if (user.getName().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
    public static boolean addProduct(String username, Product product) {
        User user = getUser(username);
        if (user != null) {
            user.addToBasket(product);
            saveUserData();
            return true;
        }
        return false;
    }

    public static boolean removeProduct(String username, String productName) {
        User user = getUser(username);
        if (user != null) {
            Set<Product> basket = user.getBasket();
            for (Product product : basket) {
                if (product.getNameProduct().equals(productName)) {
                    user.removeFromBasket(product);
                    saveUserData();
                    return true;
                }
            }
        }
        return false;
    }


    private static void clearFileContent() {
        try (FileWriter writer = new FileWriter(JSON_FILE_PATH, false)) {
            writer.write("");  // Перезаписываем файл, очищая его содержимое
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void setAddress(String username, String address) {

        User user = getUser(username);
        if (user != null) {
            user.setDeliveryAddress(address);
            saveUserData();
        }
    }

    public static void setPostAddress(String username, String postAddress) {
        User user = getUser(username);
        if (user != null) {
            user.setEmailPost(postAddress);
            saveUserData();
        }
    }
    public static void setUserData(String username, String email, String address) {
        User user = getUser(username);
        if (user != null) {
            user.setEmailPost(email);
            user.setDeliveryAddress(address);
            saveUserData();
        }
    }
}