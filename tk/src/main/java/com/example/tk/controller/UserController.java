package com.example.tk.controller;

import com.example.tk.common.util.EasyExcelUtil;
import com.example.tk.common.util.ExcelUtil;
import com.example.tk.entity.User;
import com.example.tk.service.UserService;
import com.example.tk.vo.UserTempVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: UserController
 * @Author: amy
 * @Description: UserController
 * @Date: 2021/8/12
 * @Version: 1.0
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/insert")
	public void insert(@RequestBody User user) {
		userService.save(user);
	}

	@PostMapping("/update")
	public void update(@RequestBody User user) {
		userService.update(user);
	}

	@GetMapping
	public List<User> list() {
		return userService.list();
	}

	@PostMapping("/import")
	public void importExcel(@RequestParam("file") MultipartFile file) {
		userService.importExcel(file);
	}

	@PostMapping("/import1")
	public void easyImportExcel(@RequestParam("file") MultipartFile file) {
		userService.importExcel(file);
	}

	@GetMapping("/export1")
	public void easyImportExcel(String fileName, HttpServletResponse response) {
		List<UserTempVO> userTemps = new ArrayList<>();
		userTemps.add(UserTempVO.builder().name("nacy").age(21L).sex("男").build());
		userTemps.add(UserTempVO.builder().name("lily").age(33L).sex("女").build());
		EasyExcelUtil.exportWeb(response, "测试", "测试", UserTempVO.class, userTemps);
	}

	@GetMapping("/exportTemplate")
	public void easyImportExcel(HttpServletResponse response, String inFileName, String outFileNam) {
		EasyExcelUtil.downloadExcel(response, inFileName, outFileNam);
	}

	@GetMapping("/export")
	public void importExcel(String fileName, HttpServletResponse response) {

		Map<String, List<String>> dataList = new HashMap<>();
		for (int i = 0; i < 2; i++) {
			List<String> values = new ArrayList<>();
			values.add("name" + i);
			values.add("sex" + i);
			dataList.put(String.valueOf(i + 1), values);
		}

		List<String> headers = new ArrayList<>();
		headers.add("header1");
		headers.add("header2");

		ExcelUtil.writeExcel(response, dataList, headers, fileName);
	}

}
