package com.example.md;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @ClassName: Book
 * @Author: amy
 * @Description: BooK
 *
 * @Date: 2021/8/6
 * @Version: 1.0
 */
@Data
@Document(collation = "book")
@Builder
public class Book {

    @Id
    private String id;

    private String name;

    private String code;

}
