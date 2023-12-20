package Objects;

import java.util.HashSet;
import java.util.Set;

public class User {
    private String name;
    private String password;
    private boolean admin;
    private String emailPost;
    private String deliveryAddress;
    private Set<Product> basket = new HashSet<>();


    public User(String name, String password) {
        this.name = name;
        this.password = password;
        admin = false;
        this.emailPost = " ";
        this.deliveryAddress = " ";
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Set<Product> getBasket() {
        return basket;
    }

    public void setBasket(Set<Product> basket) {
        this.basket = basket;
    }

    public void addToBasket(Product product) {
        basket.add(product);
    }

    public void removeFromBasket(Product product) {
        basket.remove(product);
    }

    public String getEmailPost() {
        return emailPost;
    }

    public void setEmailPost(String emailPost) {
        this.emailPost = emailPost;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
