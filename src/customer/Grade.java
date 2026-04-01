package customer;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Grade {
    BRONZE(1, 0),
    SILVER(2, 0.05),
    GOLD(3, 0.1),
    PLATINUM(4, 0.15);

    private static final Map<Integer, Grade> BY_ORDER =
            Arrays.stream(values())
                    .collect(Collectors.toUnmodifiableMap(Grade::getOrderNum, Function.identity()));

    private final int orderNum;
    private final double discountRate;

    Grade(int orderNum, double discountRate) {
        this.orderNum = orderNum;
        this.discountRate = discountRate;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public static Grade fromOrderNum(int orderNum) {
        Grade grade = BY_ORDER.get(orderNum);
        if (grade == null) {
            throw new IllegalArgumentException("해당 번호와 일치하는 등급이 없습니다.");
        }
        return grade;
    }

    public int getOrderNum() {
        return orderNum;
    }
}
