package halo.halospring.domain;


import jdk.jfr.Enabled;

import javax.persistence.*;

@Entity
public class Member {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 고객이 정하는 값이아닌 시스템이 정하는 값


//    @Column(name = "username")
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
