import java.util.List;

public class InventoryManagement {
    // 재고 관리 클래스입니다.

    // 속성
    private List<Cart> carts;

    // 생성자
    public InventoryManagement(List<Cart> carts) {
        this.carts = carts;
    }

    // 재고 부족 확인 (재고 부족 시 예외 발생)
    public void checkCount(Product product) throws Exception {
        if (product.getCount() == 0) {
            throw new Exception("재고가 부족합니다! 재고: " + product.getCount());
        }
        for (Cart cart : carts) {
            if (cart.getProductName().equals(product.getProductName())
                    && product.getCount() - cart.getQuantity() - 1 < 0) {
                throw new Exception("재고가 부족합니다! 담은 수량: " + cart.getQuantity() + " 재고: " + product.getCount());
            }
        }

    }

    // 구매 시 재고 차감 (장바구니 상품과 동일한 상품 찾아서 수량만큼 재고 감소)
    public void reduceCount() {
        for (Cart cart : carts) {
            for (Product product : Category.getAllProducts()) {
                if (product.getProductName().equals(cart.getProductName())){
                    System.out.printf("%s 재고가 %d개",product.getProductName(), product.getCount());
                    product.setCount(-cart.getQuantity());
                    System.out.printf(" → %d개로 업데이트되었습니다.\n",product.getCount());
                }
            }
        }
    }

}
