package hello.coreAutoConfig.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

// 우리는 @Qualifier 를 쓰는 거랑 똑같이 쓸 거기 때문에 @Qualifier 어노테이션에서 설정들 다 긁어 모으기
// 사실 어노테이션끼리는 상속 개념이 없기 때문에 이렇게 어노테이션을 일일이 모아서 안에 붙여줘야 함
// 이런 기능을 그동안 Spring Framework 에서 해 주고 있었기 때문에 우리가 모른 채로 편하게 쓸 수 있던 것
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
// 어디에 붙일 수 있는지, 필드에 붙이거나 메소드에 붙이거나 파라미터에 붙이거나.....
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy {

    // 예를 들어서 내가 선언하는 곳에서는 @Qualifier("mainDiscountPolicy") 이렇게 붙여놓았어
    // 근데 불러다 쓰는 곳에서는 실수로 @Qualifier("mainnDiscountPolicy") 이렇게 오타가 날 수도 있잖아
    // 그런데 이런 실수들은 컴파일 단계에서 잡을 수가 없음
    // 그냥 "~~" 해서 넣는 문자열이기 때문에 런타임에서 터질 수밖에 없겠지
    // 이런 걸 방지하기 위해서 많이 쓰는 거라면 어노테이션을 따로 만들어서 쓸 수도 있음.
    // @Qualifier 랑 똑같은 기능의 어노테이션을 새로 만들고, 그 안에 @Qualifier("~~~") 를 붙여 주는 거지.

}
