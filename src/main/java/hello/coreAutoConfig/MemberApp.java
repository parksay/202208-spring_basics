package hello.coreAutoConfig;

import hello.coreAutoConfig.member.Grade;
import hello.coreAutoConfig.member.Member;
import hello.coreAutoConfig.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        // 위처럼 하면 AppConfig 안에서 @Configuration 찾아서 @Bean 붙어 있는 메소드들을 모두 등록하고 관리해 줌.
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        // @Bean 붙은 메소드의 메소드 이름을 기본적으로 들고감. 그 이름으로 bean을 받아옴.
        // @Bean 어노테이션에 이름을 바꾸는 파라미터도 있긴 한데 웬만하면 관례상 그냥 그 메소드 이름 그대로 사용하는 쪽이 좋음.

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);


        System.out.println("new member = " + member.getName());
        System.out.println("find member = " + findMember.getName());
    }

//    20:04:34.145 [main] DEBUG org.springframework.context.annotation.AnnotationConfigApplicationContext - Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@1ae369b7
//    20:04:34.155 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.context.annotation.internalConfigurationAnnotationProcessor'
//    20:04:34.255 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.context.event.internalEventListenerProcessor'
//    20:04:34.255 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.context.event.internalEventListenerFactory'
//    20:04:34.261 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.context.annotation.internalAutowiredAnnotationProcessor'
//    20:04:34.262 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.context.annotation.internalCommonAnnotationProcessor'
//    20:04:34.267 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'appConfig'
//    20:04:34.270 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'memberService'
//    20:04:34.285 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'memberRepository'
//    20:04:34.305 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'orderService'
//    20:04:34.305 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'discountPolicy'
//    실행하면 로그창에 위와 같은 로그가 뜸.
//    Creating shared instance of singleton bean => Bean을 싱글톤으로 등록하고 그 instance 를 만들고 있대
//    위에 있는 다섯 개 'org.springframework.context.~~~~' 는 스프링에서 기본적으로 쓰는 다섯 개 Bean
//    그리고 아래 다섯 개는 우리가 AppConfig 에 @Bean 해서 등록했던 메소드들 다섯 개

}
