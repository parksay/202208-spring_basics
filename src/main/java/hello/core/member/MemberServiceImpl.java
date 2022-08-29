package hello.core.member;

public class MemberServiceImpl implements MemberService {
    // 어떤 인터페이스를 상속하는 구현체가 하나밖에 없을 때는 관례상 그 인터페이스 이름 끝에 Impl 을 붙여서 구현체 이름으로 만듦.

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    // 이 코드는 자세히 보면 우리가 왼쪽에는 인터페이스를 가져다 쓰고 있고, 오른쪽에는 구현체를 가져다 쓰고 있어
    // 즉, 인터페이스에도 의존하고 있고 구현체에도 의존하고 있어.
    // 좋은 코드가 아니야.

    @Override
    public void join(Member member) {

        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
