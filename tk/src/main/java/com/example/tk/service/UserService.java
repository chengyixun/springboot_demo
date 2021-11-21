package com.example.tk.service;

import com.example.tk.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName: UserService
 * @Author: amy
 * @Description: UserService
 * @Date: 2021/8/12
 * @Version: 1.0
 */
public interface UserService {

	void save(User user);

	void update(User user);

	List<User> list();

	void importExcel(MultipartFile file);

	void easyImportExcel(MultipartFile file);

}
