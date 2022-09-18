package hello.core.scan.filter;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
// 요게 중요함. type 이면 class 레벨에 붙는다는 뜻
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {
}
