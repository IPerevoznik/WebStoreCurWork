package Handlers;

import Objects.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ProductHandler {
    private static final String JSON_FILE_PATH = "DataProduct.json";
    private static ArrayList<Product> products;
    private static final ProductHandler instance = new ProductHandler();

    Gson gson;
    public static ProductHandler getInstance() {
        return instance;
    }

    public void addProduct(Product product) {
        loadProductsData();
        products.add(product);
        saveProductsData();
    }

    private ProductHandler() {
        products = new ArrayList<>();
        gson = new GsonBuilder().setPrettyPrinting().create();
        products = new ArrayList<>();
    }


    public void saveProductsData() {
        try (Writer writer = new FileWriter(JSON_FILE_PATH)) {
            gson.toJson(products, writer);
        } catch (IOException e) {
            System.out.println("Error saving users to JSON: " + e.getMessage());
        }
    }
    public ArrayList<Product> getProducts() {
        loadProductsData();
        return products;
    }



    public Product getProductByName(String productName) {
        for (Product product : products) {
            if (product.getNameProduct().equals(productName)) {
                return product;
            }
        }
        return null;
    }
    public void loadProductsData() {
        try (Reader reader = new FileReader(JSON_FILE_PATH)) {
            products.clear(); // Очистка списка перед загрузкой новых данных
            Product[] productsArray = gson.fromJson(reader, Product[].class);
            if (productsArray != null) {
                products.addAll(Arrays.asList(productsArray));
            }
        } catch (IOException e) {
            System.out.println("Error loading users from JSON: " + e.getMessage());
        }
    }

    public void deleteProductByName(String productName) {
        for (Product product : products) {
            if (product.getNameProduct().equals(productName)) {
                products.remove(product);
                saveProductsData();
                return;
            }
        }
    }
    public void updateProductByName(String productName, String newDescription, String newPrice) {
        for (Product product : products) {
            if (product.getNameProduct().equals(productName)) {
                product.setDescription(newDescription);
                product.setPrice(newPrice);
                saveProductsData();
                return;
            }
        }
    }
    public boolean updateProduct(String productName, Product newProduct) {
        for (Product product : products) {
            if (product.getNameProduct().equals(productName)) {
                product.setNameProduct(newProduct.getNameProduct());
                product.setDescription(newProduct.getDescription());
                product.setPrice(newProduct.getPrice());
                product.setPhotoProduct(newProduct.getPhotoProduct());
                product.getTagProduct().clear();
                product.getTagProduct().addAll(newProduct.getTagProduct());



                saveProductsData();
                return false;
            }
        }
        return false;
    }


}