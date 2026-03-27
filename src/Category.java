import java.util.List;

public class Category {
    // Product 클래스를 관리하는 클래스입니다.

    // 속성
    List<Product> products;
    String categoryName;

    // 생성자
    Category(List<Product> products, String categoryName) {
        this.products = products;
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
}
