package com.gordeev.onlinestore.dao.jdbc;

import com.gordeev.onlinestore.entity.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class JdbcUserDaoTest {

    @Test
    public void getByName() {
        User expectedUser = new User("admin","admin");
        JdbcUserDao jdbcUserDao = new JdbcUserDao();
        User actualUser = jdbcUserDao.getByName("name");

        assertNotNull(actualUser);
        assertEquals(expectedUser.getUsername(),actualUser.getUsername());
        assertEquals(expectedUser.getPassword(),actualUser.getPassword());
    }
}