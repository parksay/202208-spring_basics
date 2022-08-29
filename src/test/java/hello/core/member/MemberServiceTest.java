package hello.core.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    MemberService memberService = new MemberServiceImpl();

    // 요즘 애플리케이션 개발자라면 테스트 코드 작성은 선택이 아니라 필수
    // 기능 구현하는 시간보다 구현한 기능이 잘 작동하는지 시험하는 테스트 코드를 짜는 시간이 더 많을 정도
    @Test
    void join() {
        // given
        Member member = new Member(1L, "memberA", Grade.VIP);


        // when
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        // then
        Assertions.assertThat(member).isEqualTo(findMember);
        //assertj.core 패키지를 가져다 쓸 것. 더 편리.


    }
}
