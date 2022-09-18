package hello.coreAutoConfig.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
// 이 class 는 ComponentScan 할 때 같이 스캔해서 불러와서 Bean 으로 등록해주기
public class MemberServiceImpl implements MemberService {


    private final MemberRepository memberRepository;

    @Autowired
    // 근데 우리 수동으로 설정 넣을 때는 의존 관계를 간접적으로 명시할 수 있었는데 자동으로 등록돼 버리면 의존 관계 주입은 어떻게 지정하나?
    // 그래서 Autowired 라는 걸 씀.
    // 그러면 알아서 type 에 맞는 Bean 을 찾아다가 등록해줌.
    // ac.getBean(MemberRepository.class);
    // 이 코드가 자동으로 들어가는 것과 같은 효과.
    // 이전에 수동으로 등록할 때는 AppComfig.clas 클래스가 따로 있어서 거기서 @Bean 등록하고 의존관계도 다 명시해 줬지
    // 이제는 그런 코드 자체가 다 사라짐.
    // 그래서 이 class 안에서 어떻게든 해결해야 하는데, 그래서 쓰는 게 이 @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public void join(Member member) {

        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public MemberRepository getMemberRepository() {
        return this.memberRepository;
    }
}
