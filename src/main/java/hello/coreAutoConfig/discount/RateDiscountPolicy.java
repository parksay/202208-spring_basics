package hello.coreAutoConfig.discount;

import hello.coreAutoConfig.member.Grade;
import hello.coreAutoConfig.member.Member;
import org.springframework.stereotype.Component;

@Component
public class RateDiscountPolicy implements DiscountPolicy {

    // 할인 정책 > 일정한 비율만큼 할인해주는 정책
    private int discountPercent = 10;   // 원래 가격에서 몇 퍼센트를 할인해 줄지

    @Override
    public int discount(Member member, int price) {
        // 이 사람이 이 가격만큼 주문했는데 얼마 할인해 줘야 해요?
        if(member.getGrade() == Grade.VIP) {
            // 원래 가격 곱하기 100분의 퍼센트
            //return price * (discountPercent / 100);
            // => 처음에 이렇게 했다가 return 값이 무조건 0원임. 조건문 안 들어가나 했는데 도저히 안 들어갈 이유가 없음. 알고 보니 return 구문이 문제였음.
            // discountPercent 가 자료형이 int임. 10에서 100을 나누니까 10/100. 그러니까 0.1 이 나와야 하는데 자료형이 int라서 그냥 0이 되어버림.
            // 결과는 price * 0 이렇게 되니까 return 값이 무조건 0이었던 것.
            // 하려면 price랑 discountPercent랑 괄호해 줘야지.
            // return (price * discountPercent) / 100;
            return price * discountPercent / 100;
        } else {
            return 0;
        }
        // intelliJ 단축키 - Ctrl + Shift + T 누르면 테스트 클래스 만드는 창이 뜸
        // 옵션 - 테스트 라이브러리는 JUnit5 쓰고 클래스 이름은 원래 클래스 이름 끝에 Test 덧붙임

    }
}
