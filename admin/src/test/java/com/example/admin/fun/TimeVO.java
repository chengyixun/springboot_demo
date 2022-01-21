package com.example.admin.fun;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@link TimeVO}
 *
 * @author Liyaohui
 * @date 7/15/21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimeVO {

	// 格式：yyyy-MM-dd
	private String beginTime;

	private String endTime;

	public static void main(String[] args) {
		List<TimeVO> timeVOS = Lists.newArrayList();
		timeVOS.add(TimeVO.builder().beginTime("2021-07-01").endTime("2021-07-06").build());
		timeVOS.add(TimeVO.builder().beginTime("2021-07-05").endTime("2021-07-14").build());
		timeVOS.add(TimeVO.builder().beginTime("2021-08-07").endTime("2021-08-19").build());
		timeVOS.add(TimeVO.builder().beginTime("2021-07-15").endTime("2021-07-30").build());

		List<String> result = timeVOS.stream().sorted(Comparator.comparing(TimeVO::getBeginTime))
				.flatMap(m -> Stream.of(m.getBeginTime(), m.getEndTime())).collect(Collectors.toList());

		System.out.println(result);

		boolean ordered = Ordering.from(Comparator.comparing(Object::toString)).isOrdered(result);

		System.out.println(ordered);

		boolean ordered1 = Ordering.natural().isOrdered(result);// 此处输出字符串的字典顺序 == usingToString()
		System.out.println(ordered1);

		boolean ordered2 = Ordering.usingToString().isOrdered(result);
		System.out.println(ordered2);
	}
}
