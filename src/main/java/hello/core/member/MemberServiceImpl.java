package hello.core.member;

public class MemberServiceImpl implements MemberService {
    // 어떤 인터페이스를 상속하는 구현체가 하나밖에 없을 때는 관례상 그 인터페이스 이름 끝에 Impl 을 붙여서 구현체 이름으로 만듦.

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    @Override
    public void join(Member member) {

        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
