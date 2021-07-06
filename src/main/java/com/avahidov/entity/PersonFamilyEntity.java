package com.avahidov.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "person_family", schema = "public", catalog = "postgres")
public class PersonFamilyEntity {
    private long passport;
    private String name;
    private String secondName;
    private Integer[] parent;
    private Integer[] children;
    private Boolean live;

    @Id
    @Column(name = "passport")
    public long getPassport() {
        return passport;
    }

    public void setPassport(long passport) {
        this.passport = passport;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "second_name")
    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Basic
    @Column(name = "parent", columnDefinition = "int[]")
    @Type(type = "com.avahidov.customtype.CustomIntegerArrayType")
    public Integer[] getParent() {
        return parent;
    }

    public void setParent(Integer[] parent) {
        this.parent = parent;
    }

    @Basic
    @Column(name = "children", columnDefinition = "int[]")
    @Type(type = "com.avahidov.customtype.CustomIntegerArrayType")
    public Integer[] getChildren() {
        return children;
    }

    public void setChildren(Integer[] children) {
        this.children = children;
    }

    @Basic
    @Column(name = "live")
    public Boolean getLive() {
        return live;
    }

    public void setLive(Boolean live) {
        this.live = live;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonFamilyEntity that = (PersonFamilyEntity) o;
        return passport == that.passport && Objects.equals(name, that.name) && Objects.equals(secondName, that.secondName)
                && Objects.equals(live, that.live);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passport);
    }
}
