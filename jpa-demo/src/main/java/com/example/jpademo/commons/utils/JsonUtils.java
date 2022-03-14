package com.example.jpademo.commons.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/** @ClassName: JsonUtils @Author: amy @Description: JsonUtls @Date: 2022/3/11 @Version: 1.0 */
//@Component
@Slf4j
public class JsonUtils {

  @Autowired private Jackson2ObjectMapperBuilder jacksonBuilder;

  private static ObjectMapper mapper;
  //	private static final ObjectMapper mapper = new ObjectMapper();

  public static final String IS_COMPRESSED_KEY = "isCompressed";
  public static final String COMPRESSED_CONTENT_KEY = "content";

//  @PostConstruct
  public void init() {
    mapper = jacksonBuilder.build();
  }

  public static ObjectNode object() {
    return JsonNodeFactory.instance.objectNode();
  }

  /**
   * 对象转Json String
   *
   * @param obj
   * @return
   */
  public static JsonNode toJsonNode(Object obj) {
    return mapper.valueToTree(obj);
  }

  public static List<String> keys(JsonNode jsonNode) {
    Iterable<String> iter = () -> jsonNode.fieldNames();
    return StreamSupport.stream(iter.spliterator(), false).collect(Collectors.toList());
  }

  /**
   * jsonNode 转成String
   *
   * @param jsonNode
   * @return
   * @throws JsonProcessingException
   */
  public static String format(JsonNode jsonNode) throws JsonProcessingException {
    return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
  }

  public static String format(Object value) throws JsonProcessingException {
    return mapper.writeValueAsString(value);
  }
}
