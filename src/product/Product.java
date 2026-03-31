package product;

public class Product {
    // 개별 상품을 관리하는 클래스입니다.

    //속성
    private final String productName;
    private int price;
    private String description;
    private int count;

    //생성자
    public Product(String productName, int price, String description, int count) {
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.count = count;
    }

    // 게터
    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getCount() {
        return count;
    }

    // 재고 추가 메서드
    public void setCount(int num) {
        if (count + num < 0) {
            System.out.println("재고가 0미만이 됩니다.");
            return;
        }
        count = count + num;
    }

    // 가격 세터
    public void setPrice(int num) {
        if (num <= 0) {
            System.out.println("가격은 0원 이상이어야 합니다.");
            return;
        }
        price = num;
    }

    // 설명 세터
    public void setDescription(String description) {
        if (description.isEmpty()) {
            System.out.println("설명을 적어주세요.");
            return;
        }
        this.description = description;
    }
}
