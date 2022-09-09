package hello.core.beanfind;

import hello.coreSpring.AppConfig;
import hello.coreSpring.member.MemberRepository;
import hello.coreSpring.member.MemoryMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextSameBeanFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);
    // 이렇게 해서 밑에 SameBeanConfig 클래스를 configuration 으로 해서 ac를 생성하면 bean 이 딱 두 개 등록됨.
    // memberRepository1랑 memberRepository2. 그 두 빈의 type은 모두 MemberRepository
    // 똑같은 타입으로 빈이 두 개 등록돼 있는 것.

    @Test
    @DisplayName("타입으로 조회 시 같은 타입이 둘 이상 있으면 중복 오류가 발생")
    void findBeanByTypeDuplicate() {
        // MemberRepository beans =  ac.getBean(MemberRepository.class);
        // 이렇게 검색하면 MemberRepository 라는 type으로 Bean 이 두 개 나왔는데 이거 둘 중에 뭐 꺼내야 함? 하고 예외 던짐.
        // NoUniqueBeanDefinitionException => 유니크하지가 않다
        // 그 자체를 테스트로 만들어 보자.
        // 'ac.getBean(MemberRepository.class) 이 구문을 실행하면 NoUniqueBeanDefinitionException 예외가 나와야 해' 라고
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(MemberRepository.class));

    }

    @Test
    @DisplayName("타입으로 조회 시 같은 타입으로 Bean이 둘 이상 등록돼 있으면 그때는 빈 이름을 지정하면 된다")
    void findBeanByTypeAndName() {
        MemberRepository memberRepository =  ac.getBean("memberRepository1", MemberRepository.class);

        assertThat(memberRepository).isInstanceOf(MemberRepository.class);
    }

    @Test
    @DisplayName("어떤 타입으로 등록된 모든 bean 꺼내오기")
    void findAllBeanByType() {
        Map<String, MemberRepository> beansOfType=  ac.getBeansOfType(MemberRepository.class);
        // getBeansOfType 하면 Map에 담아서 나옴
        for (String key : beansOfType.keySet()) {
            System.out.println("key  = " + key + " // value = " + beansOfType.get(key));
        }
        System.out.println("beansOfType = " + beansOfType);
        assertThat(beansOfType.size()).isEqualTo(2);
    }



    @Configuration
    static class SameBeanConfig {
        // 지금 클래스 안에 클래스를 만들었음 ApplicationContextSameBeanFindTest > SameBeanConfig
        // 이러면, 그 클래스는 이 클래스 안에서만 쓰겠다는 scope
        // 마치 멤버 변수, 멤버 메소드처럼

        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
        // 같은 객체를 return 한다고 해서 틀린 건 아님. 충분히 가능한 상황. 생성자 만들 때 안에 파라미터를 다른 걸 넣어줄 수도 있는 거고.


    }
}
