/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.example.jpacrud.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Zheng Jie
 * @date 2018-12-17
 */
@Data
@Entity
@Table(name = "sys_menu")
public class Menu extends BaseEntity {

	@Id
	@Column(name = "menu_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/*
	 * @JSONField(serialize = false)
	 * 
	 * @ManyToMany(mappedBy = "menus",fetch = FetchType.LAZY) private Set<Role>
	 * roles;
	 */

	private String title;

	private String name;

	private Integer menuSort = 999;

	private String component;

	private String path;

	private Integer type;

	private String permission;

	private String icon;

	@Column(columnDefinition = "bit(1) default 0")
	private Boolean cache;

	@Column(columnDefinition = "bit(1) default 0")
	private Boolean hidden;

	private Long pid;

	private Integer subCount = 0;

	private Boolean iFrame;
}
