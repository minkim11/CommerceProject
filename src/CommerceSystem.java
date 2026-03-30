import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CommerceSystem {
    // 커머스 플랫폼의 상품을 관리하고 사용자 입력을 처리하는 클래스
    Scanner sc = new Scanner(System.in);

    // 카테고리 리스트 관리
    private List<Category> categories = new ArrayList<>();
    // 장바구니 리스트 관리
    private List<Cart> carts = new ArrayList<>();
    // 재고 관리 클래스 호출
    private InventoryManagement im = new InventoryManagement(carts);

    // 메인 시스템 시작 메서드
    public void start() {
        int index = 1;
        System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
        while (true) {
            System.out.println(index + ". " + categories.get(index).getCategoryName());
            index++;
            // 리스트 인덱스 범위 넘어가면 사용자 입력
            if (index == categories.size()) {
                System.out.printf("%-10s| 프로그램 종료\n","0. 종료");
                if (!carts.isEmpty()) { // 장바구니 상품 있을 경우 추가 메뉴 출력
                    System.out.println("[ 주문 관리 ]");
                    System.out.println(categories.size() + ". 장바구니 확인");
                    System.out.println(categories.size() + 1 + ". 주문 취소");
                }
                // 인덱스 범위 오류 방지 초기화(메인 시스템 메뉴 재반복을 위한 초기화)
                index = 1;
                int check1 = sc.nextInt();
                if (check1 == 0) { // 0 입력시 종료
                    System.out.println("커머스 플랫폼을 종료합니다.");
                    break;
                } else if (check1 == categories.size() && !carts.isEmpty()) { // 장바구니 확인 메뉴
                    System.out.println("아래와 같이 주문 하시겠습니까?");
                    int totalPrice = checkCart(); // 장바구니 출력 및 계산
                    System.out.println("1. 주문 확정      2. 메인으로 돌아가기");
                    int check4 = sc.nextInt();
                    if (check4 == 1) {
                        order(totalPrice); // 주문 완료 후 메인으로 돌아감
                        System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
                    } else {
                        System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
                    }
                } else if (check1 > categories.size() - 1 || check1 < 0) { // 메뉴 이외의 번호 누를 경우 예외 처리
                    System.out.println("올바르지 않은 번호 입력!");
                    System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
                }else { // 카테고리 조회, 장바구니 담기 메서드 호출 후 메인으로 돌아감
                    Product selectProduct = checkCategory(categories.get(check1).getProducts(), check1);
                    addCarts(selectProduct);
                    System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
                }
            }
        }
    }



    // 카테고리 상품 리스트 조회 메서드
    public Product checkCategory(List<Product> products, int selectNum) {
        // 상품 인덱스
        int index = 0;
        System.out.println("[ " + categories.get(selectNum).getCategoryName() + " 카테고리 ]");
        // 카테고리의 상품 리스트에서 상품 하나씩 꺼내와서 출력
        for (Product product : products) {
            // string format 왼쪽 정렬, 오른쪽 정렬, 숫자에 "," 넣기, 여백 맞추기
            System.out.printf("%d. %-18s|%,10d원 | %s\n"
                    , ++index
                    , product.getProductName()
                    , product.getPrice()
                    , product.getDescription());
        }
        System.out.println("0. 뒤로가기");
        int check2 = sc.nextInt();
        if (check2 == 0) { // 뒤로가기 - 메인으로 돌아감
            return null;
        } else if (0 < check2 && check2 <= products.size()) { // 입력한 번호의 상품 출력, 인덱스 범위 에러 예외 처리
            System.out.printf("선택한 상품: %s | %,d원 | %s | 재고: %s개\n\n"
                    , products.get(check2 - 1).getProductName()
                    , products.get(check2 - 1).getPrice()
                    , products.get(check2 - 1).getDescription()
                    , products.get(check2 - 1).getCount());
            return products.get(check2 - 1);
        }
        System.out.println("유효하지 않은 상품번호 입력!");
        return null;
    }

    // 카테고리 추가 세터
    public void setCategories(Category category) {
        categories.add(category);
    }

    // 장바구니 생성 및 담기 기능 (입력한 상품을 장바구니 생성 후 담기, 동일 상품 장바구니에 있을 경우 수량 1 추가)
    public void addCarts(Product product) {
        if (product == null) {
            return;
        }
        System.out.printf("\"%s | %,d원 | %s\"\n"
                , product.getProductName()
                , product.getPrice()
                , product.getDescription());
        System.out.println("위 상품을 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인        2.취소");
        try {
            int check3 = sc.nextInt();
            if (check3 != 1) {
                System.out.println("취소되었습니다.");
                return;
            }
            im.checkCount(product); // 장바구니 담기 전 재고 확인 메서드 호출
        } catch (InputMismatchException ie) {
            System.out.println("숫자를 입력해주세요.");
            return;
        } catch (Exception e) {
            System.out.println(e.getMessage()); // 재고 확인 메서드, 재고 부족 예외 처리
            return;
        }
        // 장바구니에 동일한 상품 있을 경우 수량 1개 추가 후 메서드 종료
        for (Cart cart : carts) {
            if (cart.getProductName().equals(product.getProductName())) {
                cart.setQuantity(1);
                System.out.println(cart.getProductName() + "가 장바구니에 추가되었습니다!!\n");
                return;
            }
        }
        // 장바구니 생성 후 담기
        Cart cart = new Cart(product);
        carts.add(cart);
        System.out.println(cart.getProductName() + "가 장바구니에 추가되었습니다!\n");
    }

    // 장바구니 출력 및 금액 계산
    public int checkCart() {
        int totalPrice = 0;
        System.out.println("[ 장바구니 내역 ]");
        for (Cart cart : carts) {
            System.out.printf("%-18s | %,10d원 | 수량: %d개\n"
                    ,cart.getProductName()
                    ,cart.getPrice()
                    ,cart.getQuantity());
            totalPrice += cart.getPrice() * cart.getQuantity();
        }
        System.out.println("[ 총 주문 금액 ]");
        System.out.printf("%,d원\n", totalPrice);
        return totalPrice;
    }

    // 주문 기능
    public void order(int totalPrice) {
        System.out.printf("주문이 완료되었습니다! 총 금액: %,d원\n", totalPrice);
        im.reduceCount(categories.get(0)); // 재고 차감 메서드 호출
        carts.clear(); // 장바구니 비우기
    }
}
