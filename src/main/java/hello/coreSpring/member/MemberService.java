package hello.coreSpring.member;

public interface MemberService {
    // 회원 정보를 관리하는 역할.
    // 회원 정보를 저장하거나, 회원 정보를 조회하는 기능.


    void join(Member member);
    Member findMember(Long memberId);

}
