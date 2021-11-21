package com.example.tk;

import com.example.tk.entity.User;
import com.example.tk.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @ClassName: ExcelTest
 * @Author: amy
 * @Description: ExcelTest
 * @Date: 2021/7/23
 * @Version: 1.0
 */
@Slf4j
public class ExcelTest {

	/**
	 * 导出数据
	 * 
	 * @throws Exception
	 */
	@Test
	public void testExport() throws Exception {
		// 0.准备数据
		User user = User.builder().id(1).name("Amy").sex("女").build();

		String[] titles = { "编号", "名字", "性别" };
		// 1.创建文件对象 创建HSSFWorkbook只能够写出为xls格式的Excel
		// 要写出 xlsx 需要创建为 XSSFWorkbook 两种Api基本使用方式一样
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建表对象
		HSSFSheet sheet = workbook.createSheet("users");
		HSSFRow titleRow = sheet.createRow(0);
		for (int i = 0; i < titles.length; i++) {
			//
			HSSFCell cell = titleRow.createCell(i);
			cell.setCellValue(titles[i]);
		}

		HSSFRow row = sheet.createRow(1);
		row.createCell(0).setCellValue(user.getId());
		row.createCell(1).setCellValue(user.getName());
		row.createCell(2).setCellValue(user.getSex());

		workbook.write(new FileOutputStream("/Users/amy/Gaia/file/hhhh.xls"));
	}

	/** Excel 导入 1. Excel文件 2. 流读取文件 3. poi解析流 获取数据 4. 通过Mybatis 添加数据 */
	@Test
	public void testImport() throws Exception {
		// 1.通过流读取Excel文件
		FileInputStream fis = new FileInputStream("/Users/amy/Gaia/file/hhhh.xls");
		// 2.通过poi解析流 HSSFWorkbook 处理流得到的对象中 就封装了Excel文件所有的数据
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		// 3. 从文件中获取表对象，getSheetAt通过下标获取
		HSSFSheet sheet = workbook.getSheetAt(0);
		// 4.从表中获取到行数据 从第二行开始 到 最后一行 getLastRowNum() 获取最后一行的下标
		int lastRowNum = sheet.getLastRowNum();

		for (int i = 1; i <= lastRowNum; i++) {
			// 通过下标获取行
			HSSFRow row = sheet.getRow(i);
			// 从行中获取数据
			/** getNumericCellValue() 获取数字 getStringCellValue 获取String */
			double id = row.getCell(0).getNumericCellValue();
			String name = row.getCell(1).getStringCellValue();
			String sex = row.getCell(2).getStringCellValue();

			// 封装到对象中
			User user = User.builder().id((int) id).name(name).sex(sex).build();

			// 将对象添加数据库
			System.out.println(user);
		}
	}

	@Test
	public void testSb() {
		StringBuilder sb = new StringBuilder();

		String a = "";
		String b = "222";
		sb.append(a);
		sb.append(b);
		String join = String.join(";", a, b);
		System.out.println(join);
	}


	public List<String> getValues(Integer i){
		List<UserVO> userVOS = new ArrayList<>();
		userVOS.add(UserVO.builder().name("nacy").age(21L).sex("男").build());
		userVOS.add(UserVO.builder().name("lily").age(33L).sex("女").build());

		UserVO m = userVOS.get(i);
		return Stream.of(m.getName(), m.getSex(), String.valueOf(m.getAge()))
				.collect(Collectors.toList());
	}

	public UserVO getValue(Integer i){
		List<UserVO> userVOS = new ArrayList<>();
		userVOS.add(UserVO.builder().name("nacy").age(21L).sex("男").build());
		userVOS.add(UserVO.builder().name("lily").age(33L).sex("女").build());

		return userVOS.get(i);

	}


	@Test
	public void testFlat() {

		Map<String, List<String>> dataList = new HashMap<>();

		// data
		List<UserVO> userVOS = new ArrayList<>();
		userVOS.add(UserVO.builder().name("nacy").age(21L).sex("男").build());
		userVOS.add(UserVO.builder().name("lily").age(33L).sex("女").build());
		AtomicInteger index=new AtomicInteger(1);


		List<Object> collect = IntStream.of(1, userVOS.size()).mapToObj(m -> getValue(m)).flatMap(m -> {
		return 	Stream.of(m.getName(), m.getSex(), String.valueOf(m.getAge()));
		}).collect(Collectors.toList());

	}

	@Test
	public void testE(){
		Integer index = TabooEnum.getIndex("X-ray");
        System.out.println(index);
	}
}
