package cn.syl.domain;


import oracle.sql.TIMESTAMP;

import java.util.HashSet;
import java.util.Set;

public class Role extends BaseEntity {
    private Set<User> users = new HashSet<>();//角色与用户多对多
    private Set<Module> modules = new HashSet<>();//角色与模块多对多
    private String id;
    private String name;   //角色
    private String remark;   //备注
    private String orderNo;    //排序号

    public Set<Module> getModules() {
        return modules;
    }

    public void setModules(Set<Module> modules) {
        this.modules = modules;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
