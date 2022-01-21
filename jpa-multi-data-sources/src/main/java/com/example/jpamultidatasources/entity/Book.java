package com.example.jpamultidatasources.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName: Book @Author: amy @Description: Book @Date: 2021/9/18 @Version:
 *             1.0
 */
@Entity
@Data
@Table(name = "biz_book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private String auther;
}
