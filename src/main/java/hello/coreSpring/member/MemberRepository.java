package hello.coreSpring.member;

public interface MemberRepository {
    // 회원 정보를 저장소와 주고 받는 다리.
    // 회원 정보를 저장하거나, 아이디를 주면 회원 정보를 되돌려주는 기능.

    void save(Member member);

    Member findById(Long memberId);
}
