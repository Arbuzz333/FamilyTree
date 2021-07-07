package com.avahidov.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FamilyPersonDto implements Comparable<FamilyPersonDto> {

    @NotNull
    private Long passport;

    @NotNull
    private String name;

    @NotNull
    private String secondName;

    private Boolean live;

    private List<FamilyPersonDto> parent = new ArrayList<>();
    private Set<FamilyPersonDto> sisterBrother = new TreeSet<>();
    private List<FamilyPersonDto> children = new ArrayList<>();

    public FamilyPersonDto() {
    }

    public FamilyPersonDto(Long passport, String name, String secondName,
                           List<FamilyPersonDto> parent,
                           Set<FamilyPersonDto> sisterBrother,
                           List<FamilyPersonDto> children) {
        this.passport = passport;
        this.name = name;
        this.secondName = secondName;
        this.parent = parent;
        this.sisterBrother = sisterBrother;
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public Long getPassport() {
        return passport;
    }

    public List<FamilyPersonDto> getParent() {
        return parent;
    }

    public void setParent(List<FamilyPersonDto> parent) {
        this.parent = parent;
    }

    public Set<FamilyPersonDto> getSisterBrother() {
        return sisterBrother;
    }

    public List<FamilyPersonDto> getChildren() {
        return children;
    }

    public void setSisterBrother(Set<FamilyPersonDto> sisterBrother) {
        this.sisterBrother = sisterBrother;
    }

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
        FamilyPersonDto dto = (FamilyPersonDto) o;
        return passport.equals(dto.passport) && name.equals(dto.name) && secondName.equals(dto.secondName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passport, name, secondName);
    }

    @Override
    public int compareTo(FamilyPersonDto dto) {
        return this.passport.compareTo(dto.passport);
    }
}
