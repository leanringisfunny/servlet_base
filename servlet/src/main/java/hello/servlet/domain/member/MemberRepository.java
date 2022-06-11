package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MemberRepository {
    private static Map<Long,Member> store = new HashMap();
    private static Long sequence = 0L;

    private static final MemberRepository instance= new MemberRepository();

    public static MemberRepository getInstance(){
        return instance;
    }
    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(), member);
        return store.get(member.getId());
    }

    public Member findById(Long id){
        return  store.get(id);
    }
    public ArrayList<Member> findAll(){
        return new ArrayList<Member>(store.values());
    }
    public void clearStore(){
        store.clear();
    }



    private MemberRepository(){

    }


}
