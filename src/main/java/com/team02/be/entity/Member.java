///도메인 설정
//Member.java Setting
package com.team02.be.entity;

import jakarta.persistence.*;

//회원 Entity
//
//Entity는 DB 테이블과 연결되는 클래스
//이 클래스는 MySQL의 members 테이블과 매핑
//
//즉, Java의 Member 객체 하나가 DB의 members 테이블 row 하나와 연결된다고 보면 됨
@Entity
@Table(name = "members")
public class Member {

    //회원 고유 ID
    //
    //@Id: 이 필드가 테이블의 Primary Key라는 뜻
    //
    //@GeneratedValue(strategy = GenerationType.IDENTITY): MySQL의 AUTO_INCREMENT 방식으로 id 값을 자동 증가시킴
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //회원 이메일
    //
    //@Column: 이 필드를 DB 테이블의 컬럼과 연결함
    //
    //nullable = false: DB에 null로 저장될 수 없음
    //
    //unique = true: 같은 이메일을 중복 저장할 수 없음
    //
    //length = 100: 최대 길이를 100자로 제한
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    //회원 비밀번호
    //
    //nullable = false: 비밀번호는 반드시 저장되어야 함
    //
    //length = 255: 나중에 BCrypt로 암호화하면 문자열이 길어질 수 있으므로 넉넉하게 255자로 잡음
    //
    //실제 프로젝트에서는 반드시 BCrypt로 암호화해서 저장해야 함
    @Column(nullable = false, length = 255)
    private String password;

    // 회원 이름
    //
    //nullable = false: 이름은 반드시 저장되어야 함
    //
    // length = 50: 최대 길이를 50자로 제한
    @Column(nullable = false, length = 50)
    private String name;

    //JPA 기본 생성자
    //
    //JPA는 Entity 객체를 만들 때 기본 생성자가 필요함
    //
    //protected로 둔 이유: 외부에서 아무 값도 없는 Member 객체를 막 생성하지 못하게 하기 위해서
    //
    //개발자가 직접 사용할 생성자는 아래의 public Member(String email, String password, String name) 생성자임
    protected Member() {
    }

    //회원 생성자
    //
    //회원가입 요청을 받았을 때 email, password, name 값을 넣어서 Member 객체를 만듦
    //
    // id는 직접 넣지 않음
    // 이유 -> id는 MySQL이 AUTO_INCREMENT로 자동 생성하기 때문임
    public Member(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    //회원 ID 반환
    //
    //Service나 DTO 변환 과정에서 member.getId() 형태로 사용함
    public Long getId() {
        return id;
    }

    //회원 이메일 반환
    //
    //Service나 DTO 변환 과정에서 member.getEmail() 형태로 사용함
    public String getEmail() {
        return email;
    }

    //회원 비밀번호 반환
    //
    //실제 API 응답에는 비밀번호를 넣지 않음
    //
    //지금 getter가 있는 이유: 내부 로직에서 필요할 수 있기 때문임
    //
    //단, MemberResponse에는 password를 절대 넣지 않음
    public String getPassword() {
        return password;
    }

    // 회원 이름 반환
    //
    //Service나 DTO 변환 과정에서 member.getName() 형태로 사용함
    public String getName() {
        return name;
    }
}