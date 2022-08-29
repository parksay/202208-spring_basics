package hello.core.member;

import java.util.HashMap;
import java.util.Map;

public class MemoryMemberRepository implements MemberRepository {

    // MemberRepository 인터페이스랑 그 구현체 MemoryMemberRepository랑 같은 패키지에 만들어 놓았지.
    // 지금은 프로젝트 구조를 복잡하게 만들지 않으려고 일부러 이렇게 해 두었음.
    // 하지만 보통은 인터페이스랑 구현체를 다른 패키지에 두는 구조가 더 좋은 설계라고 함.

    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {

        return store.get(memberId) ;
    }

    // 지금 store 변수 자체가 DB역할을 하고 있는 거.
    // store.put 이 말하자면 insert into 인 거고 store.get이 말하자면 select 문인 것.

}
