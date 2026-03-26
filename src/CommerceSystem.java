import java.util.List;
import java.util.Scanner;

public class CommerceSystem {
    // 커머스 플랫폼의 상품을 관리하고 사용자 입력을 처리하는 클래스

    // 속성
    List<Product> products;
    Scanner sc = new Scanner(System.in);

    // 생성자
    CommerceSystem(List<Product> products) {
        this.products = products;
    }

    // 사용자 입력 처리 메서드
    public void start() {
        System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ]");
        // 상품 인덱스
        int index = 0;
        // 상품 리스트 하나씩 꺼내와서 출력
        system: while (true) {
            // string format 왼쪽 정렬, 오른쪽 정렬, "," 넣기, 여백 맞추기
            System.out.printf("%-18s",index + 1 + ". " + products.get(index).productName);
            System.out.printf("|%,10d원 |", products.get(index).price);
            System.out.println(" " + products.get(index).description);
            index++;
            // 리스트 인덱스 범위 넘어가면 사용자 입력
            if (index == products.size()) {
                // 인덱스 범위 오류 방지 초기화
                index = 0;
                System.out.printf("%-16s| 프로그램 종료\n", "0. 종료");
                int check = sc.nextInt();
                switch (check) {
                    case 0:
                        System.out.println("커머스 플랫폼을 종료합니다.");
                        break system;
                }
            }
        }
    }
}
