package cn.syl.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Dept implements Serializable {
	// create table DEPT_P (
	// DEPT_ID varchar2(40) not null,
	// DEPT_NAME varchar2(50),
	// PARENT_ID varchar2(40),
	// STATE NUMBER(6,0),
	// constraint PK_DEPT_P primary key (DEPT_ID)
	// );
	//alter table DEPT_P
 // add foreign key (PARENT_ID)
 // references DEPT_P (DEPT_ID) on delete cascade;

	private String id;
	//// Hibernate框架默认的集合是set集合，集合必须要自己手动的初始化
	private Set<User> users = new HashSet<User>();
	private String deptName;
	private Dept parent;// 自关联 多个子部门 对同个父亲 多对一
	private Integer state; //1启用 0停用

	public String getId() {
		return id;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Dept getParent() {
		return parent;
	}
	public void setParent(Dept parent) {
		this.parent = parent;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	

}
