package com.example.jpamultitenancy.common.util;

import com.example.jpamultitenancy.common.exception.Exceptions;
import com.example.jpamultitenancy.common.exception.ServerException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static com.example.jpamultitenancy.common.constant.Constants.CODE;
import static com.example.jpamultitenancy.common.constant.Constants.ERROR;
import static com.example.jpamultitenancy.common.constant.Constants.MESSAGE;
import static com.example.jpamultitenancy.common.constant.Constants.RESULT;
import static com.example.jpamultitenancy.common.constant.Constants.SUCCESS;

/** @ClassName: JsonUtil @Author: amy @Description: JsonUtil @Date: 2021/5/28 @Version: 1.0 */
@Component
public class JsonUtils {

  @Autowired private Jackson2ObjectMapperBuilder jacksonBuilder;

  private ObjectMapper mapper;

  public static final String IS_COMPRESSED_KEY = "isCompressed";
  public static final String COMPRESSED_CONTENT_KEY = "content";

  private static JsonUtils INSTANCE;

  @PostConstruct
  public void init() {
    this.mapper = jacksonBuilder.build();
    INSTANCE = this;
  }

  public static ObjectNode object() {
    return JsonNodeFactory.instance.objectNode();
  }

  public static JsonNode from(File file) {
    try {
      return INSTANCE.mapper.readTree(file);
    } catch (IOException e) {
      throw new ServerException(e, Exceptions.Code.IO_ERROR);
    }
  }

  public static JsonNode from(String content, boolean... isCompressed) {
    try {
      if (isCompressed.length > 0 && isCompressed[0]) {
        String s = GZipUtils.unzip(content);
        return INSTANCE.mapper.readTree(s);
      }
      return INSTANCE.mapper.readTree(content);
    } catch (IOException e) {
      throw new ServiceException(Exceptions.Code.IO_ERROR, e);
    }
  }

  public static JsonNode from(InputStream is) {
    try {
      return INSTANCE.mapper.readTree(is);
    } catch (IOException e) {
      throw new ServiceException(Exceptions.Code.IO_ERROR, e);
    }
  }

  public static void save(JsonNode json, File file) {
    try {
      INSTANCE.mapper.writeValue(file, json);
    } catch (IOException e) {
      throw new ServiceException(Exceptions.Code.IO_ERROR, e);
    }
  }

  public static <T> T to(JsonNode json, Class<T> clz) {
    return INSTANCE.mapper.convertValue(json, clz);
  }

  public static <T> List<T> toList(JsonNode json, Class<T> clz) {
    Class<?> arrayType = Array.newInstance(clz, 0).getClass();
    T[] array = (T[]) INSTANCE.mapper.convertValue(json, arrayType);
    return Arrays.asList(array);
  }

  public static <T> List<T> toList(Object o, Class<T> clz) {
    Class<?> arrayType = Array.newInstance(clz, 0).getClass();
    T[] array = (T[]) INSTANCE.mapper.convertValue(o, arrayType);
    return Arrays.asList(array);
  }

  public static JsonNode toJson(Object o) {
    return INSTANCE.mapper.valueToTree(o);
  }

  public static List<String> keys(JsonNode json) {
    List<String> fieldNames = new ArrayList<>();
    Iterator<String> iter = json.fieldNames();
    while (iter.hasNext()) {
      fieldNames.add(iter.next());
    }
    return fieldNames;
  }

  public static ObjectNode get(ObjectNode json, String... fieldNames) {
    ObjectNode o = object();
    for (String fieldName : fieldNames) {
      o.set(fieldName, json.get(fieldName).deepCopy());
    }
    return o;
  }

  public static ObjectNode sortKeys(ObjectNode json) {
    List<String> keys = keys(json);
    Collections.sort(keys);
    ObjectNode o = JsonUtils.object();
    keys.forEach(k -> o.set(k, json.get(k)));
    return o;
  }

  public static ObjectNode put(ObjectNode json, String fieldName, Object o) {
    json.set(fieldName, toJson(o));
    return json;
  }

  public static ObjectNode remove(ObjectNode json, String... fieldNames) {
    json.remove(Arrays.asList(fieldNames));
    return json;
  }

  public static ObjectNode renameField(ObjectNode json, String fieldName, String newName) {
    json.set(newName, json.get(fieldName));
    json.remove(fieldName);
    return json;
  }

  public static ObjectNode renameFields(ObjectNode json, Function<String, String> mapper) {
    List<String> fieldNames = new ArrayList<>();
    Iterator<String> iter = json.fieldNames();
    while (iter.hasNext()) {
      fieldNames.add(iter.next());
    }
    fieldNames.stream()
        .forEach(
            key -> {
              String name = mapper.apply(key);
              if (!name.equals(key)) {
                renameField(json, key, name);
              }
            });
    return json;
  }

  public static String format(JsonNode json) {
    try {
      return INSTANCE.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
    } catch (IOException e) {
      throw new ServiceException(Exceptions.Code.IO_ERROR, e);
    }
  }

  public static void addObject(JsonNode json, Object o) {
    if (json.isArray()) {
      ArrayNode array = (ArrayNode) json;
      array.add(toJson(o));
    }
  }

  public static String stringify(JsonNode json, boolean pretty, boolean compress) {
    try {
      String s = pretty ? format(json) : INSTANCE.mapper.writeValueAsString(json);
      return compress ? GZipUtils.zip(s) : s;
    } catch (IOException e) {
      throw new ServiceException(Exceptions.Code.IO_ERROR, e);
    }
  }

  public static boolean isCompressed(ObjectNode json) {
    if (json.has(IS_COMPRESSED_KEY)
        && json.has(COMPRESSED_CONTENT_KEY)
        && json.get(IS_COMPRESSED_KEY).asBoolean()) {
      return true;
    }
    return false;
  }

  public static ObjectNode compress(JsonNode json) {
    if (json.isObject() && isCompressed((ObjectNode) json)) {
      return (ObjectNode) json;
    }

    String content = stringify(json, false, true);
    return JsonUtils.object().put(COMPRESSED_CONTENT_KEY, content).put(IS_COMPRESSED_KEY, true);
  }

  public static JsonNode decompress(ObjectNode json) {
    if (isCompressed(json)) {
      String content = json.get(COMPRESSED_CONTENT_KEY).asText();
      return from(content, true);
    }
    return json;
  }

  public static MultiValueMap<String, String> toMultiValueMap(JsonNode json) {
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    Iterator<String> iter = json.fieldNames();
    while (iter.hasNext()) {
      String key = iter.next();
      JsonNode value = json.get(key);
      if (value.isArray()) {
        value.forEach(v -> map.add(key, v.asText()));
      } else {
        map.add(key, value.asText());
      }
    }
    return map;
  }

  public static JsonNode error(int code, String message) {
    ObjectNode result = JsonUtils.object().put(SUCCESS, false);
    ObjectNode error = JsonUtils.object().put(CODE, code);
    error.put(MESSAGE, message);
    result.set(ERROR, error);
    return result;
  }

  public static JsonNode success(Object obj) {
    ObjectNode result = JsonUtils.object().put(SUCCESS, true);
    result.set(RESULT, JsonUtils.toJson(obj));
    return result;
  }
}
