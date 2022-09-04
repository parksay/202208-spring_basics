package hello.coreSpring.order;

public interface OrderService {

    // 주문 정보를 관리하는 역할

    // 누가 무엇을 얼마에 샀는지 던져주면 그 정보를 바탕으로 주문 정보를 만들어 줌.
    Order createOrder(Long memberId, String itemName, int itemPrice);

}
