package com.example.admin.fun;

import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** @ClassName: ListTest @Author: amy @Description: ListTest @Date: 2021/6/3 @Version: 1.0 */
public class ListTest {

  public List<Person> personList() {
    List<Person> personList = Lists.newArrayList();
    personList.add(new Person("Tom"));
    personList.add(new Person("Jack"));
    personList.add(new Person("Lily"));
    personList.add(new Person("Anni"));
    personList.add(new Person("Owen"));
    personList.add(new Person("Alisa"));
    return personList;
  }

  @Test
  public void test1() {
    Set<Person> set1 = new HashSet<>();
    set1.add(new Person("Tom"));
    set1.add(new Person("Jack"));

    Set<Person> set2 = new HashSet<>();
    set2.add(new Person("Tom"));
    set2.add(new Person("Jack2"));

    set1.retainAll(set2); //

    System.out.println(set1);
  }

  @Test
  public void test13() {
    Set<String> set1 = new HashSet<>();
    //set1.add("11");
    //set1.add("22");

    Set<String> set2 = new HashSet<>();
    set2.add("33");
    set2.add("444");

    set1.retainAll(set2);
    if (!set1.isEmpty()) {
      System.out.println("111");
    }

    if (set1.isEmpty()) {
      System.out.println(set1);
    } else {
      set1.stream().forEach(m -> System.out.println(m));
    }
  }
}
