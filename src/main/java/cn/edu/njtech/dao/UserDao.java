package cn.edu.njtech.dao;

import cn.edu.njtech.entity.User;

import java.util.List;

/**
 * @author tim
 * @date 2022/9/9 8:21 下午
 */
public interface UserDao {
    List<User> findAll();

    List<User> findByName(String name);
}
