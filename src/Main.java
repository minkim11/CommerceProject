import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Product> products1 = new ArrayList<>();
        List<Product> products2 = new ArrayList<>();
        List<Product> products3 = new ArrayList<>();


        // 상품 목록 추가 ======================================================
        Product product1 = new Product("Galaxy S25", 1200000
                , "최신 안드로이드 스마트폰", 1);
        Product product2 = new Product("iPhone 16", 1350000
                , "Apple의 최신 스마트폰", 1);
        Product product3 = new Product("MacBook Pro", 2400000
                , "M3 칩셋이 탑재된 노트북", 1);
        Product product4 = new Product("AirPods Pro", 350000
                , "노이즈 캔슬링 무선 이어폰", 1);

        products1.add(product1);
        products1.add(product2);
        products1.add(product3);
        products1.add(product4);

        Product product5 = new Product("onepiece", 100000, "해적이 입고 싶은 원피스", 1);
        products2.add(product5);

        Product product6 = new Product("bibigo mandoo", 10000, "비비고 김치 교자", 5);
        products3.add(product6);

        // 카테고리 생성=========================================================
        Category electronics = new Category(products1, "전자제품");
        Category clothes = new Category(products2, "의류");
        Category food = new Category(products3, "식품");

        // CommerceSystem 객체 생성 및 함수 호출
        CommerceSystem commerceSystem = new CommerceSystem();
        commerceSystem.setCategories(electronics);
        commerceSystem.setCategories(clothes);
        commerceSystem.setCategories(food);
        commerceSystem.start();

    }
}