import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        List<Product> products = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        // 상품 목록 추가 ======================================================
        Product product1 = new Product("Galaxy S25", 1200000
                , "최신 안드로이드 스마트폰", 1);
        Product product2 = new Product("iPhone 16", 1350000
                , "Apple의 최신 스마트폰", 1);
        Product product3 = new Product("MacBook Pro", 2400000
                , "M3 칩셋이 탑재된 노트북", 1);
        Product product4 = new Product("AirPods Pro", 350000
                , "노이즈 캔슬링 무선 이어폰", 1);

        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        //=====================================================================

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
