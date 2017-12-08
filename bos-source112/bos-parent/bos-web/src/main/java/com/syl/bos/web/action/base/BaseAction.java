package com.syl.bos.web.action.base;



import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.syl.bos.utils.PageBean;
import com.syl.domain.Region;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	protected PageBean pageBean = new PageBean();
	
	DetachedCriteria detachedCriteria = null;
	
	public static final String HOME = "home";
	public static final String LIST = "list";

	//easyUI参数:第几页
	//protected int page;
	// 每页显示几个
	//protected int rows;

	public void setPage(int page) {
		pageBean.setCurrentPage(page);
		//this.page = page;
	}

	public void setRows(int rows) {
		pageBean.setPageSize(rows);
		//this.rows = rows;
	}

	// 模型对象new User()
	protected T model;

	@Override
	public T getModel() {
		return model;
	}
	
	/**
	 * 指定对象转换为Json相应客户页面
	 * @param o
	 * @param exclueds
	 */
	public void jave2Json(Object o ,String[] exclueds) {
		//new String[]{"currentPage","detachedCriteria","pageSize"}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(exclueds);
		String json = JSONObject.fromObject(o,jsonConfig).toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(json);
	}
	/**
	 * 
	 * @param list
	 * @param exclueds
	 */
	public void jave2Json(List o ,String[] exclueds) {
		//new String[]{"currentPage","detachedCriteria","pageSize"}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(exclueds);
		String json = JSONArray.fromObject(o,jsonConfig).toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(json);
	}

	public BaseAction() {
		ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
		// 获取被继承时BaseActin泛型数组
		Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
		Class<T> entityClass = (Class<T>) actualTypeArguments[0];
		
		detachedCriteria = DetachedCriteria.forClass(entityClass);
		pageBean.setDetachedCriteria(detachedCriteria);
		
		// 通过泛型创建对象
		try {
			model = entityClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		System.out.println("BaseAction"+this);
	}

}
