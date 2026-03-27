import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommerceSystem {
    // 커머스 플랫폼의 상품을 관리하고 사용자 입력을 처리하는 클래스
    Scanner sc = new Scanner(System.in);

    // 속성
    // 카테고리 리스트 관리
    List<Category> categories = new ArrayList<>();

    // 메인 시스템 시작 메서드
    public void start() {
        int index = 0;
        System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
        while (true) {
            System.out.println(index + 1 + ". " + categories.get(index).getCategoryName());
            index++;
            // 리스트 인덱스 범위 넘어가면 사용자 입력
            if (index == categories.size()) {
                // 인덱스 범위 오류 방지 초기화
                index = 0;
                System.out.printf("%-10s| 프로그램 종료\n","0. 종료");
                int selectNum = sc.nextInt();
                if (selectNum == 0) {
                    System.out.println("커머스 플랫폼을 종료합니다.");
                    break;
                } else {
                    checkCategory(categories.get(selectNum - 1).getProducts(), selectNum);
                }
            }
        }
    }



    // 카테고리 상품 리스트 조회 메서드
    public void checkCategory(List<Product> products, int selectNum) {
        // 상품 인덱스
        int index = 0;
        System.out.println("[ " + categories.get(selectNum - 1).getCategoryName() + " 카테고리 ]");
        // 상품 리스트 하나씩 꺼내와서 출력
        while (true) {
            // string format 왼쪽 정렬, 오른쪽 정렬, "," 넣기, 여백 맞추기
            System.out.printf("%-18s",index + 1 + ". " + products.get(index).productName);
            System.out.printf("|%,10d원 |", products.get(index).price);
            System.out.println(" " + products.get(index).description);
            index++;
            // 리스트 인덱스 범위 넘어가면 사용자 입력
            if (index == products.size()) {
                System.out.println("0. 뒤로가기");
                int check = sc.nextInt();
                if (check != 0) {
                    System.out.print("선택한 상품: " + products.get(check - 1).productName);
                    System.out.printf(" |%,10d원 |", products.get(check - 1).price);
                    System.out.print(" " + products.get(check - 1).description);
                    System.out.println(" | 재고: " + products.get(check - 1).count + "개\n");
                }
                System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
                break;
            }
        }
    }

    // 카테고리 추가 세터
    public void setCategories(Category category) {
        categories.add(category);
    }
}
