package hello.core.scan.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

public class ComponentFilterAppConfigTest {

    @Test
    void filterScan() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
        BeanA beanA  = ac.getBean("beanA", BeanA.class);
        Assertions.assertThat(beanA).isNotNull();

        // BeanB beanB  = ac.getBean("beanB", BeanB.class);
        // BeanB 는 등록이 안 돼야 하는 게 정상이니까 조회하는 순간 예외가 터져야 정상이겠지.
        org.junit.jupiter.api.Assertions.assertThrows(
                NoSuchBeanDefinitionException.class,
                ()->ac.getBean("beanB", BeanB.class)
            );

    }

    @Configuration
    @ComponentScan(
            includeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION, classes = MyIncludeComponent.class),
            excludeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION, classes = MyExcludeComponent.class)
    )
    // 여기서 @ComponentScan.Filter(type= FilterType.ANNOTATION, classes = MyExcludeComponent.class) 보면 FilterType 이라는 게 있음
    // 옵션들이 다섯 개가 있는데 뭐가 있는지 알아보자
    // ANNOTATION => 사실 이게 default 값임. FilterType 파라미터를 안 쓰면 기본값으로 이게 들어감.
    // ASSIGNABLE_TYPE  =>  지정한 타입과 그 자식 타입들을 대상으로 삼음. ex) excludeFilters = { @Filter(type= FilterType.ASSIGNABLE_TYPE, classes = BeanA.class }
    // ASPECTJ  =>  AspectJ 패턴을 사용해서 찾아 옴
    // REGEX    =>  정규 표현식 사용해서 만족하는 애들 찾아옴.
    // CUSTOM   =>  TypeFiler 라는 인터페이스를 사용자가 직접 구현해서 처리할 수도 있음
    static class ComponentFilterAppConfig {


    }
}
