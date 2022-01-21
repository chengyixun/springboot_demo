package com.example.jpamultitenancy.common.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: DateUtil @Author: amy @Description: 日期格式 @Date:
 *             2021/5/28 @Version: 1.0
 */
@Slf4j
public class DateUtils {

	public static final DateTimeFormatter LOCAL_DATE_TIME = new DateTimeFormatterBuilder()
			.append(DateTimeFormatter.ISO_LOCAL_DATE).appendLiteral(' ').append(DateTimeFormatter.ISO_LOCAL_TIME)
			.toFormatter();

	public static String format(LocalDateTime localDateTime, String pattern) {
		return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
	}

	public static String format(LocalDateTime localDateTime) {
		return LOCAL_DATE_TIME.format(localDateTime);
	}

	public static String format(Date date) {
		return LOCAL_DATE_TIME.format(toLocalDateTime(date));
	}

	public static String format(Date date, String pattern) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
		return df.format(toLocalDateTime(date));
	}

	public static String format(LocalDate localDate) {
		return DateTimeFormatter.ISO_LOCAL_DATE.format(localDate);
	}

	public static String formatDate(Date date) {
		return DateTimeFormatter.ISO_LOCAL_DATE.format(toLocalDateTime(date));
	}

	public static Date parse(String dateTime) {
		LocalDateTime localDateTime = LocalDateTime.from(LOCAL_DATE_TIME.parse(dateTime));
		return toDate(localDateTime);
	}

	public static Date parse(String date, String pattern) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime localDateTime = LocalDateTime.parse(date, dateTimeFormatter);
		return toDate(localDateTime);
	}

	public static Date parseDate(String date) {
		LocalDate localDate = LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(date));
		return toDate(localDate);
	}

	public static Integer toMinutes(LocalTime time) {
		return time.getHour() * 60 + time.getMinute();
	}

	public static Integer toMinutes(String time) {
		return toMinutes(LocalTime.parse(time));
	}

	public static Date plus(Date date, long amountToAdd, TemporalUnit unit) {
		LocalDateTime dateTime = toLocalDateTime(date);
		return toDate(dateTime.plus(amountToAdd, unit));
	}

	public static Date plusMonths(Date date, int months) {
		LocalDateTime dateTime = toLocalDateTime(date);
		return toDate(dateTime.plusMonths(months));
	}

	public static Date plusDays(Date date, int days) {
		LocalDateTime dateTime = toLocalDateTime(date);
		return toDate(dateTime.plusDays(days));
	}

	public static LocalDate toLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static LocalDate toLocalDate(String dateTime) {
		return Instant.ofEpochMilli(parseDate(dateTime).getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	/**
	 * 默认时区
	 *
	 * @param date
	 * @return
	 */
	public static LocalDateTime toLocalDateTime(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public static Date toDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date toDate(LocalDate localDate) {
		return toDate(localDate.atTime(LocalTime.MIN));
	}

	public static int getDayOfMonth(Date date) {
		return toLocalDateTime(date).getDayOfMonth();
	}

	public static Integer getWorkDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int weekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDay - 1;
	}

	/**
	 * 计算 [start-end] 之间的 周末的日期数
	 *
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<Integer> getWeekDays(Date start, Date end) {
		LocalDate startDate = toLocalDate(start);
		LocalDate endDate = toLocalDate(end);
		List<Integer> weekDays = Lists.newArrayList();
		while (startDate.compareTo(endDate) <= 0) {
			DayOfWeek week = startDate.getDayOfWeek();
			if (week == DayOfWeek.SATURDAY || week == DayOfWeek.SUNDAY) {
				weekDays.add(startDate.getDayOfMonth());
			}
			startDate = startDate.plusDays(1);
		}
		return weekDays;
	}

	/**
	 * 获取区间内的 指定 weekDay的 日期数
	 *
	 * @param weekDay
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<Integer> getWeekDays(String weekDay, Date start, Date end) {
		LocalDate startDate = toLocalDate(start);
		LocalDate endDate = toLocalDate(end);
		List<Integer> weekDays = Lists.newArrayList();
		while (startDate.compareTo(endDate) <= 0) {
			DayOfWeek week = startDate.getDayOfWeek();
			if (Integer.valueOf(weekDay) == week.getValue()) {
				System.out.println(week.getValue());
				weekDays.add(startDate.getDayOfMonth());
			}
			startDate = startDate.plusDays(1);
		}
		return weekDays;
	}
}
