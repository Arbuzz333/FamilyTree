package com.avahidov.web.dto;

import java.util.List;


public class FamilyPersonDto {

    private Long passport;

    private String name;

    private String secondName;

    private List<FamilyPersonDto> parent;
    private List<FamilyPersonDto> sisterBrother;
    private List<FamilyPersonDto> children;

    public FamilyPersonDto() {
    }

    public FamilyPersonDto(Long passport, String name, String secondName,
                           List<FamilyPersonDto> parent,
                           List<FamilyPersonDto> sisterBrother,
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

    public List<FamilyPersonDto> getSisterBrother() {
        return sisterBrother;
    }

    public List<FamilyPersonDto> getChildren() {
        return children;
    }
}
