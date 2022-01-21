package com.example.jpamultitenancy;

import com.example.jpamultitenancy.common.util.DateUtils;
import com.example.jpamultitenancy.tenant.entity.Group;
import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @ClassName: DateTest @Author: amy @Description: 日期测试 @Date:
 *             2021/5/28 @Version: 1.0
 */
@Slf4j
public class DateTest {

	/** 只会获取年月日 */
	@Test
	public void testLocalDate() {
		// 获取当前年月日
		LocalDate localDate = LocalDate.now();
		System.out.println(localDate);
		// 构造指定的年月日
		LocalDate localDate1 = LocalDate.of(2019, 9, 10);
		System.out.println(localDate1);
	}

	/** 只会获取几点几分几秒 */
	@Test
	public void testLocalTime() {
		LocalTime localTime = LocalTime.of(13, 51, 10);
		System.out.println(localTime);
		LocalTime localTime1 = LocalTime.now();
		System.out.println(localTime1);
		// 获取小时
		int hour = localTime.getHour();
		int hour1 = localTime.get(ChronoField.HOUR_OF_DAY);
		System.out.println("hour:" + hour + ";" + hour1);
		// 获取分
		int minute = localTime.getMinute();
		int minute1 = localTime.get(ChronoField.MINUTE_OF_HOUR);
		System.out.println("minute:" + minute + ";" + minute1);
		// 获取秒
		int second = localTime.getSecond();
		int second1 = localTime.get(ChronoField.SECOND_OF_MINUTE);
		System.out.println("second:" + second + ";" + second1);
	}

	/** 获取年月日时分秒，等于LocalDate+LocalTime */
	@Test
	public void testLocalDateTime() {
		LocalDateTime localDateTime = LocalDateTime.now();
		System.out.println(localDateTime);
		/*
		 * //修改年为2019 localDateTime = localDateTime.withYear(2020); //修改为2022
		 * localDateTime = localDateTime.with(ChronoField.YEAR, 2022);
		 */

		String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		System.out.println(format);
	}

	@Test
	public void testCan() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
		Date time = calendar.getTime();
		System.out.println(time);
		LocalDateTime localDateTime = DateUtils.toLocalDateTime(time);
		System.out.println(localDateTime);
	}

	@Test
	public void testDup() {
		Set<Group> groupSet = new HashSet<>();
		Set<Group> groupSet1 = new HashSet<>();
		groupSet1.add(Group.builder().code("ww").name("group_B").build());
		groupSet1.add(Group.builder().code("yy").name("group_B").build());

		Set<Group> groupSet2 = new HashSet<>();
		groupSet2.add(Group.builder().code("11").name("group_B").build());
		groupSet2.add(Group.builder().code("yy").name("group_B").build());

		groupSet.addAll(groupSet1);
		groupSet.retainAll(groupSet2); // 交集

		// groupSet.addAll(groupSet2);//并将

		Iterator<Group> iterator = groupSet.iterator();
		while (iterator.hasNext()) {
			Group group = iterator.next();
			System.out.println(group);
		}
	}

	@Test
	public void testRange() {
		/*
		 * Range<Integer> range = Range.closed(1, 3);
		 * System.out.println(range.upperEndpoint().intValue()); Duration between =
		 * Duration.between(LocalTime.NOON, LocalTime.MAX);
		 * System.out.println(between.toMinutes());
		 */

		Date start = DateUtils.toDate(LocalDate.of(2021, 06, 01));
		Date end = DateUtils.toDate(LocalDate.of(2021, 06, 30));

		Date start1 = DateUtils.toDate(LocalDate.of(2021, 07, 15));
		Date end1 = DateUtils.toDate(LocalDate.of(2021, 07, 30));

		Range<Date> dateRange = Range.closed(start, end);
		Range<Date> calDateRange = Range.closed(start1, end1);

		if (dateRange.isConnected(calDateRange)) {
			Range<Date> intersection = dateRange.intersection(calDateRange);
			Date lowerEndpoint = intersection.lowerEndpoint();
			Date upperEndpoint = intersection.upperEndpoint();
			int startIndex = DateUtils.getDayOfMonth(lowerEndpoint);
			int endIndex = DateUtils.getDayOfMonth(upperEndpoint);
			// box Integer.valueOf
			List<Integer> workDateList = IntStream.rangeClosed(startIndex, endIndex).boxed()
					.collect(Collectors.toList());
			System.out.println(workDateList);
		}

		Date date = Optional.ofNullable(end1).orElse(start);
		System.out.println(date);

		Range<Integer> intersection1 = Range.closed(0, 5).intersection(Range.closed(3, 9));
		System.out.println(intersection1.lowerEndpoint());
		System.out.println(intersection1.upperEndpoint());
	}

	@Test
	public void testRange2() {
		// 当前是周一
		String c1 = "2021-09-06";
		String c2 = "2021-09-12";

		String s1 = "2021-09-06";
		String s2 = "2021-09-07";
		Range<String> c = Range.closed(c1, c2);

		Range<String> s = Range.closed(s1, s2);

		if (c.isConnected(s)) {
			Range<String> intersection = c.intersection(s);
			System.out.println("connect");
			System.out.println(intersection.lowerEndpoint());
			System.out.println(intersection.upperEndpoint());
		}
	}

	@Test
	public void testRange3() {
		// 当前是周一
		String c1 = "2021-09-06";
		String c2 = "2021-09-12";

		String s1 = "2021-09-06";
		String s2 = "2021-09-07";
		Range<String> c = Range.closed(c1, c2);
		Range<String> s = Range.closed(s1, s2);

		if (c.isConnected(s)) {
			Range<String> intersection = c.intersection(s);
			System.out.println("connect");
			System.out.println(intersection.lowerEndpoint());
			System.out.println(intersection.upperEndpoint());
		}
	}

	@Test
	public void testDuration() {
		String beginTime = "12:00";
		String endTime = "13:00";
		Duration between1 = Duration.between(LocalTime.parse(beginTime), LocalTime.parse(endTime));
		System.out.println("区间：相差的小时 ：" + between1.toHours() + " 相差的分钟：" + between1.toMinutes());
	}

	@Test
	public void testDuration2() {
		Duration between1 = Duration.between(LocalDateTime.of(2021, 07, 01, 01, 12, 12, 12),
				LocalDateTime.of(2021, 06, 01, 12, 12, 12));
		System.out.println("区间：相差的小时 ：" + between1.toHours() + " 相差的分钟：" + between1.toMinutes());

		Duration between = Duration.between(LocalDate.of(2021, 07, 01), LocalDate.of(2021, 07, 03));
		System.out.println("区间：相差的小时 ：" + between.toDays());
	}

	@Test
	public void testLocalTime1() {
		LocalTime localTime = LocalTime.now();
		System.out.println(localTime);
		DateTimeFormatter formatter = DateTimeFormatter.ISO_TIME;
		String openTimeStr = localTime.format(formatter);
		System.out.println(openTimeStr);
	}

	@Test
	public void testLocalDateTime2() {
		// date
		Date date = DateUtils.parse("2021-06-23", "yyyy-MM-dd");
		int dayOfMonth = DateUtils.toLocalDateTime(date).getDayOfMonth();
		// int dayOfMonth = localDateTime.getDayOfMonth();
		System.out.println(dayOfMonth);
	}

	@Test
	public void testDateToLocal() {
		// date
		Date date = DateUtils.parse("2021-06-01", "yyyy-MM-dd");
		// date to localDate
		LocalDate localDate = DateUtils.toLocalDate(date);
		// localTime
		LocalTime localTime = LocalTime.of(12, 12, 12);
		// LocalDateTime = localDate+ localTime
		LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
		// LocalDateTime to date
		Date asDate = DateUtils.toDate(localDateTime);
		System.out.println(asDate);
	}

	@Test
	public void testLocalTimeCompare() {
		LocalTime openTime = LocalTime.of(12, 12, 12);
		LocalTime closeTime = LocalTime.now();
		boolean isNight = openTime.isAfter(closeTime) || openTime.equals(closeTime);
		System.out.println(isNight);
		int i = openTime.compareTo(closeTime);
		System.out.println(i);
		int b = closeTime.compareTo(openTime);
		System.out.println(b);
	}

	@Test
	public void testLocalDateTimeFormat() {
		LocalDateTime localDateTime = LocalDateTime.now();
		String format = localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		System.out.println(format);
	}

	/**
	 * ***********************************LocalDateTime**********************************************
	 */
	/** ISO 8601 规定的日期和时间分隔符是T 2021-06-01T22:22:26.069 */
	@Test
	public void test1() {
		LocalDateTime dt = LocalDateTime.now(); // 当前日期和时间
		LocalDate d = dt.toLocalDate(); // 转换到当前日期
		LocalTime t = dt.toLocalTime(); // 转换到当前时间
		System.out.println(dt);
		System.out.println(d);
		System.out.println(t);
	}

	@Test
	public void test2() {
		// 自定义格式化:
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		System.out.println("自定义格式化 LocalDateTime to String: " + dtf.format(LocalDateTime.now()));

		// 用自定义格式解析:
		LocalDateTime dt2 = LocalDateTime.parse("2019/11/30 15:16:17", dtf);
		System.out.println("用自定义格式解析 String to LocalDateTime:" + dt2);
	}

	/** 加减 */
	@Test
	public void test3() {
		LocalDateTime dt = LocalDateTime.of(2019, 10, 26, 20, 30, 59);
		System.out.println(dt);
		// 加5天减3小时:
		LocalDateTime dt2 = dt.plusDays(5).minusHours(3);
		System.out.println(dt2); // 2019-10-31T17:30:59
		// 减1月:
		LocalDateTime dt3 = dt2.minusMonths(1);
		System.out.println(dt3); // 2019-09-30T17:30:59
	}

	/** with */
	@Test
	public void test4() {
		LocalDateTime dt = LocalDateTime.of(2019, 10, 26, 20, 30, 59);
		System.out.println(dt); // 2019-10-26T20:30:59
		// 日期变为31日
		LocalDateTime dt2 = dt.withDayOfMonth(31);
		System.out.println(dt2); // 2019-10-31T20:30:59

		// 月份变为 9
		LocalDateTime dt3 = dt2.withMonth(9);
		System.out.println(dt3); // 2019-09-30T20:30:59
		LocalDateTime localDateTime = dt.withHour(0).withMinute(0).withSecond(0);
		LocalDateTime with = dt.with(LocalTime.MIN);
		System.out.println(localDateTime);
		System.out.println(with);
	}

	/** 定制api */
	@Test
	public void test5() {
		// 本月第一天 0：00 时刻
		LocalDateTime firstDay = LocalDate.now().withDayOfMonth(1).atStartOfDay();
		System.out.println(firstDay); // 2021-06-01T00:00

		// 本月最后1天
		LocalDate lastDay = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
		System.out.println(lastDay); // 2021-06-30

		// 下月第1天
		LocalDate nextMonthFirstDay = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
		System.out.println(nextMonthFirstDay); // 2021-07-01

		// 本月第1个周一
		LocalDate firstWeekDay = LocalDate.now().with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
		System.out.println(firstWeekDay); // 2021-06-07

		LocalDate localDate = LocalDate.of(2021, 6, 6);
		LocalDate with = localDate.with(TemporalAdjusters.dayOfWeekInMonth(1, localDate.getDayOfWeek()));
		System.out.println(with);
	}

	/** 比较 */
	@Test
	public void test6() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime target = LocalDateTime.of(2019, 11, 19, 8, 15, 0);
		System.out.println(now.isBefore(target)); // false
		System.out.println(LocalDate.now().isBefore(LocalDate.of(2019, 11, 19))); // false
		System.out.println(LocalTime.now().isAfter(LocalTime.parse("08:15:00"))); // true
	}

	/** duration period */
	@Test
	public void test7() {

		LocalDateTime start = LocalDateTime.of(2019, 11, 19, 8, 15, 0);
		LocalDateTime end = LocalDateTime.of(2020, 1, 9, 19, 25, 30);

		Duration between = Duration.between(start, end);
		System.out.println("duration: " + between + " days:" + between.toDays());

		Period p = LocalDate.of(2019, 11, 19).until(LocalDate.of(2020, 1, 9));
		System.out.println(p);
	}

	@Test
	public void test8() {
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalTime localTime = localDateTime.toLocalTime();
		System.out.println(localTime);
		System.out.println(localDateTime.getHour() * 60);
		Integer integer = DateUtils.toMinutes(localTime);
		System.out.println(integer);
		int a = 1;
		long b = 2;
		a += b;
		System.out.println(a);
	}

	@Test
	public void test9() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
		System.out.println(w - 1);

		// LocalDate now = LocalDate.now();
		LocalDate now = LocalDate.of(2021, 3, 1);
		DayOfWeek dayOfWeek = now.getDayOfWeek();
		System.out.println(dayOfWeek.getValue());
		long l = now.toEpochDay();
		System.out.println(l);
	}

	@Test
	public void testCom() {
		LocalDateTime a = LocalDateTime.of(2012, 6, 30, 12, 00);
		LocalDateTime b = LocalDateTime.of(2012, 7, 1, 12, 00);
		int i = a.compareTo(b);
		System.out.println(i);

		int i1 = b.compareTo(a);
		System.out.println(i1);

		int i2 = a.compareTo(a);
		System.out.println(i2);

		System.out.println(a);
		LocalDateTime localDateTime = a.plusDays(1);
		System.out.println(localDateTime);
	}

	@Test
	public void testCom2() {
		String o1 = "08:00";
		String o2 = "10:00";

		System.out.println(o1.compareTo(o2));
	}

	@Test
	public void testPreData() {
		Date start = DateUtils.toDate(LocalDate.of(2021, 06, 01));
		Date end = DateUtils.toDate(LocalDate.of(2021, 06, 13));
		// 计算 [start-end] 之间的 周末的日期数
		LocalDate startDate = DateUtils.toLocalDate(start);
		LocalDate endDate = DateUtils.toLocalDate(end);

		List<Integer> weekDays = Lists.newArrayList();

		while (startDate.compareTo(endDate) <= 0) {
			DayOfWeek week = startDate.getDayOfWeek();
			if (week == DayOfWeek.SATURDAY || week == DayOfWeek.SUNDAY) {
				System.out.println("周末，日期：" + startDate);
				weekDays.add(startDate.getDayOfMonth());
			}
			startDate = startDate.plusDays(1);
		}

		weekDays.forEach(System.out::println);
	}

	@Test
	public void testBreak() {
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < 5; i++) {
				if (i == 3)
					break;
				System.out.println("The number i:" + i + " The number j:" + j);
			}
		}
	}

	@Test
	public void testCon() {

		for (int i = 0; i <= 5; i++) {
			if (i == 3)
				continue;
			System.out.println("The number i:" + i);
		}
	}

	@Test
	public void test20() {
		String[] str = {};
		System.out.println(str);
		if (Objects.isNull(str)) {
			System.out.println("s: " + str);
		}
	}

	/** ZonedDate、 ZonedTime、 ZonedDateTime */
	@Test
	public void test21() {
		Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
		availableZoneIds.forEach(System.out::println);
	}

	/** 获取带时区的时间 和 日期 */
	@Test
	public void test22() {
		ZoneId zoneId = ZoneId.of("America/Panama");
		LocalDateTime now = LocalDateTime.now(zoneId);
		System.out.println(now);
	}

	@Test
	public void test23() {
		String startDate = "2021-07-01";
		String endDate = "2021-07-05";

		LocalDate startLocalDate = DateUtils.toLocalDate(startDate);
		LocalDate endLocalDate = DateUtils.toLocalDate(endDate);

		while (startLocalDate.compareTo(endLocalDate) <= 0) {

			System.out.println(DateUtils.format(startLocalDate));

			startLocalDate = startLocalDate.plusDays(1);
		}
	}

	/** a<b : a.compareTo(b) <0 a=b : a.compareTo(b) =0 a>b : a.compareTo(b) >0 */
	@Test
	public void testDate22() {
		String a = "2021-09-01";
		String b = "2021-09-03";

		System.out.println(a.compareTo(b));

		Date date = DateUtils.parseDate(a);
		Date date2 = DateUtils.parseDate(b);

		System.out.println(date.compareTo(date2));

		System.out.println(Range.closed(a, b).contains("2021-09-02"));
	}

	@Test
	public void test55() {
		String join = String.join(":", "WORKINGINOTHER", "6d407396-63f1-43c4-a01c-f164da181f3d", "点心车间");
		System.out.println(join.length());
		System.out.println(join);
	}

	@Test
	public void testMin() {
		Integer integer = DateUtils.toMinutes("18:40");
		System.out.println(integer);
	}
}
