package customer;

public class Customer {
    // 고객 정보를 관리하는 클래스입니다.

    //속성
    private final String customerName;
    private final String email;
    private String grade;

    // 생성자
    Customer(String customerName, String email) {
        this.customerName = customerName;
        this.email = email;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getEmail() {
        return email;
    }

    public String getGrade() {
        return grade;
    }
}
