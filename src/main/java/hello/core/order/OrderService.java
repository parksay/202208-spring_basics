package hello.core.order;

public interface OrderService {

    // 클라이언트가 주문을 하면 주문을 생성하는 역할

    // 누가 무엇을 얼마에 샀는지 던져주면 그 정보를 바탕으로 주문 정보를 만들어 줌.
    Order createOrder(Long memberId, String itemName, int itemPrice);

}
