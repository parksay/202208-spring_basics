package hello.core.member;

import hello.core.AppConfig;
import hello.core.order.OrderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    // MemberService memberService = new MemberServiceImpl();
    // 원래 코드는 위쪽. 수정한 코드가 아래쪽.
    // MemberServiceImpl을 직접 생성하는 게 아니라 AppConfig한테 받아다가 씀.

    MemberService memberService;

    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    // 이전 방식은 MemberApp 이 MemberServiceImpl 를 생성하고, MemberServiceImpl 는 그 안에서 MemoryMemberRepository 를 생성했지.
    // 마치 연쇄 반응처럼 사슬 구조였지.
    // 지금은 그 역할을 모두 AppConfig 가 다 해줌. 나머지 객체들, 연극의 배우들은 모두 AppConfig 를 바라보고 AppConfig 한테서 받아다가 씀.

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
