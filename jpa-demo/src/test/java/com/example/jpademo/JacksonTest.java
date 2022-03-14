package com.example.jpademo;

import com.example.jpademo.entity.Address;
import com.example.jpademo.entity.Dept;
import com.example.jpademo.entity.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Date;

/**
 * @ClassName: JsonNodeTest @Author: amy @Description: JsonNodeTest @Date: 2022/3/11 @Version: 1.0
 */
@Slf4j
public class JacksonTest {

  /**
   * 测试 @JsonUnwrapped注释可以在序列化和反序列化过程中使用解开值
   *
   * @throws JsonProcessingException
   */
  @Test
  public void testJsonUnwrapped() throws JsonProcessingException {

    Employee employee =
        Employee.builder()
            .name("呃呃")
            .dept(Dept.builder().name("部门A").build())
            .address(
                Address.builder()
                    .doorNumber("209")
                    .street("竹园路")
                    .pinCode("22")
                    .city("suzhou")
                    .build())
            .build();
    ObjectMapper mapper = new ObjectMapper();
    String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(employee);

    log.info("entity:{}", employee);
    log.info("jsonString:{}", jsonString);
  }

  @Test
  public void test1() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    Book book = Book.builder().price(1.3f).title("java基础").publish(new Date()).build();

    String json = objectMapper.writeValueAsString(book);
    log.info("对象序列化为json字符串:{}", json);

    Book book1 = objectMapper.readValue(json, Book.class);
    log.info("json字符串 反序列化为 POJO :{}", book1);
  }

  /**
   * JsonNode 节点对象 put 数据时，有 8 种基本数据类型以及 String、BigDecimal、BigInteger，但是没有 Date 类型 * 所以日期类型只能通过 Long
   * 长整型设置，但是转 POJO 对象时仍然会自动转为 Date 类型
   *
   * @throws IOException
   */
  @Test
  public void test2() throws IOException {

    ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
    objectNode.put("id", 1);
    objectNode.put("title", "java进阶");
    objectNode.put("publish", new Date().getTime());

    Book book = new ObjectMapper().treeToValue(objectNode, Book.class);
    log.info("book:{}", book);
  }

  @Test
  public void test3(){
    Book book = Book.builder().price(1.3f).title("java基础").publish(new Date()).build();
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.convertValue(book, JsonNode.class);
    log.info("将POJO转为 json节点:{}",jsonNode);
  }
}
