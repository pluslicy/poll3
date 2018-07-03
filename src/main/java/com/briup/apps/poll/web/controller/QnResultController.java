/**
 * Project Name:poll3
 * File Name:QnResultController.java
 * Package Name:com.briup.apps.poll.web.controller
 * Date:2018年7月3日上午8:42:49
 * Copyright (c) 2018, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.briup.apps.poll.web.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.briup.apps.poll.util.ExcelUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * ClassName:问卷结果控制器 <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年7月3日 上午8:42:49 <br/>
 * @author   lichunyu
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@Api(description="问卷结果相关接口")
@RestController
@RequestMapping("result")
public class QnResultController {
	@ApiOperation(value="测试poi",notes="注意！测试的时候请将地址粘贴到浏览器地址栏测试",produces="application/octet-stream")
	@GetMapping("testDownload")
	public void testDownload(HttpServletResponse response) {
		try {
			// 创建新的Excel 工作簿
			XSSFWorkbook workbook = new XSSFWorkbook();
			// 在Excel工作簿中建一工作表，其名为缺省值
			XSSFSheet sheet = workbook.createSheet();
			XSSFCellStyle cellStyle = workbook.createCellStyle(); // 单元格样式
			XSSFFont fontStyle = workbook.createFont(); // 字体样式
			fontStyle.setBold(true); // 加粗
			fontStyle.setFontName("送体"); // 字体
			fontStyle.setFontHeightInPoints((short) 12); // 大小
			// 将字体样式添加到单元格样式中 
			cellStyle.setFont(fontStyle);
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			cellStyle.setBorderBottom(BorderStyle.THIN);; //下边框    
			cellStyle.setBorderLeft(BorderStyle.THIN);//左边框    
			cellStyle.setBorderTop(BorderStyle.THIN);//上边框    
			cellStyle.setBorderRight(BorderStyle.THIN);//右边框
			
			
			// 在索引0的位置创建行（最顶端的行）
			for(int i=0;i<3;i++) {
				XSSFRow row = sheet.createRow(i);
				XSSFCell cell = row.createCell(0);
				cell.setCellValue("hello");
				cell.setCellStyle(cellStyle);
				XSSFCell cell2 = row.createCell(1);
				cell2.setCellValue("world");
				cell2.setCellStyle(cellStyle);
				CellRangeAddress cra = new CellRangeAddress(i, i, 1, 10);
				sheet.addMergedRegion(cra);
				
				RegionUtil.setBorderBottom(BorderStyle.THIN, cra, sheet); // 下边框
				RegionUtil.setBorderLeft(BorderStyle.THIN, cra, sheet); // 左边框
				RegionUtil.setBorderRight(BorderStyle.THIN, cra, sheet); // 有边框
				RegionUtil.setBorderTop(BorderStyle.THIN, cra, sheet); // 上边框
			}
			
			//将excel导出
			OutputStream os = response.getOutputStream();
			 // 告诉浏览器用什么软件可以打开此文件
	        response.setHeader("content-Type", "application/vnd.ms-excel");
	        // 下载文件的默认名称
	        response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("test.xlsx", "utf-8"));
	        
	        workbook.write(os);
			os.flush();
			// 操作结束，关闭文件
			os.close();
			// 关闭workbook
			workbook.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@ApiOperation(value="下载问卷结果",notes="注意！测试的时候请将地址粘贴到浏览器地址栏测试",produces="application/octet-stream")
	@GetMapping("downLoadQnResult")
	public void downLoadQnResult(HttpServletResponse response,long id) {
		try {
			//表头label
			String[] headList = new String[] {"编号","班级","姓名"};
			//表头属性（与数据中的key对应）
			String[] fieldList = new String[] {"id","clazz","name"};
			//表格数据，其中每个map表示一组数据
			Map<String, Object> data = new HashMap<>();
			data.put("id", 1001);
			data.put("clazz", "初中一年级");
			data.put("name", "张三");
			List<Map<String, Object>> dataList = new ArrayList<>();
			dataList.add(data);
			//将excel导出
			OutputStream os = response.getOutputStream();
			 // 告诉浏览器用什么软件可以打开此文件
	        response.setHeader("content-Type", "application/vnd.ms-excel");
	        // 下载文件的默认名称
	        response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("test.xlsx", "utf-8"));
			ExcelUtils.createExcel(os, headList, fieldList, dataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}













