package hello.core.beanfind;

import hello.coreSpring.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class ApplicationContextInfoTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);


    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean() {
        // 이름들 쭉 문자열 배열로 받아서
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        // intelliJ 단축카 - list 나 반복문 돌릴 수 있는 대상이 있으면 "iter" 치고 탭 누르면 알아서 반복문 만들어 줌. forEach 문 기능. (iter = iterator 약자)

        // 이름들이 들어 있는 배열을 재료로 반복문 한 바퀴 돌기
        for(String beanDefinitionName : beanDefinitionNames) {
            // 이름으로 빈 꺼내고
            Object bean =  ac.getBean(beanDefinitionName);;
            // 빈 정보 출력
            System.out.println("beanDefinitionName = " + beanDefinitionName + "bean = " + bean);
        }
    }


    @Test
    @DisplayName("애플리케이션 빈만 출력하기")
    void findApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        // 위에서는 모든 빈이 나오니까 헷갈려. 우리가 만든 빈인지 스프링 기본 빈인지. 우리가 만든 빈만 출력하기.

        for(String beanDefinitionName : beanDefinitionNames) {
            // BeanDefinition 이라고 해서 빈 안에 정보를 들고 있는 객체
            BeanDefinition beanDefinition  = ac.getBeanDefinition(beanDefinitionName);

            // bean이 맡은 역할 role 맡은 역할. 기능. 출력하기.
            // BeanDefinition.ROLE_APPLICATION  => 애플리케이션 실행하는 데에 필요한 빈들
            // BeanDefinition.ROLE_INFRASTRUCTURE  =>  스프링 컨테이너 내부에서 기본적으로 사용하는 빈
            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                Object bean = ac.getBean(beanDefinitionName);
                System.out.println("name = " + beanDefinitionName + "// object = " + bean);

            }
        }
    }

}
