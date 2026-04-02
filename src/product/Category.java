package product;

import java.util.ArrayList;
import java.util.List;

public class Category {
    // product.Product 클래스를 관리하는 클래스입니다.

    // 속성(전체 상품 리스트, 카테고리별 상품 리스트, 카테고리 이름)
    static List<Product> allProducts = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private final String categoryName;

    // 생성자
    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    // 카테고리 이름 반환 메서드
    public String getCategoryName() {
        return categoryName;
    }

    // 상품 리스트 게터
    public List<Product> getProducts() {
        List<Product> readProducts = new ArrayList<>();
        for (Product product : products) {
            Product readProduct = new Product(
                    product.getProductName(), product.getPrice(), product.getDescription(), product.getCount());
            readProducts.add(readProduct);
        }

        return readProducts;
    }

    // 모든 상품 리스트 allProducts 게터
    public static List<Product> getAllProducts() {
        List<Product> readAll = new ArrayList<>();
        for (Product product : allProducts) {
            Product readProduct = new Product(
                    product.getProductName(), product.getPrice(), product.getDescription(), product.getCount());
            readAll.add(readProduct);
        }

        return readAll;
    }

    // 재고 관리 위한 전체 상품 반환 메서드
    static List<Product> setAllProducts() {
        return allProducts;
    }

    // 상품 리스트의 상품 접근
    public Product getProduct(int index) {
        return products.get(index);
    }

    // 전체 상품리스트의 상품 접근
    public static Product getAllCatOfProduct(int index) {
        return allProducts.get(index);
    }

    // 상품 추가 메서드
    public void addProducts(Product product) {
        if (product == null) {
            return;
        }
        products.add(product);
        allProducts.add(product);
    }

    // 상품 삭제 메서드
    public  void delProduct(Product product) {
        if (products.isEmpty() || allProducts.isEmpty()) {
            System.out.println("비어있음");
            return;
        }
        products.remove(product);
        allProducts.remove(product);
    }
}
