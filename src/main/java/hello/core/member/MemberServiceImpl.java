package hello.core.member;

public class MemberServiceImpl implements MemberService {
    // 어떤 인터페이스를 상속하는 구현체가 하나밖에 없을 때는 관례상 그 인터페이스 이름 끝에 Impl 을 붙여서 구현체 이름으로 만듦.

    // private final MemberRepository memberRepository = new MemoryMemberRepository();
    // 이 코드는 자세히 보면 우리가 왼쪽에는 인터페이스를 가져다 쓰고 있고, 오른쪽에는 구현체를 가져다 쓰고 있어
    // 즉, 인터페이스에도 의존하고 있고 구현체에도 의존하고 있어.
    // 좋은 코드가 아니야.

    
    
    // 얘도 마찬가지.
    // 프로그램을 하나의 공연이라고 생각해 보자. 서비스를 하나 만들어서 올리는 것과 공연을 하나 기획해서 올리는 것은 비슷한 점이 많다.
    // 레오나르도 디카프리오가 로미오 역할이다. 디카프리오는 줄리엣 역할을 누가 맡아서 할지 상대 배우까지 직접 알아보고 결정해서 섭외하고 초빙한다.
    // 이거는 말이 안 된다. 월권이라고 하면 월권이고, 과다 책임이라고 하면 과다 책임이다. 디카프리오는 배우이고 로미오 역할만 잘 수행하면 된다. 줄리엣 배우를 누가 맡을지는 공연 기획자가 결정해야 한다.
    // 지금 MemberServiceImpl 이 하고 있는 것도 똑같다.
    // MemberService 는 회원 정보를 생성하하거나 저장하거나 조회하는 역할을 한다. MemberServiceImpl 는 그 역할 수행하는 구현체일 뿐이다.
    // 여기서 어떤 매체에 저장할지, 메모리에 잠깐 저장할지 DataBase 에 저장할지, 외부 DB를 쓸지, 그건 MemberServiceImpl가 정할 일이 아니다. 서비스 매니저가 할 일이다.
    // 그런데 코드 내용을 보면 MemberServiceImpl는 MemberRepository 안에 MemoryMemberRepository를 쓸 거라고 자기가 직접 결정해서 넣어주고 있다.
    // 이것은 마치 로미오 역할 배우인 레오나르도 디카프리오가 상대 역할 줄리엣을 누가 맡아서 할지 여배우를 직접 알아보고 결정해서 섭외하고 초빙까지 하는 것과 같다.
    // 이러한 구조는 월권이기도 하고, 원래 맡았던 역할보다 더 많은 일을 시키는 과다 책임이기도 하다.
    // 이러한 문제를 해결하기 위해 AppConfig 라는 공연 기획자를 따로 만들어 두기로 한다.
    // 자 그럼 이 문제를 어떻게 해결해야 할까?


    // private final MemberRepository memberRepository = new MemoryMemberRepository();
    // 위에 있던 코드가 이거.
    // 여기서 구현체를 직접 결정해서 넣는 부분을 삭제함.
    private final MemberRepository memberRepository;
    // 이렇게 하면 구현체는 등장하지 않아. 추상화 (인터페이스)에만 의존해. DIP를 지키고 있는 프로그래밍이지.
    // 근데 이 상태로 실행시키면 NullPointerException 뜨겠지. memberRepository 에 아무것도 들어 있지 않으니까.
    // 그럼 어떻게 넣어주느냐? 생성자에서 넣어주기.
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    // 이러면 MemberRepository 안에 들어갈 구현체를 MemberServiceImpl 내부에서 결정하는 게 아니라, MemberServiceImpl를 생성할 때 외부에서 결정해서 넣어줌.


    @Override
    public void join(Member member) {

        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
    
    // intelliJ 단축키 - Ctrl + 마우스 왼쪽 클릭 : 선언문 or 추상 클래스 바로 가기
    // intelliJ 단축키 - Ctrl + Alt + 마우스 왼쪽 클릭 : 구현 클래스 바로 가기
}
