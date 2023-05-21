package project.service;

import project.repository.MemberRepository;
import project.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService{
    
    private final MemberRepository memberRepository;
    
    // @Autowired
    // public MemberService(MemberRepository memberRepository){
    //     this.memberRepository = memberRepository;
    // }
    
    //회원 가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }
    
    private void validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }
    
    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    
    //회원 단건 조회
    public Member findOne(Long id){
        return memberRepository.findOne(id);
    }
    
    //회원 정보 수정
    @Transactional
    public Long updateMember(Long id, String name){
        Member member = memberRepository.findOne(id);
        member.setName(name);
        
        return id;
    }
}