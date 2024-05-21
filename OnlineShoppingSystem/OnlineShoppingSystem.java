import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Class representing a product
class Product {
    private String productId;
    private String name;
    private double price;

    public Product(String productId, String name, double price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product ID: " + productId + ", Name: " + name + ", Price: Rs" + price;
    }
}

// Class representing a customer
class Customer {
    private String customerId;
    private String name;
    private ShoppingCart cart;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
        this.cart = new ShoppingCart();
    }

    // Getters and Setters
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    @Override
    public String toString() {
        return "Customer ID: " + customerId + ", Name: " + name;
    }
}

// Class representing a shopping cart
class ShoppingCart {
    private List<Product> products;

    public ShoppingCart() {
        this.products = new ArrayList<>();
    }

    // Methods to add and remove products
    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(String productId) {
        products.removeIf(product -> product.getProductId().equals(productId));
    }

    public double calculateTotal() {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    public void displayCart() {
        if (products.isEmpty()) {
            System.out.println("Shopping cart is empty.");
        } else {
            products.forEach(System.out::println);
        }
    }

    public void checkout() {
        double total = calculateTotal();
        if (total > 0) {
            System.out.println("Proceeding to checkout. Total amount: Rs" + total);
            products.clear();
        } else {
            System.out.println("Shopping cart is empty. Add products to proceed to checkout.");
        }
    }
}

// Main class to simulate the online shopping system
public class OnlineShoppingSystem {
    private List<Product> products;
    private List<Customer> customers;

    public OnlineShoppingSystem() {
        this.products = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    // Methods to manage products
    public void addProduct(Product product) {
        products.add(product);
    }

    public void deleteProduct(String productId) {
        products.removeIf(product -> product.getProductId().equals(productId));
    }

    public Product findProductById(String productId) {
        return products.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    // Methods to manage customers
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public Customer findCustomerById(String customerId) {
        return customers.stream()
                .filter(customer -> customer.getCustomerId().equals(customerId))
                .findFirst()
                .orElse(null);
    }

    public static void main(String[] args) {
        OnlineShoppingSystem system = new OnlineShoppingSystem();
        Scanner scanner = new Scanner(System.in);

        // Sample data
        Product product1 = new Product("P001", "Laptop", 999.99);
        Product product2 = new Product("P002", "Smartphone", 699.99);
        Product product3 = new Product("P003", "Headphones", 199.99);
        system.addProduct(product1);
        system.addProduct(product2);
        system.addProduct(product3);

        Customer customer1 = new Customer("C001", "Harsh");
        Customer customer2 = new Customer("C002", "Yash");
        system.addCustomer(customer1);
        system.addCustomer(customer2);

        // Example operations
        System.out.println("Customer Browsing Products:");
        system.products.forEach(System.out::println);

        System.out.println("\nCustomer Adding Products to Cart:");
        customer1.getCart().addProduct(product1);
        customer1.getCart().addProduct(product3);
        customer1.getCart().displayCart();

        System.out.println("\nCustomer Removing Product from Cart:");
        customer1.getCart().removeProduct("P003");
        customer1.getCart().displayCart();

        System.out.println("\nCustomer Checkout:");
        customer1.getCart().checkout();
        customer1.getCart().displayCart();

        scanner.close();
    }
}
