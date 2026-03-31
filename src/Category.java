import java.util.ArrayList;
import java.util.List;

public class Category {
    // Product 클래스를 관리하는 클래스입니다.

    // 속성
    private static List<Product> allProducts = new ArrayList<>();
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

    // 상품 리스트 반환 멤서드
    public List<Product> getProducts() {
        return products;
    }

    // 모든 상품 리스트 allProducts 반환 메서드
    public static List<Product> getAllProducts() {
        return allProducts;
    }

    // 상품 추가 메서드
    public void addProducts(Product product) {
        products.add(product);
        allProducts.add(product);
    }

    // 상품 삭제 메서드
    public  void delProduct(Product product) {
        products.remove(product);
    }
}
