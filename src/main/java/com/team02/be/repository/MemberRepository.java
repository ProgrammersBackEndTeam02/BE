//MemberRepository.java Setting
package com.team02.be.repository;

import com.team02.be.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//회원 Repository
//
//Repository는 DB에 접근하는 계층
//
//Controller가 직접 DB에 접근하지 않고, Service도 직접 SQL을 작성하지 않음
//
//대신 Repository가 MySQL과 연결되어 저장, 조회, 삭제 같은 DB 작업을 담당함
public interface MemberRepository extends JpaRepository<Member, Long> {

    // JpaRepository<Member, Long> 의미
    //
    //Member: 이 Repository가 관리할 Entity 클래스
    //
    //Long:  Member Entity의 Primary Key 타입
    //
    //즉, Member 클래스의 id 타입이 Long이므로 JpaRepository<Member, Long>이라고 작성
    //
    //이것만 상속해도 기본 CRUD 메서드를 사용할 수 있음
    //
    // 예:
    // save(member)      → 회원 저장
    // findById(id)      → id로 회원 조회
    // findAll()         → 전체 회원 조회
    // delete(member)    → 회원 삭제

    //이메일로 회원을 조회하는 메서드
    //
    // Spring Data JPA가 메서드 이름을 보고 자동으로 SQL 쿼리를 만들어 줌
    //
    // findByEmail(String email) → email 컬럼 값이 전달받은 email과 같은 회원을 찾음
    //
    // Optional<Member>: 조회 결과가 있을 수도 있고 없을 수도 있다는 뜻
    //
    //예:
    //test@test.com 회원이 있으면 Optional 안에 Member가 들어있다.
    //없으면 Optional.empty()가 됨
    Optional<Member> findByEmail(String email);

    //이메일 중복 여부 확인
    //
    //existsByEmail(String email) → 해당 email을 가진 회원이 DB에 존재하는지 확인함
    //
    //결과:
    //존재하면 true
    //존재하지 않으면 false
    //
    //회원가입할 때 같은 이메일로 중복 가입하는 것을 막기 위해 사용함
    boolean existsByEmail(String email);
}