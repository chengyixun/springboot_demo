package com.example.jpamultitenancy.tenant.service;

import com.example.jpamultitenancy.tenant.entity.Group;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: GroupService @Author: amy @Description: GroupService @Date:
 *             2021/5/28 @Version: 1.0
 */
public interface GroupService {

	List<Group> getGroups();

	Group getGroupById(Long id);

	Map<Long, Group> getAllGroup();
}
