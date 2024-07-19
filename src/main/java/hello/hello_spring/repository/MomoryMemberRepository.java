package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;

import java.util.*;

public class MomoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        //람다 표현식을 사용해서 store에서 이름이 일치하는 Member 객체를 찾아 Optional로 감싸서 반환한다.
        return store.values().stream()
                .filter(member -> member.getName().equals(name)) //member의 이름이 name과 같은지 비교
                .findAny();


    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
}
