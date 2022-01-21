package com.example.tk.common.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @ClassName: EasyExcelUtil
 * @Author: amy
 * @Description: EasyExcelUtil
 * @Date: 2021/8/16
 * @Version: 1.0
 */
@Slf4j
public class EasyExcelUtil {

	public static void exportWeb(HttpServletResponse response, String excelName, String sheetName, Class clazz,
			List data) {
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		// 这里URLEncoder.encode可以防止中文乱码
		try {
			excelName = URLEncoder.encode(excelName, "UTF-8");
			response.setHeader("Content-disposition",
					"attachment;filename=" + excelName + ExcelTypeEnum.XLSX.getValue());
			EasyExcel.write(response.getOutputStream(), clazz).sheet(sheetName).doWrite(data);
		} catch (Exception e) {
			log.error("export error:{}", e.getMessage());
		}

	}

	/**
	 * 下载模板文件
	 * 
	 * @param response
	 * @param inFileName
	 * @param outFileNam
	 */
	public static void downloadExcel(HttpServletResponse response, String inFileName, String outFileNam) {
		InputStream inputStream = null;
		try {
			response.reset();
			// 设置输出文件格式
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(outFileNam.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
			ServletOutputStream outputStream = response.getOutputStream();

			ClassPathResource cpr = new ClassPathResource("/templates/" + inFileName);
			inputStream = cpr.getInputStream();
			// inputStream = getClass().getResourceAsStream("/templates/" + inFileName);
			byte[] buff = new byte[1024];
			int length;
			while ((length = inputStream.read(buff)) != -1) {
				outputStream.write(buff, 0, length);
			}
			if (outputStream != null) {
				outputStream.flush();
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {

				try {
					inputStream.close();
				} catch (IOException e) {
					log.error("关闭资源出错" + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

}
