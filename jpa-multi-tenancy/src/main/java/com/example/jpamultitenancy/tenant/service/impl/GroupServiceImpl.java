package com.example.jpamultitenancy.tenant.service.impl;

import com.example.jpamultitenancy.common.exception.Exceptions;
import com.example.jpamultitenancy.tenant.entity.Group;
import com.example.jpamultitenancy.tenant.repository.GroupRepository;
import com.example.jpamultitenancy.tenant.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: GroupServiceImpl @Author: amy @Description: GroupServiceImpl @Date:
 * 2021/5/28 @Version: 1.0
 */
@Service
@Slf4j
public class GroupServiceImpl implements GroupService {

  @Autowired private GroupRepository groupRepository;

  @Override
  public List<Group> getGroups() {
    return groupRepository.findAll();
  }

  @Override
 // @Cacheable(cacheNames = "group", key = "#id")
  public Group getGroupById(Long id) {
    return groupRepository.findById(id).orElseThrow(() -> Exceptions.NOT_FOUND());
  }

  @Override
  //@Cacheable(cacheNames = "groupMap")
  public Map<Long, Group> getAllGroup() {
    return groupRepository.findAll().stream().collect(Collectors.toMap(Group::getId, o -> o));
  }
}
