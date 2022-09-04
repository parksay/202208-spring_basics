package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
// intelliJ 단축키 - 앞에 class name 에서 alt + enter 누르면 static import 있음. Add on-demand static import
// 위에 모여 있는 게 static import 한 class 목록들임. import static ~~~

class RateDiscountPolicyTest {


    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 10% 할인이 적용되어야 한다")
    void vip_o() { // vip라면 할인이 잘 적용되어야 한다. vip => o

        // given
        // 회원 정보 하나 생성하기 - vip 등급으로
        Member member = new Member(1L, "memberVIP", Grade.VIP);
        // intelliJ 단축키 - Ctrl + Alt + V 누르면 Introduce Variable. return type 맞춰서 변수 선언해 줌.

        //when
        // vip인 멤버가 10000원짜리 물건을 사려고 하면 얼마를 할인해 줘야 하나요?
        int discount = discountPolicy.discount(member, 10000);

        // then
        // 할인 받은 가격은 1000원과 같아야 한다
        assertThat(discount).isEqualTo(1000);
        // 라이브러리는 assertj 사용
    }

    @Test
    @DisplayName("VIP가 아니라면 할인이 적용되지 않아야 한다")
    void vip_x() { // 성공 테스트도 중요하지만 실패 테스트도 중요함. 꼭 만들어야 함. vip x
        // given
        // 회원 정보 하나 생성하기 - vip 등급이 아니도록
        Member member = new Member(2L, "memberBasic", Grade.BASIC);

        //when
        // vip가 아닌 멤버가 10000원짜리 물건을 사려고 하면 얼마를 할인해 줘야 하나요?
        int discount = discountPolicy.discount(member, 10000);

        // then
        // 할인 받은 가격은 0원과 같아야 한다
        assertThat(discount).isEqualTo(0);
        // intelliJ 단축키 - 앞에 class name 에서 alt + enter 누르면 static import 있음. Add on-demand static import
    }
}