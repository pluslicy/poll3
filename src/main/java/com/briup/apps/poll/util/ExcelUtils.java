/**
 * Project Name:poll3
 * File Name:ExcelUtils.java
 * Package Name:com.briup.apps.poll.util
 * Date:2018年7月3日上午8:57:12
 * Copyright (c) 2018, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.briup.apps.poll.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * ClassName:ExcelUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年7月3日 上午8:57:12 <br/>
 * 
 * @author lichunyu
 * @version
 * @since JDK 1.6
 * @see
 */
@SuppressWarnings("deprecation")
public class ExcelUtils {
	/**
	 * 要求excel版本在2007以上
	 *
	 * @param file
	 *            文件信息
	 * @return
	 * @throws Exception
	 */
	public static List<List<Object>> readExcel(File file) throws Exception {
		if (!file.exists()) {
			throw new Exception("找不到文件");
		}
		List<List<Object>> list = new LinkedList<List<Object>>();
		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
		// 读取第一张表格内容
		XSSFSheet sheet = xwb.getSheetAt(0);
		XSSFRow row = null;
		XSSFCell cell = null;
		for (int i = (sheet.getFirstRowNum() + 1); i <= (sheet.getPhysicalNumberOfRows() - 1); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			List<Object> linked = new LinkedList<Object>();
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
				Object value = null;
				cell = row.getCell(j);
				if (cell == null) {
					continue;
				}
				switch (cell.getCellType()) {
				case XSSFCell.CELL_TYPE_STRING:
					// String类型返回String数据
					value = cell.getStringCellValue();
					break;
				case XSSFCell.CELL_TYPE_NUMERIC:
					// 日期数据返回LONG类型的时间戳
					if ("yyyy\"年\"m\"月\"d\"日\";@".equals(cell.getCellStyle().getDataFormatString())) {
						// System.out.println(cell.getNumericCellValue()+":日期格式："+cell.getCellStyle().getDataFormatString());
						// value =
						// DateUtils.getMillis(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())) /
						// 1000;
						value = cell.getNumericCellValue();
					} else {
						// 数值类型返回double类型的数字
						// System.out.println(cell.getNumericCellValue()+":格式："+cell.getCellStyle().getDataFormatString());
						value = cell.getNumericCellValue();
					}
					break;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					// 布尔类型
					value = cell.getBooleanCellValue();
					break;
				case XSSFCell.CELL_TYPE_BLANK:
					// 空单元格
					break;
				default:
					value = cell.toString();
				}
				if (value != null && !value.equals("")) {
					// 单元格不为空，则加入列表
					linked.add(value);
				}
			}
			if (linked.size() != 0) {
				list.add(linked);
			}
		}
		// 关闭workbook
		xwb.close();
		return list;
	}

	/**
	 * 要求excel版本在2007以上
	 *
	 * @param fileInputStream
	 *            文件信息
	 * @return
	 * @throws Exception
	 */
	public static List<List<Object>> readExcel(FileInputStream fileInputStream) throws Exception {
		List<List<Object>> list = new LinkedList<List<Object>>();
		XSSFWorkbook xwb = new XSSFWorkbook(fileInputStream);
		// 读取第一张表格内容
		XSSFSheet sheet = xwb.getSheetAt(1);
		XSSFRow row = null;
		XSSFCell cell = null;
		for (int i = (sheet.getFirstRowNum() + 1); i <= (sheet.getPhysicalNumberOfRows() - 1); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			List<Object> linked = new LinkedList<Object>();
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
				Object value = null;
				cell = row.getCell(j);
				if (cell == null) {
					continue;
				}
				switch (cell.getCellType()) {
				case XSSFCell.CELL_TYPE_STRING:
					value = cell.getStringCellValue();
					break;
				case XSSFCell.CELL_TYPE_NUMERIC:
					if ("yyyy\"年\"m\"月\"d\"日\";@".equals(cell.getCellStyle().getDataFormatString())) {
						// System.out.println(cell.getNumericCellValue()+":日期格式："+cell.getCellStyle().getDataFormatString());

						// value =
						// DateUtils.getMillis(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())) /
						// 1000;
						value = cell.getNumericCellValue();
					} else {
						// System.out.println(cell.getNumericCellValue()+":格式："+cell.getCellStyle().getDataFormatString());
						value = cell.getNumericCellValue();
					}
					break;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					value = cell.getBooleanCellValue();
					break;
				case XSSFCell.CELL_TYPE_BLANK:
					break;
				default:
					value = cell.toString();
				}
				if (value != null && !value.equals("")) {
					// 单元格不为空，则加入列表
					linked.add(value);
				}
			}
			if (linked.size() != 0) {
				list.add(linked);
			}
		}
		// 关闭workbook
		xwb.close();
		return list;
	}

	/**
	 * 导出excel
	 * 
	 * @param excel_name
	 *            导出的excel路径（需要带.xlsx)
	 * @param headList
	 *            excel的标题备注名称
	 * @param fieldList
	 *            excel的标题字段（与数据中map中键值对应）
	 * @param dataList
	 *            excel数据
	 * @throws Exception
	 */
	public static void createExcel(OutputStream os, String[] headList, String[] fieldList,
			List<Map<String, Object>> dataList) throws Exception {
		// 创建新的Excel 工作簿
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 在Excel工作簿中建一工作表，其名为缺省值
		XSSFSheet sheet = workbook.createSheet();
		// 在索引0的位置创建行（最顶端的行）
		XSSFRow row = sheet.createRow(0);
		// 设置excel头（第一行）的头名称
		for (int i = 0; i < headList.length; i++) {
			
			// 在索引0的位置创建单元格（左上端）
			XSSFCell cell = row.createCell(i);
			// 定义单元格为字符串类型
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			// 在单元格中输入一些内容
			cell.setCellValue(headList[i]);
		}
		// ===============================================================
		// 添加数据
		for (int n = 0; n < dataList.size(); n++) {
			// 在索引1的位置创建行（最顶端的行）
			XSSFRow row_value = sheet.createRow(n + 1);
			Map<String, Object> dataMap = dataList.get(n);
			// ===============================================================
			for (int i = 0; i < fieldList.length; i++) {

				// 在索引0的位置创建单元格（左上端）
				XSSFCell cell = row_value.createCell(i);
				// 定义单元格为字符串类型
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				// 在单元格中输入一些内容
				cell.setCellValue((dataMap.get(fieldList[i])).toString());
			}
			// ===============================================================
		}
		// 新建一输出文件流
		//FileOutputStream fos = new FileOutputStream(excel_name);
		// 把相应的Excel 工作簿存盘
		workbook.write(os);
		os.flush();
		// 操作结束，关闭文件
		os.close();
		// 关闭workbook
		workbook.close();
	}
}
