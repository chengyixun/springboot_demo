package com.example.jpademo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: OrganizationController
 * @Author: amy
 * @Description: OrganizationController
 * @Date: 2022/3/10
 * @Version: 1.0
 */
@RestController
@RequestMapping("/organization")
public class OrganizationController {


    @GetMapping("/inital")
    public void init(){


    }



}
