package hello.core.singleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();
    // 클래스 SingletonService 가 static 으로 자기 자신의 인스턴스를 하나 들고 있어
    // 모든 SingletonService 클래스가 하나의 인스턴스를 공유하고 있는 구조가 되겠지
    // 근데 밖에서 외부에서 이 SingletonService 클래스의 인스턴스를 가져다가 써야 할 때가 있을 거 아냐
    // 그럼 그럴 때는 어떻게 이걸 받아다 쓰냐?
    // 그래서 만든 게 아래 getInstance 메소드임.

    public static SingletonService getInstance() {
        return instance;
    }
    // 이렇게 하면 다른 데서는 SingletonService singletonService = SingletonService.geInstance();
    // 위처럼 불러다 쓰겠지.

    // 아니 근데, 내가 아무리 이렇게 잘 마련해 두고 주석까지 친절하게 달아 두어도 그냥 각자 인스턴스 만들어 버리면 그만임.
    // SingletonService singletonService =  new SingletonService();
    // 그래서 이런 걸 막기 위해서 생성자를 일부러 따로 만들어 두고, 그 접근제어자를 private 으로 못을 박음
    private SingletonService () {
        // 이렇게
    }

    // 자 이렇게 해서 싱글톤 패턴을 만들어 봤는데.
    // 여러 클라이언트가 여러 번 불러도 인스턴스를 하나만 생성한다는 장점이 있지
    // 인스턴스 한 번 만드는 데에 일을 1000만큼 해야 한다면, 이미 만들어 둔 인스턴스를 가져오는 건 1 정도밖에 되질 않아
    // 그만큼 코드를 효율적으로 만들어 주지만 단점이 없는 건 아니야
    // 1. 일단 가장 먼저, 뭐 private 으로 생성자 만들고, static으로 instance 만들고... 기본적인 코드가 이것저것 많이 들어감.
    // 2. 클라이언트가 특정한 클래스의 인스턴스를 직접 지정해서 꺼내오는 거기 때문에 추상화에 의존하지 않고 구현체 클래스에 의존하게 된다는 점. DIP, OCP를 위반하게 될 가능성이 높다는 점.
    // 3. .... 이것 말고도 많음. 유연성이 떨어지고, 테스트를 하기 어렵고, private 으로 생성자를 만들다 보니 자식 클래스를 만들기 어렵고..
    // 그런데 스프링을 이용하면 이런 단점들을 모두 해결해 주면서 싱클톤 패턴을 사용할 수 있어.
    // 스프링은 어떻게 이런 기능을 가능하게 하는지 알아보게

}
