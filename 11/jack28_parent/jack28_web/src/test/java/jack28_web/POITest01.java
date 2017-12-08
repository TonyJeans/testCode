package jack28_web;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class POITest01 {

    @Test
    public void testPOI() throws IOException {
        //1.创建工作簿
        Workbook wb = new HSSFWorkbook();
        //2.创建工作表
        Sheet sheet = wb.createSheet();

        //3.创建行对象Row
        Row row = sheet.createRow(3);//0开始

        //4.创建单元格对象
        Cell cell = row.createCell(3);//0

        //5.设置单元格内容
        cell.setCellValue("孙宇龙");
        //6.设置单元格样式
        CellStyle cellStyle = wb.createCellStyle();
        //字体
        Font font = wb.createFont();
        font.setFontName("方正舒体");
        font.setFontHeightInPoints((short) 48);

        cellStyle.setFont(font);//添加字体样式


        cell.setCellStyle(cellStyle);

        //
        OutputStream os = new FileOutputStream("d://a.xls");
        wb.write(os);
        os.close();
        //下载
    }


}
