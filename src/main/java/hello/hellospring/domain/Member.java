package hello.hellospring.domain;

import javax.persistence.*;

//JPA가 관리하는 Entity(=Member)
@Entity
public class Member {
    //pk는 id, id자동으로 생성해주는 것을 Identity 전략이라고 함(Oracle에서는 sequence)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//임의의 값, 시스템이 정하는 id(data 구분 위함)

    //DB에 있는 username 컬럼명과 mapping
    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
