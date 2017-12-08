package cn.syl.domain;


import java.util.HashSet;
import java.util.Set;

public class User extends BaseEntity {
//    create table USER_P  (
//            USER_ID              VARCHAR2(40)                    not null,
//    DEPT_ID              VARCHAR2(40),
//    USER_NAME            VARCHAR2(50),
//    PASSWORD             VARCHAR2(64),
//    STATE                INT,
//    CREATE_BY            VARCHAR2(40),
//    CREATE_DEPT          VARCHAR2(40),
//    CREATE_TIME          TIMESTAMP,
//    UPDATE_BY            VARCHAR2(40),
//    UPDATE_TIME          TIMESTAMP,
//    constraint PK_USER_P primary key (USER_ID)
//);


    // private String  deptId    ;
    private String id;

    private Dept dept;
    private Set<Role> roles = new HashSet<>();//用户与角色多对多.


    private String userName;
    private String password;
    private Integer state;

    private UserInfo userInfo; //一个用户对一个用户信息

    // private String  createBy  ;//创建者id
// private String  createDept;//创建者所在部门id
// private Date    createTime;
// private String  updateBy  ;//更新者id
// private Date    updateTime;//更新时间

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }


}
