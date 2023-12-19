package Objects;

import java.util.HashSet;
import java.util.Set;

public class Product {
    private String name_product;
    private String description;
    private String photo_product;

    private String price;
    private Set<String> tagProduct = new HashSet<>();

    // Constructors
    public Product() {
    }

    // Getters and Setters
    public String getNameProduct() {
        return name_product;
    }

    public void setNameProduct(String nameProduct) {
        this.name_product = nameProduct;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoProduct() {
        return photo_product;
    }

    public void setPhotoProduct(String photoProduct) {
        this.photo_product = photoProduct;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Set<String> getTagProduct() {
        return tagProduct;
    }

    public void setTagProduct(Set<String> tagProduct) {
        this.tagProduct = tagProduct;
    }


    public boolean containsTag(String tag) {
        return tagProduct.contains(tag);
    }


    public boolean matchesName(String name) {
        return name_product.equalsIgnoreCase(name);
    }
}
