package com.gordeev.onlinestore.dao.jdbc;

import com.gordeev.onlinestore.entity.User;
import com.gordeev.onlinestore.entity.UserRole;
import org.junit.Test;

import static org.junit.Assert.*;

public class JdbcUserDaoTest {

    @Test
    public void getByName() {
        User expectedUser = new User("admin","admin", UserRole.ADMIN);
        JdbcUserDao jdbcUserDao = new JdbcUserDao();
        User actualUser = jdbcUserDao.getByName("name");

        assertNotNull(actualUser);
        assertEquals(expectedUser.getUserName(),actualUser.getUserName());
        assertEquals(expectedUser.getPassword(),actualUser.getPassword());
    }
}