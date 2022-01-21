package com.example.jpamultitenancy.tenant.repository;

import com.example.jpamultitenancy.tenant.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName: GroupRepository @Author: amy @Description: GroupRepository @Date:
 *             2021/5/28 @Version: 1.0
 */
public interface GroupRepository extends JpaRepository<Group, Long> {

	Page<Group> findByNameContaining(String name, Pageable pageable);
}
