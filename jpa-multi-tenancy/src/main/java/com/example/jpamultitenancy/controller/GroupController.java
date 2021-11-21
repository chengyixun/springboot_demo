package com.example.jpamultitenancy.controller;

import com.example.jpamultitenancy.tenant.entity.Group;
import com.example.jpamultitenancy.tenant.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: GroupController @Author: amy @Description: GroupController @Date: 2021/5/28 @Version:
 * 1.0
 */
@RestController()
@RequestMapping("/biz/group")
public class GroupController {

  @Autowired private GroupService groupService;

  @GetMapping
  @PreAuthorize("@el.check('group:list')")
  public List<Group> list() {
    return groupService.getGroups();
  }
}
