package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;

public class MemberApp {

    public static void main(String[] args) {
        // MemberService memberService = new MemberServiceImpl();
        // 원래 코드는 위쪽. 수정한 코드가 아래쪽.
        // MemberServiceImpl을 직접 생성하는 게 아니라 AppConfig한테 받아다가 씀.
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        // 이전 방식은 MemberApp 이 MemberServiceImpl 를 생성하고, MemberServiceImpl 는 그 안에서 MemoryMemberRepository 를 생성했지.
        // 마치 연쇄 반응처럼 사슬 구조였지.
        // 지금은 그 역할을 모두 AppConfig 가 다 해줌. 나머지 객체들, 연극의 배우들은 모두 AppConfig 를 바라보고 AppConfig 한테서 받아다가 씀.
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);


        System.out.println("new member = " + member.getName());
        System.out.println("find member = " + findMember.getName());
    }
}
