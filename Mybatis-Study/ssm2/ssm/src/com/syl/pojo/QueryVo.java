package com.syl.pojo;

import java.io.Serializable;

public class QueryVo implements Serializable{
	private String name;
	private String detail;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
