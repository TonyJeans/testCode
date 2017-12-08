package com.syl.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.syl.bos.service.IRegionService;
import com.syl.bos.utils.PageBean;
import com.syl.bos.utils.PinYin4jUtils;
import com.syl.bos.web.action.base.BaseAction;
import com.syl.domain.Region;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller(value = "regionAction1")
//@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region> {
	// importXls
	private File regionFile;

	@Autowired
	private IRegionService regionService;

	public void setRegionFile(File regionFile) {
		this.regionFile = regionFile;
	}

	public String importXls() throws FileNotFoundException, IOException {
		// 使用POI解析Excel
		List<Region> regionList = new ArrayList<>();
		System.out.println(regionFile);
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(regionFile));
		HSSFSheet sheet = workbook.getSheetAt(0);
		// 遍历所有的行
		for (Row row : sheet) {
			int rowNum = row.getRowNum();// 行号
			if (rowNum == 0) {
				continue;
			}
			String id = row.getCell(0).getStringCellValue();
			String province = row.getCell(1).getStringCellValue();
			String city = row.getCell(2).getStringCellValue();
			String district = row.getCell(3).getStringCellValue();
			String postcode = row.getCell(4).getStringCellValue();
			// 包装区域对象
			Region region = new Region(id, province, city, district, postcode, null, null, null);
			// 转化拼音,简码
			province = province.substring(0, province.length() - 1);
			city = city.substring(0, city.length() - 1);
			district = district.substring(0, district.length() - 1);
			String info = province + city + district;
			String[] headByString = PinYin4jUtils.getHeadByString(info);
			String shortcode = StringUtils.join(headByString);
			// 城市编码 -->shijiazhuang
			String cityCode = PinYin4jUtils.hanziToPinyin(city, "");
			region.setShortcode(shortcode);
			region.setCitycode(cityCode);

			regionList.add(region);
		}
		// 批量保存
		regionService.saveBatch(regionList);
		return NONE;
	}



	// 分页查询
	public String pageQuery() {
		//PageBean pageBean = new PageBean();
		//pageBean.setCurrentPage(page);
	//	pageBean.setPageSize(rows);
		//DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Region.class);
		//pageBean.setDetachedCriteria(detachedCriteria);
		regionService.pageQuery(pageBean);
		this.jave2Json(pageBean, new String[]{"currentPage","detachedCriteria","pageSize","subareas"});//避免死循环
		System.out.println("regionAction");
		return NONE;
	}

	private String q;
	
	public void setQ(String q) {
		this.q = q;
	}
	/**
	 * 查询所有区域,写回json数据
	 * @return
	 */ 
	public String listajax() {
		List<Region> list =null;
		if (StringUtils.isNotBlank(q)) {
			list = regionService.findListByQ(q);
		}else{
			list = regionService.findAll();
		}
				
		this.jave2Json(list, new String[]{"subareas"}); 
		return NONE;
	}

}
