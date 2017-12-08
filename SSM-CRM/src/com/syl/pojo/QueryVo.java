package com.syl.pojo;

public class QueryVo {

	private String custName;
	private String custSource;
	private String custIndustry;
	private String custLevel;
	/**
	 * 当前页
	 */
	private Integer page = 1;

	/**
	 *
	 * //查询开始条数  limit star ,end=10(已知)
	 */
	private Integer start;
	/**
	 * 每页显示条数
	 *
	 */
	private Integer size = 10;


	public Integer getStart() {
		return start;
	}

	/**
	 * //查询开始条数  limit star ,end=10(已知)
	 * @param start
	 */
	public void setStart(Integer start) {
		this.start = start;
	}

	/**
	 * 当前页
	 * @return
	 */
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * 每页显示条数
	 * @return
	 */
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustSource() {
		return custSource;
	}
	public void setCustSource(String custSource) {
		this.custSource = custSource;
	}
	public String getCustLevel() {
		return custLevel;
	}
	public void setCustLevel(String custLevel) {
		this.custLevel = custLevel;
	}
	public String getCustIndustry() {
		return custIndustry;
	}
	public void setCustIndustry(String custIndustry) {
		this.custIndustry = custIndustry;
	}
	
	
}
