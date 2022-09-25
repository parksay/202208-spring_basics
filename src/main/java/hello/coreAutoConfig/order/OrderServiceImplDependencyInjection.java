package hello.coreAutoConfig.order;

import hello.coreAutoConfig.discount.DiscountPolicy;
import hello.coreAutoConfig.member.Member;
import hello.coreAutoConfig.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImplDependencyInjection implements OrderService {

    // 이렇케 클래스에 멤버 변수가 있어
    // 이 변수들에 의존 관계를 주입해 줘야겠지
    // 의존 관계를 주입해 주는 방법을 알아보자.
    // 총 네 가지가 있어.
    // 생성자 주입 / 수정자 주입 / 필드 주입 / 일반 메소드 주입
    // 생성자 주입은 생성자에다가 파라미터로 받아서 바로 필드로 넣어주는 방법
    // 수정자 주입은 setter 함수로 넣는 방법
    // 필드 주입은 필드에서 멤버 변수를 선언할 때 바로 주입하는 방법
    // 일반 메소드는 그냥 다른 메소드에서 로직 진행하다가 넣는 방법
    private MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;




    // 첫째로 생성자 주입.
    private final MemberRepository memberRepository1;
    private final DiscountPolicy discountPolicy1;
    @Autowired
    public OrderServiceImplDependencyInjection(MemberRepository memberRepository1, DiscountPolicy discountPolicy1) {
        this.memberRepository1 = memberRepository1;
        this.discountPolicy1 = discountPolicy1;

        // 생성자에 이렇게 해 두면 자동으로 파라미터들에 의존관계 주입해 줌
        // 생성자 파리미터에 들어 있는 애들을 type 으로 검색해서 맞는 애들 넣어줌
        // 클래스에 생성자가 딱 하나 있으면 @Autowired 를 생략해도 됨.
        // 생성자가 두 개 이상 있으면 스프링이 자동으로 인스턴스를 생성할 때 어떤 생성자를 골라서 자기가 들고 있어야 하는지 혼란스럽겠지.
        // 그래서 @Autowired 로 '스프링 님아 이 생성자로 실행해서 관리해 주세요' 라고 표시해서 넘겨주는 것
        // 의존관계를 이렇게 넣어주는 방법을 생성자 주입이라고 함.
        // 객체를 생성할 때 파라미터로 값을 넣어주는 방법.
        // 이렇게 생성자로 넣는 방법은 one & only 일 때.
        // 첫째로  나중에 수정할 일이 없어.
        // 수정자, 그러니까 setter 함수가 없어.
        // 공연이 한 번 시작되면 중간에 배우를 바꿀 일이 없는 거지.
        // 그래서 클래스에서 멤버 변수를 선언할 때는 final 키워드를 넣어줌.

        // 둘째로 꼭 필요한 값들이야.
        // 만드는 데에 꼭 필요한 재료들이니까 만들 때부터 일단 넣어달라고 하는 거겠지.
        // 객체를 생성할 때 같이 넣어줘야 하는 파라미터들은 개발자들끼리 보통 필수값이라고 생각해.

    }

    // 둘째로 수정자 주입 방식
    private MemberRepository memberRepository2;
    private DiscountPolicy discountPolicy2;
    @Autowired
    public void setMemberRepository2(MemberRepository memberRepository2) {
        System.out.println("memberRepository2 = " + memberRepository2);
        this.memberRepository2 = memberRepository2;
    }
    @Autowired
    public void setDiscountPolicy2(DiscountPolicy discountPolicy2){
        System.out.println("discountPolicy2 = " + discountPolicy2);
        this.discountPolicy2 = discountPolicy2;
//        // 의존관계를 이렇게 주입해 주는 게 수정자 방식
//        // setter 함수 쓰는 게 수정자 방식이라고 했지.
//        // 스프링에서 빈을 등록하는 라이프 사이클이 크게 두 가지라고 했어.
//        // 첫째는 ComponentScan 같은 걸로 빈을 싹 다 찾아서 일단 등록부터 해
//        // 그러고 나서 둘째는 이렇게 @Autowired 붙어 있는 메소드 다 찾아서 그 안에 의존 관계들을 주입해 줘.
//        // 그럼 객체 생성하고 나서 그 클래스에 있던 멤버 변수들에도 다 의존 관계가 들어가겠지.
//        // 그 클래스에서 쓰는 멤버 변수들에는 다 객체가 들어가 있는 상태가 되겠지.
//        // 이렇게 넣는 방식은 선택 & 변경 가능성 있을 때
//        // 꼭 필요한 거라면 생성할 때 생성자 메소드에 파라미터로 넣어달라고 요구를 했겠지.
//        // 바꿀 가능성이 없다면 굳이 setter 함수를 만들어 놓지 않았겠지
//        // 참고로 이렇게 getter / setter 로 클래스 필드를 조작하는 방식을 '자바 빈 프로퍼티 규약' 이라고 함.
//        // class Data {   private int data;   public void setData(data) { this.data = data; }     public int getData() { return this.data; }  }
    }



    // 셋째로 필드 주입 방식
    @Autowired    private MemberRepository memberRepository3;
    @Autowired    private DiscountPolicy discountPolicy3;
    // 이렇게 필드 선언할 때 바로 @Autowired 박아 버리는 방법.
    // 하지만 이렇게 넣는 방식은 추천하지 않음
    // 외부에서 변경이 불가능하기 때문에 테스트 하기가 어려움
    // DI 프레임워크가 없으면 아무것도 할 수가 없음.
    // 예를 들어서 테스트 메소드 하나 만들어볼게
//    @Test
//    void fieldInjectionTest() {
//        OrderServiceImplDependencyInjection osidi = new OrderServiceImplDependencyInjection();
//        osidi.createOrder(1L, "itemA", 50000);
//        // 아니 나는 그냥 createOrder() 메소드 하나만 테스트해보고 싶은 상황.
//        // 근데 이렇게 new OrderServiceImplDependencyInjection(); 생성자 호출하고 나서 바로 createOrder() 호출하면 어떻게 되겠어.
//        // memberRepository3 랑 discountPolicy3 랑 안에 비어 있어서 nullPointerException 뜨겠지.
//        // Spring Framework 로 실행해서 DI 가 먼저 수행된 코드가 아니니까.
//        // 그래서 따로 그 멤버 변수들 안에 객체를 내가 원하는 걸로 채워 넣어주고 싶은데 그럴 방법이 없지
//        // 그럼 setter 메소드를 변수마다 하나씩 만들어서 채워넣어줘야 한다는 거.
//        // 결국 그럼 수정자 주입 방식으로 돌아온 거지. 그거랑 똑같지. 그럴 거면 뭐하러 필드 주입해서 써.
//        // 이렇게 테스트 코드 짤 수가 없다는 거.
//    }

    // 넷째로 일반 메소드 주입 방식
    private MemberRepository memberRepository4;
    private DiscountPolicy discountPolicy4;

    @Autowired
    public void init( MemberRepository memberRepository4, DiscountPolicy discountPolicy4) {
        // ~~~~~ do something
        this.memberRepository4 = memberRepository4;
        this.discountPolicy4 = discountPolicy4;
        // ~~~~~ do something
        // 이렇게 그냥 아무 메소드에다가 대고 주입 받아도 됨
        // 한 번에 여러 필드를 주입 받을 수 있어서 좋음
        // 사실 뭐 setter 수정자 주입이랑 구조적으로는 별로 다를 바가 없긴 하지.
        // 근데 실무에서는 거의 생성자 주입이랑 수정자 주입으로 다 해결하지 이렇게 일반 메소드에서 주입하는 방식은 거의 사용하지 않음
        // 그냥 가능은 하다, 정도만 알아두기

    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

}
