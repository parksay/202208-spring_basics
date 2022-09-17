package hello.core.singleton;

public class StatefulService {

    // 싱글톤 패턴으로 만든 클래스는 stateful 하게 만들면 안 됨.
    // 여기서 말하는 state 는 react나 vue 에서 말하는 state와 같은 말임.
    // 상태 관리 라이브러리, 할 때 그 '상태'와 같은 말임.
    // 클래스에 어떤 상태가 있는 거지. flag가 true인 상태, false인 상태, member가 vip인 상태, basic인 상태...
    // 싱글톤으로 만드는 클래스는 이러한 상태가 없어야 함. 바꿀 수 있는 상태가 없어야 함. 상태를 바꿀 수 없어야 함.
    // 즉, 싱글톤으로 만드는 클래스는 모든 것들이 '읽기 전용'이어야 함.

    private int price; // 상태를 유지하는 필드

    public void order(String name, int price) {
        System.out.println("price = " + price);
        this.price = price; // 여기서 지금 state 가 발생하지

    }

    public int getPrice() {
        // 뭐... 이전에 주문했던 상품의 가격을 저장해뒀다가 나중에 또 꺼내서 쓰려고 이렇게 했을 수도 있겠지
        //
        return this.price;

    }

    // 이렇게 싱글톤으로 디자인한 클래스가 state 값을 가지게 되면 일어나는 문제점은..?
    // => StatefulServiceTest.class


    // 그럼 어떻게 하면 좋나..? 나는 주문한 금액을 정확하게 받아서 들고 있고 싶은데?
    // 공유하고 있는 state값은 그냥 지우고 이렇게 하면 되지.
    // public int order(String name, int price) {
    //     System.out.println("price = " + price);
    //     // ~~~~~~~~~~~~ 처리
    //     return price;
    // }
}
