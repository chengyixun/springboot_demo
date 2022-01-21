package com.example.jpamultitenancy.tenant.entity;

import com.example.jpamultitenancy.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

/**
 * @ClassName: User @Author:amy @Description: User @Date: 2021/5/25 @Version:
 *             1.0
 */
@Builder
@Data
@Entity
@Table(name = "sys_user")
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column(nullable = false, unique = true)
	private String username;

	private String password;

	private String tenant;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "sys_users_roles", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "user_id") }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", referencedColumnName = "role_id") })
	private Set<Role> roles;

	/** 0：不是 1：是的 */
	private Boolean isSuperAdmin;
}
