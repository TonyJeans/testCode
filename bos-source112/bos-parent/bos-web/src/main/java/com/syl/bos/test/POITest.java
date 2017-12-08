package com.syl.bos.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

import com.opensymphony.xwork2.util.ResolverUtil.NameEndsWith;

public class POITest {

	//poi解析excel
	@Test
	public void test1() throws FileNotFoundException, IOException {
		String path = "E:\\Learn\\heima32\\项目一：物流BOS系统（58-71天）\\BOS-day05\\BOS-day05\\资料\\区域导入测试数据.xls";
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File(path)));
		//读取文件中sheet1
		HSSFSheet sheetAt = workbook.getSheetAt(0);
		//遍历所有的行
		for (Row row : sheetAt) {
			int rowNum = row.getRowNum();//行号
			if (rowNum==0) {
				continue;
			}
			for (Cell cell : row) {
				String value = cell.getStringCellValue();
				System.out.print(value+" ");
			}
			System.out.println();
		}
	}
}
