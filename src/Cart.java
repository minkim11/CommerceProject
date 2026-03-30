public class Cart {
    // 사용자가 상품을 담을 장바구니 클래스

    // 속성 (상품이름, 수량, 가격)
    private final String productName;
    private int quantity = 1;
    private final int price;

    // 생성자
    public Cart(Product product) {
        this.productName = product.getProductName();
        this.price = product.getPrice();
    }

    // 게터
    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    // 상품 수량 변경 세터
    public void setQuantity(int num) {
        if (quantity + num <= 0) {
            System.out.println("0개 이하로 담을 수 없습니다.");
            return;
        }
        quantity += num;
    }

}