public class Product {
    // 개별 상품을 관리하는 클래스. 현재는 전자제품만

    //속성
    String productName;
    int price;
    String description;
    int count;

    //생성자
    Product(String productName, int price, String description, int count) {
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.count = count;
    }
}
