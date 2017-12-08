package com.syl.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.syl.bos.service.ISubareaService;
import com.syl.bos.utils.FileUtils;
import com.syl.bos.web.action.base.BaseAction;
import com.syl.domain.Region;
import com.syl.domain.Subarea;

@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea> {

	@Resource
	private ISubareaService subareaService;

	/**
	 * 添加分区
	 */
	public String add() {
		subareaService.save(model);
		return LIST;
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 */
	public String pageQuery() {
		// 动态添加过滤条件
		// BaseAction:detachedCriteria = DetachedCriteria.forClass(entityClass);
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		String addresskey = model.getAddresskey();
		if (StringUtils.isNoneBlank(addresskey)) {
			// 添加过滤条件
			dc.add(Restrictions.like("addresskey", "%" + addresskey + "%"));
		}

		Region region = model.getRegion();
		if (region != null) {
			String province = region.getProvince();
			String city = region.getCity();
			String district = region.getDistrict();
			// select * from bc_subarea s JOIN bc_region r ON s.region_id = r.id
			// where r.province like '%北京%'
			// 多表关联查询 para1:关联对象属性名称,param2:别名
			dc.createAlias("region", "r");
			if (StringUtils.isNotBlank(province)) {
				dc.add(Restrictions.like("r.province", "%" + province + "%"));
			}
			if (StringUtils.isNotBlank(city)) {
				dc.add(Restrictions.like("r.city", "%" + city + "%"));
			}
			if (StringUtils.isNotBlank(district)) {
				dc.add(Restrictions.like("r.district", "%" + district + "%"));
			}
		}
		subareaService.pageQuery(pageBean);
		this.jave2Json(pageBean,
				new String[] { "currentPage", "detachedCriteria", "pageSize", "decidedzone", "subareas" });
		return NONE;
	}

	// 导出数据
	public String exportXls() throws Exception {
		List<Subarea> list = subareaService.findAll();

		// 使用poi写入
		// 内存创建excel
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个标签页
		HSSFSheet sheet = workbook.createSheet("分区数据");
		// 创建标题行
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("分区编号");
		headRow.createCell(1).setCellValue("开始编号");
		headRow.createCell(2).setCellValue("结束编号");
		headRow.createCell(3).setCellValue("位置信息");
		headRow.createCell(4).setCellValue("省市区");

		HSSFRow dataRow = null;
		for (Subarea subarea : list) {
			dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getStartnum());
			dataRow.createCell(2).setCellValue(subarea.getEndnum());
			dataRow.createCell(3).setCellValue(subarea.getPosition());
			dataRow.createCell(4).setCellValue(subarea.getRegion().getName());
		}
		String filename = "分区数据.xls";
		String mimeType = ServletActionContext.getServletContext().getMimeType(filename);

		// 输出流进行文件的下载(一个流,两个头)
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
		ServletActionContext.getResponse().setContentType(mimeType);
		//ServletActionContext.getResponse().setContentType("application/vnd.ms-excel");
		filename = FileUtils.encodeDownloadFilename(filename, ServletActionContext.getRequest().getHeader("User-Agent"));
		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename="+filename);
		//ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename=subarea.xls");

		workbook.write(out);
		return NONE;
	}
	/**
	 * 查询所有为关联到定区的分区,json 
	 * @return
	 */
	public String listajax(){
		List<Subarea> list = subareaService.findListNotAssociation();
		this.jave2Json(list, new String[]{"decidedzone","region"});
		return NONE;
	}
	//属性驱动
	private String decidedzoneId;
	
	public void setDecidedzoneId(String decidedzoneId) {
		this.decidedzoneId = decidedzoneId;
	}
	/**
	 * 更具定区id查询关联的分区
	 * @return
	 */
	public String findListByDecidedzoneId() {
		//decidedzoneId
		List<Subarea> list = subareaService.findListByDecidedzoneId(decidedzoneId);
		this.jave2Json(list, new String[]{"decidedzone","subareas"});
		return NONE;
	}
	/**
	 * 查询区域分区数据
	 * @return
	 */
	public String findSubareaGroupByProvince() {
		List<Object> list = subareaService.findSubareaGroupByProvince();
		this.jave2Json(list, new String[]{});
		return NONE;
	}


}
