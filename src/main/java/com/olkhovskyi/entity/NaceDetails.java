package com.olkhovskyi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "nace_details")
public class NaceDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int orderNumber;
    private int level;
    private String code;
    private String parent;
    @Column(length = 10000)
    private String description;
    @Column(length = 10000)
    private String thisItemIncludes;
    @Column(length = 10000)
    private String thisItemAlsoIncludes;
    @Column(length = 10000)
    private String rulings;
    @Column(length = 10000)
    private String thisItemExcludes;
    @Column(length = 10000)
    private String referenceToISICRev4;

    @Override
    public String toString() {
        return "NaceDetails{" +
                "order=" + orderNumber +
                ", level=" + level +
                ", code='" + code + '\'' +
                ", parent='" + parent + '\'' +
                ", description='" + description + '\'' +
                ", thisItemIncludes='" + thisItemIncludes + '\'' +
                ", thisItemAlsoIncludes='" + thisItemAlsoIncludes + '\'' +
                ", rulings='" + rulings + '\'' +
                ", thisItemExcludes='" + thisItemExcludes + '\'' +
                ", referenceToISICRev4='" + referenceToISICRev4 + '\'' +
                '}';
    }

    public NaceDetails() {}

    public NaceDetails(int orderNumber, int level, String code, String parent, String description, String thisItemIncludes, String thisItemAlsoIncludes, String rulings, String thisItemExcludes, String referenceToISICRev4) {
        this.orderNumber = orderNumber;
        this.level = level;
        this.code = code;
        this.parent = parent;
        this.description = description;
        this.thisItemIncludes = thisItemIncludes;
        this.thisItemAlsoIncludes = thisItemAlsoIncludes;
        this.rulings = rulings;
        this.thisItemExcludes = thisItemExcludes;
        this.referenceToISICRev4 = referenceToISICRev4;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThisItemIncludes() {
        return thisItemIncludes;
    }

    public void setThisItemIncludes(String thisItemIncludes) {
        this.thisItemIncludes = thisItemIncludes;
    }

    public String getThisItemAlsoIncludes() {
        return thisItemAlsoIncludes;
    }

    public void setThisItemAlsoIncludes(String thisItemAlsoIncludes) {
        this.thisItemAlsoIncludes = thisItemAlsoIncludes;
    }

    public String getRulings() {
        return rulings;
    }

    public void setRulings(String rulings) {
        this.rulings = rulings;
    }

    public String getThisItemExcludes() {
        return thisItemExcludes;
    }

    public void setThisItemExcludes(String thisItemExcludes) {
        this.thisItemExcludes = thisItemExcludes;
    }

    public String getReferenceToISICRev4() {
        return referenceToISICRev4;
    }

    public void setReferenceToISICRev4(String referenceToISICRev4) {
        this.referenceToISICRev4 = referenceToISICRev4;
    }
}