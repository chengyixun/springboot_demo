package com.example.tk.service.impl;

import com.example.tk.common.util.ExcelUtil;
import com.example.tk.entity.User;
import com.example.tk.mapper.UserMapper;
import com.example.tk.service.UserService;
import com.example.tk.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.List;

/**
 * @ClassName: UserServiceImpl
 * @Author: amy
 * @Description: UserServiceImpl
 * @Date: 2021/8/12
 * @Version: 1.0
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;


	@Override
	public void save(User user) {
		userMapper.insertSelective(user);
		log.info("after insert user:{}", user);
	}

	@Override
	public void update(User user) {
		Example example = Example.builder(User.class).where(Sqls.custom().andEqualTo("id", user.getId())).build();
		int i = userMapper.updateByExampleSelective(user, example);
		log.info("update user:{}", i);
	}

	@Override
	public List<User> list() {
		return userMapper.selectAll();
	}


	@Override
	public void importExcel(MultipartFile file) {
		List<UserVO> users = ExcelUtil.readExcel(UserVO.class, file);
		log.info("excel import：{}",users);
	}

	@Override
	public void easyImportExcel(MultipartFile file) {

	}


}
