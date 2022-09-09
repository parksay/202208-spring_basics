package hello.core.xmlConfig;

import hello.coreSpring.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class XmlAppConfig {

    @Test
    void xmlAppContext() {

        ApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);

        // ApplicationContext 는 인터페이스임. 구현체는 다양함. 유연함.
        // 어떤 방식으로 config 를 등록할지, 스프링 어노테이션도 되고, xml 파일도 되고, 또 다른 내가 직접 만든 형식이어도 되고
        // 그만큼 spring 이 얼마나 유연하게 만들어졌는지
    }
}
