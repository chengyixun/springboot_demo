package com.example.admin.base;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

/**
 * @ClassName: StringUtilTest
 * @Author: amy
 * @Description: StringUtilTest
 * @Date: 2021/6/24
 * @Version: 1.0
 */
@Slf4j
public class StringUtilTest {

	// 首字母转成大写
	@Test
	public void test1() {
		String s = "sedefD";
		String s1 = StringUtils.capitalize(s);
		System.out.println(s1);
	}

	// 重复拼接字符串
	@Test
	public void test2() {
		String str = StringUtils.repeat("ab", 3);
		System.out.println(str); // 输出abab
	}

	@Test
	public void test3() throws ParseException {
		// Date类型转String类型
		String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		System.out.println(date); // 输出 2021-05-01 01:01:01

		// String类型转Date类型
		Date date1 = DateUtils.parseDate("2021-05-01 01:01:01", "yyyy-MM-dd HH:mm:ss");
		System.out.println(date1);
		// 计算一个小时后的日期
		Date date2 = DateUtils.addHours(new Date(), 1);
		System.out.println(date2);
	}
}
