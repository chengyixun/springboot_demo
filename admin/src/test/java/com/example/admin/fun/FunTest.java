package com.example.admin.fun;

import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

/**
 * {@link FunTest}
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-03-15
 */
public class FunTest {

  /** consumer接口是消费型接口，无返回值 */
  @Test
  public void testConsumer() {
    this.handlerConsumer(10000, (i) -> System.out.println(i));
  }

  public void handlerConsumer(Integer number, Consumer<Integer> consumer) {
    consumer.accept(number);
  }

  /** Supplier接口是供给型接口，有返回值 */
  @Test
  public void testSupplier() {
    List<Integer> numberList = this.getNumberList(10, () -> new Random().nextInt(100));
    numberList.forEach(System.out::println);
  }

  public List<Integer> getNumberList(int num, Supplier<Integer> supplier) {
    List<Integer> list = Lists.newArrayList();
    for (int i = 0; i < num; i++) {
      list.add(supplier.get());
    }
    return list;
  }

  /** Function接口是函数型接口，有返回值 */
  @Test
  public void testFunc() {
    String str = this.handlerString("binghe", s -> s.toUpperCase());
    System.out.println(str);
  }

  public String handlerString(String str, Function<String, String> func) {
    return func.apply(str);
  }

  /** Predicate接口是断言型接口，返回值类型为boolean */
  @Test
  public void testPredicate() {
    List<String> list = Arrays.asList("Hello", "Lambda", "binghe", "lyz", "World");
    list.stream().peek(t -> t.toLowerCase()).collect(toList());
    System.out.println(list);
    // List<String> result = this.handlerFilterStr(list, s -> s.length() >= 5);
    // result.forEach(System.out::println);
  }

  public List<String> handlerFilterStr(List<String> list, Predicate<String> predicate) {
    List<String> result = Lists.newArrayList();
    for (String s : list) {
      if (predicate.test(s)) {
        result.add(s);
      }
    }
    return result;
  }

  @Test
  public void testOptional() {
    Function<Integer, Integer[]> fun = (n) -> new Integer[n];
    // 等同于
    Function<Integer, Integer[]> fun1 = Integer[]::new;
  }

  @Test
  public void testSort() {
    String[] array = new String[] {"Apple", "Orange", "Banana", "Lemon"};
    Arrays.sort(array, String::compareTo);
    System.out.println(String.join(",", array));
  }

  @Test
  public void test2() {
    // List<String> -> List<Person>
    List<String> names = Lists.newArrayList("Bob", "Alice", "Tim");
    List<Person> peoples = names.stream().map(Person::new).collect(toList());
  }

  @Test
  public void test3() {
    List<String> props =
        Lists.newArrayList("profile=native", "debug=true", "logging=warn", "interval=500");
   /* Map<String, String> map =
        props.stream()
            .map(
                kv -> {
                  String[] split = kv.split("\\=", 2);
                  return new HashMap(split[0], split[1]);
                })
            .reduce(
                new HashMap(),
                (m, kv) -> {
                  m.putAll(kv);
                  return m;
                });*/
  }
}
