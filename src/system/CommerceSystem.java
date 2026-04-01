package system;

import customer.Grade;
import product.Cart;
import product.Category;
import product.InventoryManagement;
import product.Product;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
        int index = 0;
        System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
        while (true) {
            System.out.println(index + 1 + ". " + categories.get(index).getCategoryName());
            index++;
            // 카테고리 리스트 인덱스 범위 넘어가면 사용자 입력 1. 전자제품 2. 의류 3. 식품 0. 종료 ...
            if (index == categories.size()) {
                System.out.printf("%-10s| 프로그램 종료\n","0. 종료");
                System.out.println("6. 관리자 모드");
                if (!carts.isEmpty()) { // 장바구니 상품 있을 경우 추가 메뉴 출력
                    System.out.println("[ 주문 관리 ]");
                    System.out.println(categories.size() + 1 + ". 장바구니 확인");
                    System.out.println(categories.size() + 2 + ". 주문 취소");
                }
                // 인덱스 범위 오류 방지 초기화(메인 시스템 메뉴 재반복을 위한 초기화)
                index = 0;
                int check1 = sc.nextInt();
                if (check1 == 0) {                                                // 0. 종료
                    System.out.println("커머스 플랫폼을 종료합니다.");
                    break;
                } else if (check1 == 6) {                                         // 6. 관리자 모드 진입
                    adminMode();
                } else if (check1 == categories.size() + 1 && !carts.isEmpty()) { // 4.장바구니 있을 경우 장바구니 확인 메뉴
                    System.out.println("아래와 같이 주문 하시겠습니까?");
                    int totalPrice = checkCart(); // 장바구니 출력 및 계산
                    System.out.println("1. 주문 확정      2. 메인으로 돌아가기");
                    int check2 = sc.nextInt();
                    if (check2 == 1) {
                        // 고객 등급 반환해서 order로 넘겨주기
                        Grade grade = checkGrade();
                        if (grade != null) {
                            order(totalPrice, grade); // 주문 완료 후 메인으로 돌아감
                        } else {
                            System.out.println("잘못된 등급 입력!");
                        }
                        System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
                    } else {
                        System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
                    }
                } else if (check1 == categories.size() + 2 && !carts.isEmpty()) { // 5. 장바구니 삭제
                    removeCart();
                    System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
                } else if (check1 > categories.size() || check1 < 0) { // 메뉴 이외의 번호 누를 경우 예외 처리
                    System.out.println("올바르지 않은 번호 입력!");
                    System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
                }else { // 카테고리 조회, 장바구니 담기 메서드 호출 후 메인으로 돌아감    // 1.전자제품 2.의류 3.식품
                    List<Product> choiceProducts =
                            checkCategory(categories.get(check1 - 1).getProducts(), check1 - 1);
                    Product choiceProduct = selectProduct(choiceProducts);
                    addCarts(choiceProduct);
                    System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
                }
            }
        }
    }



    // 카테고리 상품 리스트 조회 메서드
    public List<Product> checkCategory(List<Product> products, int selectNum) {
        // 필터할 금액
        int filterPrice = 1000000;
        // 필터한 상품들 담을 리스트 생성
        List<Product> selectProducts = new ArrayList<>();
        // 필터 상품 인덱스
        AtomicInteger index = new AtomicInteger(1);

        // 메뉴 1. 전체상품 2. 필터 금액 이하 3. 필터 금액 초과 0. 뒤로가기
        System.out.println("[ " + categories.get(selectNum).getCategoryName() + " 카테고리 ]");
        System.out.printf("1. 전체 상품 보기\n2. 가격대별 필터링 (%d만원 이하)\n3. 가격대별 필터링 (%d만원 초과)\n"
                , filterPrice / 10000, filterPrice / 10000);
        System.out.println("0. 뒤로가기");
        int check = sc.nextInt();
        // 카테고리의 상품 출력, 가격 필터링
        // string format 왼쪽 정렬, 오른쪽 정렬, 숫자에 "," 넣기, 여백 맞추기
        switch (check) {
            case 1:
                selectProducts = products.stream()
                        .toList();
                System.out.println("[ 전체 상품 목록 ]");
                selectProducts.stream()
                        .forEachOrdered(product ->
                                System.out.printf("%d. %-18s|%,10d원 | %s\n"
                                        , products.indexOf(product) + 1
                                        , product.getProductName(), product.getPrice(), product.getDescription())
                        );
                break;
            case 2:
                selectProducts = products.stream()
                        .filter(product -> (product.getPrice() <= filterPrice))
                        .toList();
                System.out.printf("[ %d만원 이하 상품 목록 ]\n", filterPrice/10000);
                if (selectProducts.isEmpty()) {
                    System.out.println("없음");
                } else {
                    selectProducts.stream()
                            .forEachOrdered(product ->
                                    System.out.printf("%d. %-18s|%,10d원 | %s\n"
                                            , index.getAndIncrement()
                                            , product.getProductName(), product.getPrice(), product.getDescription())
                            );
                }
                break;
            case 3:
                selectProducts = products.stream()
                        .filter(product -> (product.getPrice() > filterPrice))
                        .toList();
                System.out.printf("[ %d만원 초과 상품 목록 ]\n", filterPrice/10000);
                if (selectProducts.isEmpty()) {
                    System.out.println("없음");
                } else {
                    selectProducts.stream()
                            .forEachOrdered(product ->
                                    System.out.printf("%d. %-18s|%,10d원 | %s\n"
                                            , index.getAndIncrement()
                                            , product.getProductName(), product.getPrice(), product.getDescription())
                            );
                }
                break;
            case 0:
                return null;
        }
        System.out.println("0. 뒤로가기");
        return selectProducts;
    }

    // 카테고리의 상품 선택 메서드
    public Product selectProduct(List<Product> products) {
        if (products == null) {
            return null;
        }
        int check = sc.nextInt();
        if (check == 0) { // 뒤로가기 - 메인으로 돌아감
            return null;
        } else if (0 < check && check <= products.size()) { // 입력한 번호의 상품 출력, 인덱스 범위 에러 예외 처리
            System.out.printf("선택한 상품: %s | %,d원 | %s | 재고: %s개\n\n"
                    , products.get(check - 1).getProductName()
                    , products.get(check - 1).getPrice()
                    , products.get(check - 1).getDescription()
                    , products.get(check - 1).getCount());
            return products.get(check - 1);
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
            int check = sc.nextInt();
            if (check != 1) {
                System.out.println("취소되었습니다.");
                return;
            }
            im.checkCount(product); // 장바구니 생성 및 담기 전 재고 확인 메서드 호출
        } catch (InputMismatchException ie) {
            System.out.println("숫자를 입력해주세요.");
            return;
        } catch (Exception e) {
            System.out.println(e.getMessage()); // 재고 확인 메서드, 재고 부족 예외 처리
            return;
        }
        // 장바구니에 동일한 상품 있을 경우 수량 1개 추가 후 메서드 종료
        Optional<Cart> alreadyCart = carts.stream()
                .filter(cart -> cart.getProductName().equals(product.getProductName()))
                .findFirst();
        if (alreadyCart.isPresent()) {
            alreadyCart.get().setQuantity(1);
            System.out.println(alreadyCart.get().getProductName() + "가 장바구니에 추가되었습니다!\n");
            return;
        }
        // 장바구니 생성 후 담기
        Cart cart = new Cart(product);
        carts.add(cart);
        System.out.println(cart.getProductName() + "가 장바구니에 추가되었습니다!!\n");
    }

    // 장바구니 출력 및 금액 계산
    public int checkCart() {
        System.out.println("[ 장바구니 내역 ]");
        for (Cart cart : carts) {
            System.out.printf("%-18s | %,10d원 | 수량: %d개\n"
                    ,cart.getProductName()
                    ,cart.getPrice()
                    ,cart.getQuantity());
        }
        int totalPrice = carts.stream().mapToInt(cart -> cart.getPrice() * cart.getQuantity()).sum();
        System.out.println("[ 총 주문 금액 ]");
        System.out.printf("%,d원\n", totalPrice);
        return totalPrice;
    }

    // 장바구니 삭제 메서드 (stream.filter 사용) - 상품 이름 입력받은 후 장바구니에 있는 상품일 시 삭제
    public void removeCart() {
        System.out.println("장바구니에서 제거할 상품 이름을 입력하세요: ");
        sc.nextLine();
        String productName = sc.nextLine();
        Optional<Cart> choiceCart = carts.stream()
                .filter(cart -> cart.getProductName().equals(productName))
                .findFirst();
        if (choiceCart.isPresent()) {
            carts.remove(choiceCart.get());
            System.out.println(productName + " 상품을 장바구니에서 뺍니다!");
        } else {
            System.out.println(productName + "장바구니에 없는 상품입니다!");
        }
    }

    // 고객 등급 입력 후 등급 반환 메서드
    public Grade checkGrade() {
        System.out.println("고객 등급을 입력해주세요.");
        Arrays.stream(Grade.values())
                .sorted(Comparator.comparing(Grade::getOrderNum))
                .forEach(grade -> System.out.printf("%d. %-9s:%4s%% 할인\n"
                        ,grade.getOrderNum(), grade, (int)(grade.getDiscountRate()*100)));
        int check = sc.nextInt();
        return Grade.fromOrderNum(check);
    }

    // 주문 기능
    public void order(int totalPrice, Grade grade) {
        if (grade == null) {
            return;
        }
        double discountRate = grade.getDiscountRate();
        int discountPrice = (int)(totalPrice * discountRate);
        int finalPrice = (totalPrice - discountPrice);
        System.out.printf("주문이 완료되었습니다!\n할인 전 금액: %,d원\n", totalPrice);
        System.out.printf("%s 등급 할인(%d%%): -%,d원\n", grade, (int)(discountRate*100), discountPrice);
        System.out.printf("최종 결제 금액: %,d원\n", finalPrice);
        im.reduceCount(); // 재고 차감 메서드 호출
        carts.clear(); // 장바구니 비우기
    }

    // 관리자모드 =================================================================
    public void adminMode() {
        Password adminSystem = new Password();
        int tryNum = 0;
        adminPass : while (tryNum < 3) {
            System.out.println("관리자 비밀번호를 입력해주세요: ");
            String inPassword = sc.next();
            tryNum++;
            if (inPassword.equals(adminSystem.getPassword())) {
                while (true) {
                    System.out.println("[ 관리자 모드 ]");
                    System.out.println("1. 상품 추가\n2. 상품 수정\n3. 상품 삭제\n4. 전체 상품 현황\n0. 메인으로 돌아가기");
                    int check = sc.nextInt();
                    sc.nextLine();
                    switch (check) {
                        case 0:
                            System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
                            break adminPass;
                        case 1:
                            adminAddProduct();
                            break;
                        case 2:
                            adminEditProduct();
                            break;
                        case 3:
                            adminDelProduct();
                            break;
                        case 4:
                            adminAllProducts();
                            break;
                        default:
                            System.out.println("잘못된 입력입니다!");
                    }
                }

            } else if (tryNum == 3){
                System.out.println("3회 오류! 메인 메뉴로 돌아갑니다.");
                System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
            } else {
                System.out.println("비밀번호가 틀렸습니다! 틀린 횟수: " + tryNum);
            }
        }
    }

    // 관리자 상품 추가
    public void adminAddProduct() {
        System.out.println("어느 카테고리에 상품을 추가하시겠습니까?");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println(i + 1 + ". " + categories.get(i).getCategoryName());
        }
        // 카테고리 선택
        int check1 = sc.nextInt();
        sc.nextLine();
        Category selectCat = categories.get(check1 - 1);
        // 상품 정보 입력
        System.out.printf("[ %s 카테고리에 상품 추가 ]\n", selectCat.getCategoryName());
        System.out.print("상품명을 입력해주세요: ");
        String productName = sc.nextLine();
        System.out.print("가격을 입력해주세요: ");
        int price = sc.nextInt();
        sc.nextLine();
        System.out.print("상품 설명을 입력해주세요: ");
        String description = sc.nextLine();
        System.out.print("재고수량을 입력해주세요: ");
        int count = sc.nextInt();
        // 입력 상품 정보 확인
        System.out.printf("\n%s | %,d원 | %s | 재고: %d개\n", productName, price, description, count);
        System.out.println("위 정보로 상품을 추가하시겠습니까?");
        System.out.println("1. 확인    2. 취소");
        int check2 = sc.nextInt();
        if (check2 == 1) {
            for (Product product : selectCat.getProducts()) {
                if (product.getProductName().equals(productName)) {
                    System.out.println("이미 존재하는 상품입니다!");
                    return;
                }
            }
            // 새로운 Product 객체 생성
            Product newProduct = new Product(productName, price, description, count);
            // 선택한 카테고리에 추가
            categories.get(check1 - 1).addProducts(newProduct);
            System.out.println("상품이 성공적으로 추가되었습니다!");
        } else {
            System.out.println("취소되었습니다!");
        }
    }

    // 관리자 상품 수정
    public void adminEditProduct() {
        System.out.print("수정할 상품명을 입력해주세요: ");
        String productName = sc.nextLine();
        boolean isPresent = false;
        for (int i = 0; i < Category.getAllProducts().size(); i++) {
            Product product = Category.getAllCatOfProduct(i);
            if (product.getProductName().equals(productName)) {
                isPresent = true;
                System.out.printf("현재 상품 정보: %s | %,d원 | %s | 재고: %d개\n\n"
                        , product.getProductName(), product.getPrice(), product.getDescription(), product.getCount());
                System.out.println("수정할 항목을 선택해주세요:\n1. 가격\n2. 설명\n3. 재고수량");
                int check = sc.nextInt();
                sc.nextLine();
                switch (check) {
                    case 1:
                        System.out.println("현재 가격: " + product.getPrice());
                        System.out.print("새로운 가격을 입력해주세요:");
                        int newPrice = sc.nextInt();
                        System.out.printf("%s의 가격이 %,d원 → %,d원으로 수정되었습니다.\n"
                                , product.getProductName(), product.getPrice(), newPrice);
                        product.setPrice(newPrice);
                        break;
                    case 2:
                        System.out.println("현재 설명: " + product.getDescription());
                        System.out.print("새로운 설명을 입력해주세요: ");
                        String newDescription = sc.nextLine();
                        if (newDescription.isEmpty()) {
                            System.out.println("수정 실패!");
                        } else {
                            System.out.println("수정 완료!");
                        }
                        product.setDescription(newDescription);
                        break;
                    case 3:
                        System.out.println("현재 재고: " + product.getCount());
                        System.out.print("추가 수량을 입력해주세요: ");
                        int newCount = sc.nextInt();
                        System.out.printf("%s의 재고가 %d개 → %d개로 수정되었습니다.\n"
                                , product.getProductName(), product.getCount(), product.getCount() + newCount);
                        product.setCount(newCount);
                        break;
                    default:
                        System.out.println("잘못된 입력입니다!");
                }
            }
        }
        if (!isPresent) {
            System.out.println("존재하지 않는 상품입니다!");
        }
    }

    // 관리자 상품 삭제
    public void adminDelProduct() {
        System.out.println("어느 카테고리의 상품을 삭제하시겠습니까?");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println(i + 1 + ". " + categories.get(i).getCategoryName());
        }
        // 카테고리 선택
        int index = 0;
        int check1 = sc.nextInt();
        Category selectCat = categories.get(check1 - 1);
        System.out.println("[ " + selectCat.getCategoryName() + " 카테고리 ]");
        // 카테고리의 상품 리스트에서 상품 하나씩 꺼내와서 출력
        for (Product product : selectCat.getProducts()) {
            System.out.printf("%d. %-18s|%,10d원 | %s\n"
                    , ++index
                    , product.getProductName()
                    , product.getPrice()
                    , product.getDescription());
        }
        int check2 = sc.nextInt();
        Product selectProduct;
        try {
            selectProduct = selectCat.getProduct(check2 - 1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.printf("선택한 상품: %s | %,d원 | %s | 재고: %s개\n\n"
                , selectProduct.getProductName()
                , selectProduct.getPrice()
                , selectProduct.getDescription()
                , selectProduct.getCount());
        System.out.println("정말 삭제하시겠습니까?");
        System.out.println("1. 확인    2. 취소");
        int check3 = sc.nextInt();
        if (check3 == 1) {
            System.out.println("다시 되돌릴 수 없습니다.");
            System.out.println("1. 삭제    2. 취소");
            int check4 = sc.nextInt();
            if (check4 == 1) {
                selectCat.delProduct(selectProduct);
                carts.removeIf(cart -> cart.getProductName().equals(selectProduct.getProductName()));
                System.out.println("삭제 완료!");
                return;
            }
        }
        System.out.println("취소되었습니다!");
    }

    // 관리자 전체 상품 조회
    public void adminAllProducts() {
        int index = 0;
        System.out.println("[ 전체 상품 ]");
        for (Product product : Category.getAllProducts()) {
            System.out.printf("%d. %-18s|%,10d원 | %s\n"
                    , ++index
                    , product.getProductName()
                    , product.getPrice()
                    , product.getDescription());
        }
    }
}
