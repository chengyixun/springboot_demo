package com.example.admin.controller;

import com.example.admin.entity.AuthUser;
import com.example.admin.entity.Menu;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * {@link AuthUserController}
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-04-09
 */
@RestController
@RequestMapping("/auth")
public class AuthUserController {

    @GetMapping("/get")
    public AuthUser authUser() {
        AuthUser authUser = new AuthUser();
        authUser.setId("111");
        return authUser;
    }
    @GetMapping("/users")
    public List<AuthUser> authUser2() {
        AuthUser authUser = new AuthUser();
        authUser.setId("111");
        return Lists.newArrayList(authUser);
    }

    @GetMapping("/menu")
    public Menu menu() {
        Menu menu = new Menu();
        menu.setId("111");
        return menu;
    }

    @GetMapping("/menus")
    public List<Menu> list() {
        Menu menu = new Menu();
        menu.setId("111");
        return Lists.newArrayList(menu);
    }
}
