package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberRepositoryTest {
    MemberRepository store=MemberRepository.getInstance();

    @BeforeEach
    void beforeEach(){
        store.clearStore();
    }

    @Test
    void save(){
        Member member =new Member("hello",20);

        //when
        Member savedMember = store.save(member);
        Member findMember= store.findById(member.getId());

        assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    void saveAll(){
        Member member1 =new Member("member1",20);
        Member member2 =new Member("member2",30);
        store.save(member1);
        store.save(member2);

        ArrayList<Member> all = store.findAll();
        assertThat(2).isEqualTo(all.size());
        assertThat(all).contains(member1,member2);
    }
}
