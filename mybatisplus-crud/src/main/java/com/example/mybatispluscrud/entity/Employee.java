package com.example.mybatispluscrud.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * {@link Employee}
 *
 * @author Liyaohui
 * @date 8/29/21
 */
@Data
@TableName(value = "biz_employee")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	@TableField(condition = SqlCondition.LIKE_RIGHT)
	private String name;

	private Integer age;

	private String email;

	private Long managerId;

	private String operateDate;

	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;

	/**
	 * 在读多写少的场景下，乐观锁比较适用，能够减少加锁操作导致的性能开销，提高系统吞吐量
	 *
	 * <p>
	 * 在写多读少的场景下，悲观锁比较使用，否则会因为乐观锁不断失败重试，反而导致性能下降。
	 */
	@Version
	private Integer version;

	/**
	 * 若要对某些表进行单独配置，在实体类的对应字段上使用@TableLogic @TableLogic(value = "0", delval = "1")
	 *
	 * <p>
	 * 若想要SELECT的列，不包括逻辑删除的那一列，则可以在实体类中通过@TableField进行配置
	 */
	private Integer deleted;
}
