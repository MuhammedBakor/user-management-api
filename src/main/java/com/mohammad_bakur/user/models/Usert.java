package com.mohammad_bakur.user.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "usert", uniqueConstraints = {
        @UniqueConstraint(
        name = "user_email_unique",
        columnNames = "email"
)})
public class Usert {
    @Id
    @SequenceGenerator(
            name = "usert_id_seq",
            sequenceName = "usert_id_seq",
            initialValue = 1,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "usert_id_seq"

    )
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Integer age;

    public Usert(){}

    public Usert(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public Usert(Integer id, String name, String email, Integer age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usert usert = (Usert) o;
        return Objects.equals(id, usert.id) && Objects.equals(name, usert.name) && Objects.equals(email, usert.email) && Objects.equals(age, usert.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, age);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}