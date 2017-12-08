package com.syl.bos.utils;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public class PageBean {
	/**
	 * 当前页码
	 */
	private int currentPage;
	/**
	 * 每页显示的记录数
	 */
	private int pageSize; 
	/**
	 * 总记录书
	 */
	private int total;

	/**
	 * 当前页面展示的数据集合
	 */
	private List rows;
	
	private DetachedCriteria detachedCriteria;

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}
	/**
	 * 当前页面展示的数据集合
	 */
	public void setRows(List rows) {
		this.rows = rows;
	}

	public DetachedCriteria getDetachedCriteria() {
		return detachedCriteria;
	}

	public void setDetachedCriteria(DetachedCriteria detachedCriteria) {
		this.detachedCriteria = detachedCriteria;
	}
	
	
}
