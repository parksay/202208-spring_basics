package hello.core.autowired;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.lang.reflect.Member;
import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutoWiredOptional() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }


    private static class TestBean {

        // 참고 : 여기서 파라미터로 넣어주는 Member 타입은 우리 애플리케이션에서 만든 그 Member 가 아님.
        // 그니까 spring 이 관리하는 클래스가 아님.
        // spring 이 의존관계 주입하려고 찾아 보면 그런 애들은 없는 상태겠지
        // 의존 관계를 주입할 때 옵션 처리 방법 세 가지
        // @Autowired 에 (required = false) 옵션 붙이기 / 파라미터에 @Nullable 어노테이션 붙이기 / Optional 타입 사용하기

        //
        @Autowired (required = false)
        public void setNoBean1(Member noBean1) {
            // @Autowired 에 옵션으로 (required = false) 를 붙여 줬음
            // 이렇게 하면 실행하다가 의존 관계 주입하려 봤는데 없으면 그냥 그 메소드 자체를 실행 안 함.
            // 그 스프링이 bean 등록할 때 두 단계라고 했지.
            // 처음에는 싹 다 찾아서 생성해서 bean 등록하고 둘째로 의존관계 주입하고 이 의존관계 주입할 때 @Autowired 도 싹 다 모아서 실행하겠지.
            // 근데 이때 옵션으로 (required = false)가 붙어 있으면 그 @Autowired 어노테이션이 붙어 있는 메소드 자체를 호출하지 않음.
            //
            System.out.println("noBean1 = " + noBean1);
        }


        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {
            // 위에서는 주입할 의존 관계를 못 찾으면 그 메소드 자체를 아예 실행을 안 했지.
            // 메소드 단위로 생각하는 거
            // 이번에는 변수 단위로 생각해 보자고
            // 의존 관계를 주입 받는 변수 하나 하나에 @Nullable 어노테이션을 붙일 수도 있음
            // 이렇게 하면 메소드 자체는 실행하고 파라미터들은 하나하나 다시 살펴봄.
            // 의존 관계 넣어주려고 봤는데 넣어줄 애를 못 찾으면 그냥 냅다 null 넣어 버림.
            System.out.println("noBean2 = " + noBean2);
        }


        @Autowired 
        public void setNoBean3(Optional<Member> noBean3) {
            // 이 방식은 Java 8 버전부터 지원하는 Optional 이라는 타입 활용
            // 있으면 그 값을 넣어주고 값이 없으면 Optional.empty 라는 값을 넣어줌
            System.out.println("noBean3 = " + noBean3);
        }

        // 이것 말고도 @Autowired 에는 또 다른 기능이 있음
        // 의존 관계 주입 받을 때 찾아온 Bean 들을 모두 꺼내올 수가 있음.
        // 그 방법은 아래 디렉토리
        // test > autowired > AllBeanTest
    }
}
