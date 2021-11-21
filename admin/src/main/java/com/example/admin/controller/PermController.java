package com.example.admin.controller;

import com.example.admin.commons.annotation.PermissionOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@link PermController}
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-04-12
 */
@Slf4j
@RestController
@RequestMapping("/perm")
public class PermController {

    @GetMapping("/list")
    @PermissionOperation(code = "perm:list", describe = "查询")
    public void list() {

    }

    @PostMapping("/edit")
    @PermissionOperation(code = "perm:edit", describe = "编辑")
    public void edit() {

    }
}
