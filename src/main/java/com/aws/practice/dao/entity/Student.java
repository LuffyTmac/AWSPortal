package com.aws.practice.dao.entity;

import javax.persistence.*;

public class Student {
    /**
     * 学生表主键学号
     */
    @Id
    @Column(name = "Sno")
    private Integer sno;

    /**
     * 学生姓名
     */
    @Column(name = "Sname")
    private String sname;

    /**
     * 性别
     */
    @Column(name = "Ssex")
    private String ssex;

    /**
     * 年龄
     */
    @Column(name = "Sage")
    private Byte sage;

    /**
     * 专业
     */
    @Column(name = "Sdept")
    private String sdept;

    /**
     * 获取学生表主键学号
     *
     * @return Sno - 学生表主键学号
     */
    public Integer getSno() {
        return sno;
    }

    /**
     * 设置学生表主键学号
     *
     * @param sno 学生表主键学号
     */
    public void setSno(Integer sno) {
        this.sno = sno;
    }

    /**
     * 获取学生姓名
     *
     * @return Sname - 学生姓名
     */
    public String getSname() {
        return sname;
    }

    /**
     * 设置学生姓名
     *
     * @param sname 学生姓名
     */
    public void setSname(String sname) {
        this.sname = sname;
    }

    /**
     * 获取性别
     *
     * @return Ssex - 性别
     */
    public String getSsex() {
        return ssex;
    }

    /**
     * 设置性别
     *
     * @param ssex 性别
     */
    public void setSsex(String ssex) {
        this.ssex = ssex;
    }

    /**
     * 获取年龄
     *
     * @return Sage - 年龄
     */
    public Byte getSage() {
        return sage;
    }

    /**
     * 设置年龄
     *
     * @param sage 年龄
     */
    public void setSage(Byte sage) {
        this.sage = sage;
    }

    /**
     * 获取专业
     *
     * @return Sdept - 专业
     */
    public String getSdept() {
        return sdept;
    }

    /**
     * 设置专业
     *
     * @param sdept 专业
     */
    public void setSdept(String sdept) {
        this.sdept = sdept;
    }
}