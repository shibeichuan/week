package com.shibeichuan.test;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shibeichuan.common.utils.UserUtils;
import com.shibeichuan.pojo.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:redis.xml")
public class UserTest {

	@Autowired
	RedisTemplate redisTemplate;

	@Test
	public void testJDK() {
		// 生成user对象
		ArrayList<User> list = new ArrayList<User>();
		for (int i = 0; i < 100000; i++) {
			User user = new User();
			user.setId(i + 1);
			user.setName(UserUtils.getName());
			user.setSex(UserUtils.getSex());
			user.setPhone(UserUtils.getPhone());
			user.setEmail(UserUtils.getMail());
			user.setBirthday(UserUtils.getBirthday());
			list.add(user);
		}
		// 记录开始时间
		long start = System.currentTimeMillis();
		// 存入对象
		redisTemplate.opsForList().leftPushAll("user", list.toArray());
		// 记录结束时间
		long end = System.currentTimeMillis();
		System.out.println("序列化方式为JDK");
		System.out.println("保存数量为10w");
		System.out.println("所耗时间为:" + (end - start) + "毫秒");
	}

	@Test
	public void testJSON() {
		// 生成user对象
		ArrayList<User> list = new ArrayList<User>();
		for (int i = 0; i < 100000; i++) {
			User user = new User();
			user.setId(i + 1);
			user.setName(UserUtils.getName());
			user.setSex(UserUtils.getSex());
			user.setPhone(UserUtils.getPhone());
			user.setEmail(UserUtils.getMail());
			user.setBirthday(UserUtils.getBirthday());
			list.add(user);
		}
		// 记录开始时间
		long start = System.currentTimeMillis();
		// 存入对象
		redisTemplate.opsForList().leftPushAll("users", list.toArray());
		// 记录结束时间
		long end = System.currentTimeMillis();
		System.out.println("序列化方式为JSON");
		System.out.println("保存数量为10w");
		System.out.println("所耗时间为:" + (end - start) + "毫秒");
	}

	@Test
	public void testHash() {
		HashMap<String,String> map = new HashMap<String, String>();
		for (int i = 0; i < 100000; i++) {
			User user = new User();
			user.setId(i + 1);
			user.setName(UserUtils.getName());
			user.setSex(UserUtils.getSex());
			user.setPhone(UserUtils.getPhone());
			user.setEmail(UserUtils.getMail());
			user.setBirthday(UserUtils.getBirthday());
			map.put(user.getId()+"",user.toString());
		}
		// 记录开始时间
		long start = System.currentTimeMillis();
		redisTemplate.opsForHash().putAll("user_map", map);
		long end = System.currentTimeMillis();
		System.out.println("序列化方式为Hash");
		System.out.println("保存数量为10w");
		System.out.println("所耗时间为:" + (end - start) + "毫秒");
	}
}
