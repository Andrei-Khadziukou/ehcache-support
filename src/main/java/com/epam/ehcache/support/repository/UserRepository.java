package com.epam.ehcache.support.repository;

import com.epam.ehcache.support.domain.User;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Date: 04/13/2016
 *
 * @author Andrei_Khadziukou
 */
@Repository
public class UserRepository {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public void insert(User user) {
        sqlSessionTemplate.insert("UserMapper.insert", user);
    }

    public List<User> find() {
        return sqlSessionTemplate.selectList("UserMapper.findAll");
    }

    public User find(String id) {
        return sqlSessionTemplate.selectOne("UserMapper.find", id);
    }

    public void update(User user) {
        sqlSessionTemplate.update("UserMapper.update", user);
    }

    public void delete(String id) {
        sqlSessionTemplate.delete("UserMapper.delete", id);
    }
}
