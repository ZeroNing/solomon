package com.steven.solomon.utils.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.steven.solomon.base.code.BaseCode;
import com.steven.solomon.base.code.error.BaseExceptionCode;
import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.utils.logger.LoggerUtils;
import com.steven.solomon.utils.verification.ValidateUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.Charsets;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;

/**
 * Excel工具类
 */
public class ExcelUtils {

	private static final Logger logger = LoggerUtils.logger(ExcelUtils.class);

	/**
	 * 导出Excel(07版.xlsx)到指定路径下
	 *
	 * @param path      路径
	 * @param excelName Excel名称
	 * @param sheetName sheet页名称
	 * @param clazz     Excel要转换的类型
	 * @param data      要导出的数据
	 */
	public static void export2File(String path, String excelName, String sheetName, Class<?> clazz, List<Object> data) {
		String fileName = path.concat(excelName).concat(ExcelTypeEnum.XLSX.getValue());
		EasyExcel.write(fileName, clazz).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
				.sheet(sheetName).doWrite(data);
	}

	/**
	 * 导出Excel(07版.xlsx)到web
	 *
	 * @param response  响应
	 * @param excelName Excel名称
	 * @param sheetName sheet页名称
	 * @param clazz     Excel要转换的类型
	 * @param data      要导出的数据
	 * @throws Exception
	 */
	public static void export2Web(HttpServletResponse response, String excelName, String sheetName, Class<?> clazz,
			List<?> data) throws Exception {
		response.setContentType("application/x-download");
		response.setCharacterEncoding("iso-8859-1");

		// 这里URLEncoder.encode可以防止中文乱码
//        excelName = URLEncoder.encode(excelName, BaseCode.UTF8);
		String fileName2Export = new String((excelName).getBytes(Charsets.UTF_8), Charsets.ISO_8859_1);

		response.setHeader("Content-disposition",
				"attachment;filename=" + fileName2Export + ExcelTypeEnum.XLSX.getValue());

		EasyExcel.write(response.getOutputStream(), clazz)
				.registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).sheet(sheetName).doWrite(data);
	}

	/**
	 * 将指定位置指定名称的Excel导出到web
	 *
	 * @param response  响应
	 * @param path      文件路径
	 * @param excelName 文件名称
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static void export2Web4File(HttpServletResponse response, String path, String excelName)
			throws UnsupportedEncodingException, BaseException {
		File file = new File(path.concat(excelName).concat(ExcelTypeEnum.XLSX.getValue()));
		ValidateUtils.isEmpty(file, BaseExceptionCode.FILE_IS_NOT_EXIST_EXCEPTION_CODE);

		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding(BaseCode.UTF8);
		// 这里URLEncoder.encode可以防止中文乱码
		excelName = URLEncoder.encode(excelName, BaseCode.UTF8);
		response.setHeader("Content-disposition", "attachment;filename=" + excelName + ExcelTypeEnum.XLSX.getValue());

		try (FileInputStream in = new FileInputStream(file); ServletOutputStream out = response.getOutputStream();) {
			IOUtils.copy(in, out);
		} catch (Exception e) {
			throw new BaseException(BaseExceptionCode.BASE_EXCEPTION_CODE);
		}
	}

}
