package com.example.admin.fun;

import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * {@link StreamTest} stream: 1.不存储数据 2.不改变数据源，通常情况下会产生一个新的集合或一个值。 3.具有延迟执行特性，只有调用终端操作时，中间操作才会执行。
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-04-23
 */
@Slf4j
public class StreamTest {

  private static Random random = new Random();

  public List<Person> personList() {
    List<Person> personList = Lists.newArrayList();
    personList.add(new Person("Tom", 8900, 20, "male", "New York", "2021-06-01", "1,2"));
    personList.add(new Person("Jack", 7000, 30, "male", "Washington", "2021-05-01", "1,2,3,4"));
    personList.add(new Person("Tom2", 7000, 20, "female", "Washington", "2021-03-01", "1,2,5"));
    personList.add(new Person("Anni", 8200, 19, "female", "New York", "2021-04-01", "6,7"));
    personList.add(new Person("Owen", 9500, 31, "male", "New York", "2021-05-01", "8,9"));
    personList.add(new Person("Alisa", 7900, 30, "female", "New York", "2021-07-01", "1,2,3,4"));
    return personList;
  }

  public List<User> users() {
    List<User> users = Lists.newArrayList();
    users.add(new User("9527", 20));
    users.add(new User("9528", 30));
    users.add(new User("9529", 40));
    users.add(new User("9530", 50));
    return users;
  }

  public List<Blog> blogs() {
    List<Blog> blogs = Lists.newArrayList();
    blogs.add(Blog.builder().code("A").currentCode("B").build());

    blogs.add(Blog.builder().code("Y").currentCode("C").build());
    blogs.add(Blog.builder().code("H").currentCode("Y").build());

    blogs.add(Blog.builder().code("D").currentCode("E").build());
    blogs.add(Blog.builder().code("D").currentCode("F").build());
    blogs.add(Blog.builder().code("D").currentCode("G").build());

    blogs.add(Blog.builder().code("K").currentCode("I").build());
    blogs.add(Blog.builder().code("L").currentCode("I").build());
    return blogs;
  }

  public List<ScheduleBreak> scheduleBreaks() {
    List<ScheduleBreak> scheduleBreaks = Lists.newArrayList();
    scheduleBreaks.add(
        ScheduleBreak.builder()
            .unit("U_1")
            .personId("xx")
            .scheduleDate("2021-11-08")
            .status(1)
            .build());
    scheduleBreaks.add(
        ScheduleBreak.builder()
            .unit("U_1")
            .personId("yy")
            .scheduleDate("2021-11-08")
            .status(2)
            .build());
    scheduleBreaks.add(
        ScheduleBreak.builder()
            .unit("U_1")
            .personId("zz")
            .scheduleDate("2021-11-09")
            .status(3)
            .build());
    scheduleBreaks.add(
        ScheduleBreak.builder()
            .unit("U_1")
            .personId("cc")
            .scheduleDate("2021-11-10")
            .status(4)
            .build());
    scheduleBreaks.add(
        ScheduleBreak.builder()
            .unit("U_1")
            .personId("aa")
            .scheduleDate("2021-11-11")
            .status(1)
            .build());
    return scheduleBreaks;
  }

  @Test
  public void testGroupBy() {

    Map<String, Map<Integer, Long>> map =
        scheduleBreaks().stream()
            .collect(
                Collectors.groupingBy(
                    ScheduleBreak::getScheduleDate,
                    Collectors.groupingBy(ScheduleBreak::getStatus, Collectors.counting())));

    Long count = map.getOrDefault("1", Maps.newHashMap()).getOrDefault(1, 0L);
    System.out.println(map);
  }

  @Test
  public void testSort() {
    List<Person> people = personList();

    List<Person> sorted =
        people.stream()
            .sorted(Comparator.comparing(Person::getSalary).thenComparing(Person::getAge))
            .collect(Collectors.toList());
    System.out.println(sorted);
  }

  @Test
  public void test_1() {
    Stream.of(1, 2, 3, 4, 5, 6).forEach(System.out::print);
    Stream.iterate(0, (x) -> x + 3).limit(4).forEach(System.out::print);
    Stream.generate(Math::random).limit(4).forEach(System.out::print);
  }

  @Test
  public void testArrayAndList() {
    int[] a = {1, 2, 3, 5};
    /** int [] 数组 转 List */
    List<Integer> list1 = Arrays.stream(a).boxed().collect(Collectors.toList());

    /** int[] 转 Integer[] */
    Integer[] integers = Arrays.stream(a).boxed().toArray(Integer[]::new);
  }

  /** */
  @Test
  public void test1() {
    List<String> list = Arrays.asList("a", "b", "c");
  }

  /** 使用stream的静态方法 of iterate generate */
  @Test
  public void test2() {
    Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6);

    Stream<Integer> stream1 = Stream.iterate(0, (x) -> x + 3).limit(3);
    stream1.forEach(System.out::println);

    Stream<Double> stream2 = Stream.generate(Math::random).limit(3);
    stream2.forEach(System.out::println);
  }

  /**
   * 3. 遍历/匹配（foreach/find/match） stream和parallelStream的简单区分： stream是顺序流，由主线程按顺序对流执行操作，
   * 而parallelStream是并行流，内部以多线程并行执行的方式对流进行操作，但前提是流中的数据处理没有顺序要求
   */
  @Test
  public void test3() {
    List<Integer> list = Arrays.asList(7, 6, 9, 3, 8, 2, 1);
    // 遍历输出符合条件的元素 >6
    list.stream().filter(x -> x > 6).forEach(System.out::println);
    // 匹配第一个
    Optional<Integer> findFirst = list.stream().filter(x -> x > 6).findFirst();
    // 匹配任意（适用于并行流）
    Optional<Integer> findAny = list.parallelStream().filter(x -> x > 6).findAny();

    // 是否包含符合特定条件的元素
    boolean anyMatch = list.stream().anyMatch(x -> x < 6);
    System.out.println("匹配第一个值：" + findFirst.get());
    System.out.println("匹配任意一个值：" + findAny.get());
    System.out.println("是否存在大于6的值：" + anyMatch);

    // Optional<Integer> first = list.stream().parallel().filter(x -> x >
    // 6).findFirst();

  }

  /** 筛选（filter） 筛选员工中工资高于8000的人，并形成新的集合。 形成新集合依赖collect（收集） */
  @Test
  public void test4() {
    List<String> names =
        personList().stream()
            .filter(x -> x.getSalary() > 8000)
            .map(Person::getName)
            .collect(Collectors.toList());

    System.out.print("高于8000的员工姓名：" + names);
  }

  /** 聚合（max/min/count) */
  @Test
  public void test5() {
    // 获取String集合中最长的元素
    List<String> list = Arrays.asList("adnm", "admmt", "pot", "xbangd", "weoujgsd");
    Optional<String> max = list.stream().max(Comparator.comparing(String::length));
    max.ifPresent(u -> System.out.println("最长的字符串：" + u));

    // 获取Integer集合中的最大值。
    List<Integer> list1 = Arrays.asList(7, 6, 9, 4, 11, 6);
    // 自然排序
    Optional<Integer> max1 = list1.stream().max(Integer::compareTo);

    Optional<Integer> max2 =
        list1.stream()
            .max(
                new Comparator<Integer>() {
                  @Override
                  public int compare(Integer o1, Integer o2) {
                    return o1.compareTo(o2);
                  }
                });
    System.out.println("自然排序的最大值：" + max1.get());
    System.out.println("自定义排序的最大值：" + max2.get());

    long count = list1.stream().filter(x -> x > 6).count();
    System.out.println("list1中大于6的元素个数：" + count);
  }

  /**
   * 映射(map/flatMap) 可以将一个流的元素按照一定的规则映射到另外一个流中，
   *
   * <p>map：接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射到一个新的元素。
   *
   * <p>flatMap：接收一个函数作为参数，将流中的每个值都换成另外一个流，然后把所有的流练成一个流
   */
  @Test
  public void test6() {
    // 案例一：英文字符串数组的元素全部改为大写。整数数组每个元素+3
    String[] strArr = {"abcd", "bcdd", "defde", "fTr"};
    List<String> strList =
        Arrays.stream(strArr).map(String::toUpperCase).collect(Collectors.toList());
    List<Integer> list = Arrays.asList(1, 3, 5, 7, 9);
    List<Integer> intListNew = list.stream().map(m -> m + 3).collect(Collectors.toList());
    System.out.println("每个元素大写：" + strList);
    System.out.println("每个元素+3：" + intListNew);
  }

  /** flapMap 使用 将两个字符合并成一个新的字符数组 */
  @Test
  public void test61() {
    List<String> list = Arrays.asList("m,k,l,a", "1,3,5,7", "1");
    List<String> listNew =
        list.stream()
            .flatMap(
                s -> {
                  // 将每个元素转换成一个stream
                  String[] split = s.split(",");
                  Stream<String> s2 = Arrays.stream(split);
                  return s2;
                })
            .collect(Collectors.toList());
    System.out.println("处理前的集合：" + list);
    System.out.println("处理后的集合：" + listNew);
  }

  /***
   * mapToLong api 配合 boxed()
   * 调用 LongStream.boxed 方法收集为 Stream<Long> 类型；
   */
  @Test
  public void testMapToLong() {
    List<String> numbers = Arrays.asList("22", "19", "89", "90");
    List<Long> results =
        numbers.stream().mapToLong(Long::valueOf).boxed().collect(Collectors.toList());
    log.info("result:{}", results);
  }

  /**
   * map 适用于对每个元素进行简单的转换，
   *
   * <p>flatMap 适用于对数组流进行平铺后合并，两个方法的应用场景不一样。
   */
  @Test
  public void testFlatMap2() {
    String[] arr1 = {"https://", "www", ".", "javastack", ".", "cn"};
    String[] arr2 = {"公众号", ":", "Java技术栈"};
    String[] arr3 = {"作者", ":", "栈长"};
    System.out.println("=====arrays list=====");
    List<String[]> result1 = Stream.of(arr1, arr2, arr3).collect(Collectors.toList());
    System.out.println(result1);

    System.out.println("=====flatMap list=====");
    List<String> result2 =
        Stream.of(arr1, arr2, arr3).flatMap(Arrays::stream).collect(Collectors.toList());
    System.out.println(result2);
  }

  @Test
  public void testFlatMap() {
    Map<String, Person> map = new HashMap<>();

    List<String> collect =
        personList().stream()
            .flatMap(
                m -> {
                  String[] split = m.getFlag().split(",");
                  return Arrays.stream(split);
                })
            .collect(Collectors.toList());

    // int value =
    // personList().stream().collect(summingInt(Person::getSalary)).intValue();
    // System.out.println(value);
  }

  @Test
  public void testFlatMap1() {
    Map<String, Person> personMap = new HashMap<>();
    personMap.entrySet().stream().forEach(f -> System.out.println(f.getKey() + " " + f.getValue()));
  }

  /** 归约（reduce) :是把一个流缩减成一个值，能实现对集合求和、求乘积和求最值 */
  @Test
  public void test7() {
    List<Integer> list = Arrays.asList(1, 3, 2, 8, 11, 4);
    // 求和方式一
    Optional<Integer> sum = list.stream().reduce((x, y) -> x + y);
    // 求和方式二
    Optional<Integer> sum2 = list.stream().reduce(Integer::sum);
    // 求和方式三
    Integer sum3 = list.stream().reduce(0, Integer::sum);

    // 求乘积
    Optional<Integer> product = list.stream().reduce((x, y) -> x * y);

    // 求最大值方式一
    Optional<Integer> max = list.stream().reduce((x, y) -> x > y ? x : y);

    // 求最大值方式二
    Integer max2 = list.stream().reduce(0, Integer::max);

    Optional<Integer> max1 = list.stream().reduce(Integer::max);

    System.out.println("list求和：" + sum.get() + "," + sum2.get() + "," + sum3);
    System.out.println("list求积：" + product.get());
    System.out.println("list求和：" + max.get() + "," + max2 + "," + max1.get());
  }

  @Test
  public void test8() {
    Optional<Integer> sum1 = personList().stream().map(Person::getSalary).reduce(Integer::sum);

    Integer sum2 = personList().stream().reduce(0, (sum, p) -> sum += p.getSalary(), Integer::sum);
  }

  /** toMap使用 返回指定唯一key值的map,如果key重复，取最后一个key的值,(o1,o2)->o2) */
  @Test
  public void test9() {

    Map<String, Person> personMap =
        personList().stream()
            .filter(p -> p.getSalary() > 8000)
            .collect(Collectors.toMap(Person::getName, p -> p, (o1, o2) -> o2));
    System.out.println(personMap);
  }

  /**
   * 分组(partitioningBy/groupingBy)
   *
   * <p>将员工按薪资是否高于8000分为两部分；将员工按性别和地区分组
   */
  @Test
  public void test10() {
    // 将员工按照薪资是否高于8000分组
    Map<Boolean, List<Person>> part =
        personList().stream().collect(Collectors.partitioningBy(x -> x.getSalary() > 8000));

    // 将员工按性别分组
    Map<String, List<Person>> group =
        personList().stream()
            .sorted(Comparator.comparing(Person::getCreateTime).reversed())
            .collect(Collectors.groupingBy(Person::getSex));

    //
    Map<String, List<Person>> group3 =
        personList().stream()
            .sorted((t1, t2) -> t2.getCreateTime().compareTo(t1.getCreateTime()))
            .collect(Collectors.groupingBy(Person::getSex));

    // 将员工按照性别分组，再按照地区分组
    Map<String, Map<String, List<Person>>> group2 =
        personList().stream()
            .collect(Collectors.groupingBy(Person::getSex, Collectors.groupingBy(Person::getArea)));

    // System.out.println("员工按薪资是否大于8000分组情况：" + part);
    System.out.println("员工按性别分组情况：" + group);
    // System.out.println("员工按性别、地区：" + group2);
  }

  /** joining() */
  @Test
  public void test11() {

    String collect = personList().stream().map(m -> m.getName()).collect(Collectors.joining(","));

    String collect1 = personList().stream().map(m -> m.getName()).collect(Collectors.joining("-"));
    System.out.println("所有员工的姓名:" + collect);
    System.out.println("所有员工的姓名拼接:" + collect1);
  }

  /** sorted 排序 */
  @Test
  public void test12() {
    // 默认升序
    List<Person> list =
        personList().stream()
            .sorted(Comparator.comparing(Person::getSalary))
            .collect(Collectors.toList());
    System.out.println("按工资升序排序：" + list);

    List<Person> list2 =
        personList().stream()
            .sorted(Comparator.comparing(Person::getSalary).reversed())
            .collect(Collectors.toList());
    System.out.println("按工资降序排序：" + list2);

    List<Person> list3 =
        personList().stream()
            .sorted(Comparator.comparing(Person::getSalary).thenComparing(Person::getAge))
            .collect(Collectors.toList());
    System.out.println("先按照薪资再按照年龄升序排序：" + list3);
  }

  @Test
  public void test13() {
    String[] arr1 = {"a", "b", "c", "d"};
    String[] arr2 = {"d", "e", "f", "g"};

    Stream<String> stream1 = Stream.of(arr1);
    Stream<String> stream2 = Stream.of(arr2);

    // concat 合并两个流，distinct：去重
    List<String> list = Stream.concat(stream1, stream2).distinct().collect(Collectors.toList());

    List<Integer> list2 = Stream.iterate(1, x -> x + 2).limit(10).collect(Collectors.toList());

    List<Integer> list3 =
        Stream.iterate(1, x -> x + 2).skip(1).limit(5).collect(Collectors.toList());

    System.out.println("流合并：" + list);
    System.out.println("limit：" + list2);
    System.out.println("skip：" + list3);
  }

  @Test
  public void test14() {
    List<Person> people = personList();
    int size = people.size();
    int index = 0;
    while (size > 1 && index + 1 < size) {
      System.out.println(index);
      System.out.println(people.get(index));
      System.out.println(people.get(index + 1));
      index++;
    }
  }

  @Test
  public void test15() {
    List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
    boolean ordered = Ordering.from(Comparator.comparing(Integer::intValue)).isOrdered(list);
    System.out.println(ordered);
  }

  @Test
  public void test16() {
    List<Person> peoples = Lists.newArrayList();
    Person person1 = new Person("ww");
    peoples.add(person1);
    Person person2 = new Person("yy");
    peoples.add(person2);
    Person person3 = new Person("zz");
    peoples.add(person3);

    peoples.removeIf(o -> o.getName().equals("zz"));
    System.out.println(peoples);
  }

  @Test
  public void test17() {
    Set<Integer> set1 = new HashSet<>();

    List<Person> peoples = Lists.newArrayList();

    peoples.stream().filter(f -> set1.contains(f.getAge())).collect(Collectors.toList());
  }

  @Test
  public void test18() {
    List<Person> personList = Lists.newArrayList();
    personList.add(new Person("Tom", 8900, 20, "male", "New York", "2021-06-01", "1,2"));
    personList.add(new Person("Jack", 7000, 30, "male", "Washington", "2021-05-01", "1,2,3,4"));
    personList.add(new Person("Lily", 7800, 40, "female", "Washington", "2021-03-01", "1,2,5"));

    personList.stream().filter(f -> f.getAge() > 31).forEach(t -> t.setName("test"));

    System.out.println(personList);
  }

  @Test
  public void test19() {
    List<Person> list = null;
    if (CollectionUtils.isEmpty(list)) {
      list = Collections.emptyList();
    }

    if (CollectionUtils.isEmpty(list)) {
      list.add(new Person("xxx"));
    }

    System.out.println(list);

    /*
     * List<Person> collect = list.stream().filter(f ->
     * StringUtils.isBlank(f.getName())).collect(Collectors.toList());
     * System.out.println(collect);
     */
  }

  @Test
  public void testMap2() {
    List<Person> str = Lists.newArrayList();
    Map<String, String> stringMap =
        str.stream().collect(Collectors.toMap(Person::getName, Person::getArea));
    // Map<String,String> stringMap = new HashMap<>();

    // stringMap.put("11","22");

    Map<String, String> stringMap2 = new HashMap<>();

    stringMap2.put("11-333", "22");
    stringMap2.put("11", "33");

    stringMap.putAll(stringMap2);

    System.out.println(stringMap);
  }

  @Test
  public void testRetainAll() {
    List<String> list1 = Lists.newArrayList();
    list1.add("a");
    list1.add("b");
    list1.add("c");
    List<String> list2 = Lists.newArrayList();
    list2.add("a");
    list2.add("b");
    list2.add("d");
    list1.retainAll(list2);

    System.out.println(list1); // 输出[a, b]

    long count = 2;
    int a = 1;
    if (count == a) {
      System.out.println("==");
    } else {
      System.out.println("!=");
    }
  }

  @Test
  public void testStreamOf() {
    String header = "123 222222222";
    header = header.replace("123 ", "");
    System.out.println(header);
  }

  @Test
  public void testSb() {

    List<String> result1 = Lists.newArrayList("a", "b");

    List<String> result2 = Lists.newArrayList("a");

    List<String> list = Lists.newArrayList();
    String collect1 = result1.stream().map(m -> testJoin(m)).collect(Collectors.joining(";"));
    System.out.println(collect1);

    String collect2 = result2.stream().map(m -> testJoin(m)).collect(Collectors.joining(";"));

    list.add(collect1);
    list.add(collect2);

    String collect = list.stream().collect(Collectors.joining(";"));
    System.out.println(collect);
  }

  private String testJoin(String m) {
    return String.join(",", m, "11", "22", "333");
  }

  @Test
  public void testMap() {
    Map<String, Integer> map = new HashMap<>();
    map.put("2021-08-01", 2);
    map.put("2021-08-02", 3);
    map.put("2021-08-03", 18);

    map.putIfAbsent("2021-08-01", 24);
    List<String> list1 = Lists.newArrayList("11", "22", "33", "22");
    List<String> list = Lists.newArrayList();

    list.addAll(list1);
    List<String> collect = list.stream().distinct().collect(Collectors.toList());

    System.out.println(collect);
  }

  @Test
  public void testFlatMap3() {

    List<PersonResponse> persons = com.google.common.collect.Lists.newArrayList();

    List<Support> supports = com.google.common.collect.Lists.newArrayList();
    Support support = new Support();
    support.setStartDate("2021-09-13");
    support.setEndDate("2021-09-19");
    support.setSupportUnitId("7f95ee30-869f-4880-9774-69b29fc260aa");
    supports.add(support);

    Support support1 = new Support();
    support1.setStartDate("2021-09-20");
    support1.setEndDate("2021-09-26");
    support1.setSupportUnitId("11111111111-222222222");
    supports.add(support1);

    PersonResponse personResponse = new PersonResponse();
    personResponse.setPersonId("b50d1bdc-5e26-43b4-ac8e-ddf6807286e6");
    personResponse.setSupportList(supports);
    persons.add(personResponse);

    Map<String, String> result =
        persons.stream()
            .filter(
                f -> org.apache.commons.collections4.CollectionUtils.isNotEmpty(f.getSupportList()))
            .flatMap(
                m ->
                    m.getSupportList().stream()
                        .flatMap(s -> getSupports(s, m.getPersonId()).stream()))
            .collect(
                Collectors.toMap(
                    k -> String.join(",", k.getPersonId(), k.getSupportDate()),
                    PersonSupportVO::getSupportUnitId));

    System.out.println(result);
  }

  private static List<PersonSupportVO> getSupports(Support s, String personId) {
    List<PersonSupportVO> personSupports = com.google.common.collect.Lists.newArrayList();

    LocalDate startLocalDate = LocalDate.of(2021, 8, 01); // DateKits.toLocalDate(s.getStartDate());
    LocalDate endLocalDate = LocalDate.of(2021, 8, 10); // DateKits.toLocalDate(s.getEndDate());

    while (startLocalDate.compareTo(endLocalDate) <= 0) {
      PersonSupportVO personSupportVO =
          PersonSupportVO.builder()
              .personId(personId)
              // DateKits.format(startLocalDate)
              .supportDate("ssss")
              .supportUnitId(s.getSupportUnitId())
              .build();
      personSupports.add(personSupportVO);
      startLocalDate = startLocalDate.plusDays(1);
    }
    return personSupports;
  }

  @Test
  public void testList() {

    List<SecondChild> seconds = Lists.newArrayList();
    seconds.add(
        SecondChild.builder()
            .name("s_name_0")
            .code("s_code_0")
            .ids(Lists.newArrayList("a", "b"))
            .build());
    seconds.add(
        SecondChild.builder()
            .name("s_name_1")
            .code("s_code_1")
            .ids(Lists.newArrayList("c", "d"))
            .build());
    seconds.add(SecondChild.builder().name("s_name_2").code("s_code_2").build());

    List<FirstChild> firsts = Lists.newArrayList();
    firsts.add(FirstChild.builder().name("f_name_0").seconds(seconds).build());

    List<Group> groups = Lists.newArrayList();
    groups.add(Group.builder().name("name").code("code").firsts(firsts).build());

    List<String> ids =
        groups.stream()
            .filter(f -> !CollectionUtils.isEmpty(f.getFirsts()))
            .flatMap(
                g ->
                    g.getFirsts().stream()
                        .filter(f -> !CollectionUtils.isEmpty(f.getSeconds()))
                        .flatMap(
                            s ->
                                s.getSeconds().stream()
                                    .filter(f -> !CollectionUtils.isEmpty(f.getIds()))
                                    .flatMap(o -> o.getIds().stream())))
            .collect(Collectors.toList());
    System.out.println(ids);

    Optional.ofNullable(groups).orElse(Collections.emptyList()).stream()
        .filter(f -> !CollectionUtils.isEmpty(f.getFirsts()))
        .map(Group::getFirsts)
        .flatMap(List::stream)
        .filter(c -> !CollectionUtils.isEmpty(c.getSeconds()))
        .flatMap(f -> f.getSeconds().stream())
        .forEach(f -> f.setName(f.getName() + "_1111"));

    System.out.println(groups);
  }

  @Test
  public void testListNull() {
    String a = "2021-09-02";
    String b = "2021-09-03";

    System.out.println(a.compareTo(b)); // a < = b 时 a.compareTo(b) <= 0
    if (a.compareTo(b) == -1) {
      System.out.println("1");
    } else { // a<=b
      System.out.println("2");
    }
  }

  @Test
  public void testNull() {
    List<Person> persons = Lists.newArrayList();

    Map<String, Person> nameMap =
        Optional.ofNullable(persons).get().stream()
            .collect(Collectors.toMap(Person::getName, Function.identity()));

    Person person = nameMap.get("11");
    System.out.println(person);
  }

  @Test
  public void test33() {

    List<SecondChild> seconds = Lists.newArrayList();
    seconds.add(
        SecondChild.builder()
            .name("s_name_0")
            .code("s_code_0")
            .ids(Lists.newArrayList("a", "b"))
            .build());
    seconds.add(
        SecondChild.builder()
            .name("s_name_1")
            .code("s_code_1")
            .ids(Lists.newArrayList("c", "d"))
            .build());
  }

  @Test
  public void testSys() {
    List<String> names = Lists.newArrayList();
    List<Person> persons = Lists.newArrayList();
    Optional<Person> personOptional =
        persons.stream().filter(p -> names.contains(p.getName())).findAny();

    String date = "2021-01-30";
    if (date.matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$")) {
      System.out.println("11");
    }
  }

  @Test
  public void testSplice() {
    List<Person> persons = personList();
    persons.parallelStream()
        .forEach(
            index -> {
              System.out.println(Thread.currentThread().getName() + " >> " + index);
            });
  }

  @Test
  public void testParallelStream() {
    // list1
    List<Person> persons = personList();
    // list2 -> 根据唯一键 组成Map
    List<User> users = users();
    Map<Integer, User> userMap =
        users.stream().collect(Collectors.toMap(User::getAge, Function.identity()));

    // 先过滤一便，用parallelStream 处理 映射成新的 对象
    List<Employee> employees =
        persons.parallelStream()
            .filter(f -> userMap.keySet().contains(f.getAge()))
            .map(
                m -> {
                  User user = userMap.get(m.getAge());
                  return Employee.builder().code(user.getCode()).name(m.getName()).build();
                })
            .collect(Collectors.toList());
    System.out.println(employees);
  }

  @Test
  public void testParallelStream2() {}

  @Test
  public void testSort3() {
    List<Blog> blogs = blogs();
    // 未组装前的数据
    List<Blog> collect =
        blogs.stream()
            .sorted(Comparator.comparing(Blog::getCode).thenComparing(Blog::getCurrentCode))
            .collect(Collectors.toList());
    // 去重之后的数据
    Set<String> codes =
        blogs.stream()
            .flatMap(f -> Stream.of(f.getCode(), f.getCurrentCode()))
            .collect(Collectors.toSet());

    // 归纳
    Map<String, List<String>> maps = Maps.newHashMap();

    List<String> beforeCodes = blogs.stream().map(Blog::getCode).collect(Collectors.toList());
    List<String> currentCodes =
        blogs.stream().map(Blog::getCurrentCode).collect(Collectors.toList());

    codes.forEach(
        f -> {
          List<Blog> blogs1 =
              collect.stream()
                  .filter(
                      c ->
                          f.equalsIgnoreCase(c.getCode()) || f.equalsIgnoreCase(c.getCurrentCode()))
                  .collect(Collectors.toList());
          Set<String> collect1 =
              blogs1.stream()
                  .flatMap(f1 -> Stream.of(f1.getCode(), f1.getCurrentCode()))
                  .collect(Collectors.toSet());
          collect1.remove(f);
          maps.put(f, Lists.newArrayList(collect1));
        });

    System.out.println(maps);
  }

  @Test
  public void testMap13() {
    List<Blog> blogs = blogs();
    List<Blog> currentBlogs =
        blogs.stream().filter(f -> f.getCode().equals("D")).collect(Collectors.toList());

    currentBlogs.forEach(o -> o.setCurrentCode("DD"));

    System.out.println(currentBlogs);
  }

  @Test
  public void testMapRefactor12() {
    List<Blog> blogs = blogs();
    Map<String, Long> longMap =
        blogs.stream()
            .flatMap(f -> Stream.of(f.getCode(), f.getCurrentCode()))
            .collect(Collectors.groupingBy(k -> k, Collectors.counting()));

    Map<String, List<String>> result = new HashMap<>();

    for (Map.Entry<String, Long> entry : longMap.entrySet()) {
      String key = entry.getKey();
      Long value = entry.getValue();
      if (Objects.nonNull(value) && value > 1) {
        List<String> values = Lists.newArrayList();
        blogs.forEach(
            b -> {
              if (key.equals(b.getCode())) {
                values.add(b.getCurrentCode());
              }
              if (key.equals(b.getCurrentCode())) {
                values.add(b.getCode());
              }
            });
        result.put(key, values);
      }
    }

    blogs.stream()
        .filter(
            c ->
                CollectionUtils.isEmpty(result.get(c.getCode()))
                    && CollectionUtils.isEmpty(result.get(c.getCurrentCode())))
        .forEach(f -> result.put(f.getCode(), Lists.newArrayList(f.getCurrentCode())));

    System.out.println(result);
  }
  /**
   * 2. 判断 1:1 N:1 1:N 关系 A B
   *
   * <p>Y C H Y
   *
   * <p>D E D F D G
   *
   * <p>K I L I
   *
   * <p>先判断哪些是重复的，比如D I，抠出来，然后
   *
   * <p>先判断 mode
   */
  @Test
  public void testMap17() {
    int abs = Math.abs(-10);
    System.out.println(abs);
  }

  @Test
  public void testConMap() {
    Map<String, List<String>> map = new HashMap<>();
    map.put("key_1", Lists.newArrayList("a", "b"));
    map.put("key_2", Lists.newArrayList("c", "d", "e"));
  }

  /** 数组转换成集合 Arrays.asList() */
  @Test
  public void testArrayToList() {

    /** 基本数据类型的数组转换成集合 */
    int[] arr = {11, 22, 33, 44, 55};
    List<int[]> list = Arrays.asList(arr);
    System.out.println(list); // [[I@2a139a55]
    for (int[] ar : list) {
      for (int i = 0; i < ar.length; i++) {
        System.out.println(ar[i]);
      }

      /** 将引用类型转换成数组 */
      Integer[] integers = {2, 3, 5, 78, 8};
      List<Integer> results = Arrays.asList(integers);
      System.out.println(results);

      /** 由数组转换过来的集合，并不能添加或删除元素，但是可以用集合的其他方法 error：java.lang.UnsupportedOperationException */
      String[] str = {"a", "b", "c", "d", "e"};
      List<String> strs = Arrays.asList(str);
      strs.add("f");
      System.out.println(strs);
    }
  }

  /** 集合转换成 Collection接口的toArray方法 */
  @Test
  public void testCollectionToArray() {}

  @Test
  public void testContinue() {
    for (int i = 0; i < 5; i++) {
      if (i == 3) {
        continue;
      }
      System.out.println(i);
    }
  }

  @Test
  public void testBoxed() {
    List<Integer> list = Arrays.asList(1, 2, 3, 5, 6);
    // to Int Stream
    IntStream intStream = list.stream().mapToInt(m -> m);
    // to Stream Integer
    Stream<Integer> integerStream = intStream.boxed();

    List<Integer> collect = list.stream().collect(Collectors.toList());
  }

  @Test
  public void testIntegerStream() {
    List<Integer> collect = random.ints(0, 100).limit(10).boxed().collect(Collectors.toList());
    System.out.println(collect);
    AtomicInteger atomicInteger = new AtomicInteger();
  }

  /** 1. Map 以前没有添加过相同的键，则put()和putIfAbsent()方法都返回的是null 2. putIfAbsent -> onlyIfAbsent 3. put */
  @Test
  public void testMapPutApi() {
    Map<String, String> map = new HashMap<>();
    System.out.println(map.put("Tom", "11"));
    System.out.println(map.get("Tom"));

    System.out.println(map.put("Tom", "22"));
    System.out.println(map.get("Tom"));

    System.out.println(map.putIfAbsent("Jack", "55"));
    System.out.println(map.get("Jack"));

    System.out.println(map.putIfAbsent("Jack", "99"));
    System.out.println(map.get("Jack"));
  }

  /** 集合按照某个字段去重 */
  @Test
  public void testByFieldDistinct0() {
    List<Person> people = personList();
    ArrayList<Person> collect =
        people.stream()
            .collect(
                Collectors.collectingAndThen(
                    Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparing(Person::getName))),
                    ArrayList::new));

    System.out.println(collect);
  }

  @Test
  public void testByFieldDistinct1() {
    List<Person> people = personList();
    List<Person> collect =
        people.stream().filter(distinctByKey(f -> f.getName())).collect(Collectors.toList());

    System.out.println(collect);
  }

  @Test
  public void testByFieldDistinct2() {
    List<Person> people = personList();
    List<Person> collect =
        people.stream().filter(distinctByKey2(f -> f.getName())).collect(Collectors.toList());

    System.out.println(collect);
  }

  // 根据对象属性去重
  public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
    Map<Object, Boolean> seen = new ConcurrentHashMap<>();
    return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
  }

  public static <T> Predicate<T> distinctByKey2(Function<? super T, ?> key) {
    Set<Object> set = ConcurrentHashMap.newKeySet();
    return t -> set.add(key.apply(t));
  }

  @Test
  public void test90() {
    List<Person> persons = personList();
    Map<Integer, Set<Integer>> collect =
        persons.stream()
            .collect(
                Collectors.groupingBy(
                    Person::getAge,
                    Collectors.mapping(p -> getCreateTime(p.getCreateTime()), toSet())));

    System.out.println(collect);
  }

  private Integer getCreateTime(String createTime) {
    if (createTime.equals("2021-06-01")) {
      return 1;
    }
    return 2;
  }

  @Test
  public void test91() {
    List<String> dates =
        Lists.newArrayList("2021-12-01", "2021-12-03", "2021-12-02", "2021-12-09", "2021-12-08");

    List<String> currentDates = dates.stream().sorted().collect(Collectors.toList());

  }
}
