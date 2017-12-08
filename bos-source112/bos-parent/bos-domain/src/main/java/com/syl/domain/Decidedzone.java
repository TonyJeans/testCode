package com.syl.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 定区
 */

public class Decidedzone implements java.io.Serializable {

	// Fields

	private String id;
	//定区有一个取派员(设置立即加载取派员,查询定区的时候把关联的取派员查询出来)
	//在加载多的一方立即加载一的一方,性能影响不是很大.(取派员一的一方)
	private Staff staff;
	private String name;
	//分区
	private Set subareas = new HashSet(0);

	// Constructors

	/** default constructor */
	public Decidedzone() {
	}

	/** minimal constructor */
	public Decidedzone(String id) {
		this.id = id;
	}

	/** full constructor */
	public Decidedzone(String id, Staff staff, String name, Set subareas) {
		this.id = id;
		this.staff = staff;
		this.name = name;
		this.subareas = subareas;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
/**
 * 获取定区对应的取派员
 * @return
 */
	public Staff getStaff() {
		return this.staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getSubareas() {
		return this.subareas;
	}

	public void setSubareas(Set subareas) {
		this.subareas = subareas;
	}

}