package hello.coreAutoConfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan (
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
// 위 코드는 컴포넌트로 등록하는 대상에서 제외할 조건 넣는 코드
// AppConfig 우리가 수동으로 Bean 등록하는 연습 했던 class 인데 이 class 에 붙은 @Configuration 어노테이션 안에 까 보면 @Component 어노테이션이 붙어 있음.
// 그래서 일단 예외 처리 해 두는 거.
// 실무에서는 사실 이렇게 예외 대상을 잘 두지는 않음.
// 지금 테스트해볼 때 이게 진짜로 우리가 테스트 하고 싶은 class 에서 등록된 건지, 이전에 짜 두었던 수동 등록 class 에서 등록된 건지 모르니까 이렇게 해둔 거.
public class AutoAppConfig {
    // 우리 AppConfig 에서 Bean 등록하는 법을 배웠지.
    // class 하나 하나 만들고 Bean 등록할 때는 또 이러이러한 코드 추가해 주고...
    // 근데 서비스 규모가 클수록 만들어야 하는 클래스도 많고, 그 클래스들 또 하나하나 다 Bean 등록해 줘야 하고..
    // 누락될 수도 있고 번거롭기도 하고 귀찮고... 실수가 발생하기 쉬움
    // 그래서 스프링에서는 또 그 문제를 해결할 수 있는 기능을 제공함
    // 알아서 쫙 불러와서 Bean 등록 싹 해주고
    // 그러한 기능을 제공하는 어노테이션이 @ComponentScan
    // @ComponentScan 어노테이션이 붙어 있으면 클래스 패스 싹 뒤져서 @Component 어노테이션이 붙은 클래스들 싹 불러서 등록함.
    // 테스트 하려면 @Component 어노테이션을 붙여야겠지.
    // 나중에 @Component 어노테이션을 붙인 클래스들 아래 리스트
    // MemoryMemberRepository.class / RateDiscountPolicy.class / MemberServiceImpl.class / OrderServiceImpl.class


    // 컴포넌트로 등록할 대상들을 어떻게 찾느냐?
    // 탐색을 시작할 위치를 설정할 수 있음.
    // @ComponentScan ( basePackages = "hello.core.member") 이런 식으로
    // 만약 모든 Java 코드 다 뒤지고 라이브러리까지 다 뒤진다면..? 비효율적이겠지. 그래서 이런 기능이 있음.
    // 여러 개 둘 수도 있음. @ComponentScan ( basePackages = "hello.core.member", "hello.core.order") 이런 식으로
    // 클래스를 줄 수도 있음. 그 클래스가 위치한 package 부터 시작함.
    // @ComponentScan ( basePackageClasses = AutoAppConfig.class ) 이런 식으로 하면 AutoAppConfig.class 가 위치한 package 기준으로 탐색 시작함
    // 이런 게 아예 없으면? basePackage 를 아예 지정하지 않으면...? default 값은 무엇이냐
    // @ComponentScan ( basePackageClasses = [현재 @ComponentScan 이 붙어 있는 class]  ) 이렇게 파라미터를 넣어준 거랑 같음.
    // 즉, 현재 @ComponentScan 이 붙어 있는 클래스의 package 를 기준으로 시작해서 하위를 다 뒤짐. 그게 default
    // 그래서 보통 설정 정보 class 는 프로젝트의 root directory 에다가 둠.
    // 그게 관례이고 spring 에서도 권장 사항이고 spring boot 에서도 default 로 이 방식을 사용함.
    // spring boot 프로젝트를 만들면 기본적으로 루트 디렉토리에 CoreApplication 이라는 class 가 있고, 그 클래스에는 @SpringBootApplication 이라는 어노테이션이 붙어 있음.
    // 이 @SpringBootApplication 라는 어노테이션을 까 보면 거기에 @ComponentScan 어노테이션이 붙어 있음.
    // 즉 spring boot 는 기본적으로 @ComponentScan 방식으로 작동하겠다는 뜻.
    // 그래서 스프링 부트를 사용하면 루트 디렉토리 아래에 있는 모든 클래스들을 다 스캔해서 가져옴
    // @ComponentScan 하면 대상이 @Component 만 가져오는 게 아니고, 아래 어노테이션 붙은 애들 다 가져옴.
    // @Component / @Controller / @Service / @Repository / @Configuration
    // 위에 있는 얘네들은 안에 interface 까서 들어가 보면 다 @Component 어노테이션이 붙어 있음.
    // 어노테이션 사이에는 상속 관계가 없어서 이렇게 한 어노테이션이 다른 어노테이션을 들고 있는 걸 인식할 수는 없음
    // 이러한 기능은 Java 언어가 지원하는 게 아니고 Spring 프레임워크가 지원해 주는 기능

    // * 어노테이션별로 스프링이 추가로 수행해주는 기능
    // @Controller => 스프링 MVC 컨트롤러로 인식
    // @Repository => 스프링 데이터 접근 계층으로 인식하고 데이터 계층이 예외를 스프링 예외로 변환해 줌.
    //          > 예를 들어 postgreSQL 에서 던지는 예외랑 Oracle 에서 던지는 예외랑 다르겠지. 근데 그 예외가 Service 계층까지 올라온단 말야. 이거를 중간에서 스프링이 추상화한 예외로 변환해서 던져줌.
    // @Configuration => 앞에서 봤듯이 스프링 설정 정보로 ㅇ니식하고 스프링 빈이 싱글톤을 유지하도록 추가 처리해 줌.
    // @Service => 사실 @Service 는 뭔가 특별한 처리를 따로 하지는 않음. 단지 인간들이 알아보기 위한 기능. 개발자들이 핵심 비지니스 로직들이 있는 클래스구나, 하고 인식하는 데에 도움이 됨.



}
