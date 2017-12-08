package cn.syl.domain;


import java.util.Date;

public class UserInfo extends BaseEntity {

private String    id;
private User      manager  ;  //直属领导 多个用户信息 是同一个领导      private String    managerId  ;
private String    name        ;
private Date      joinDate   ;    //入职时间
private Double    salary      ;   //薪水
private Date    birthday    ;      //private String    birthday    ;    java.sql.SQLException: ORA-01843: 无效的月份
private String    gender      ;   //性别
private String    station     ;    //岗位
private String    telephone   ;    //电话
private Integer   degree      ;     //等级
private String    remark      ;     //说明信息
private String    orderNo    ;   //排序id
private String    email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
//private String    createBy   ;
//private String    createDept ;
//private String    createTime ;
//private String    updateBy   ;
//private String    updateTime ;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
