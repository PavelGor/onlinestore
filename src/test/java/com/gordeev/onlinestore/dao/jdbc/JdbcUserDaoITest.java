package com.gordeev.onlinestore.dao.jdbc;

import com.gordeev.onlinestore.entity.User;
import com.gordeev.onlinestore.entity.UserRole;
import com.gordeev.onlinestore.locator.ServiceLocator;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JdbcUserDaoITest {

    @Before
    public void jdbcInit(){
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost/onlinestore?useUnicode=true&characterEncoding=UTF8");
        dataSource.setUser("root");
        dataSource.setPassword("root");
        ServiceLocator.register(DataSource.class, dataSource);
    }

    @Test
    public void getByNameITest() {
        User expectedUser = new User("admin","admin", "sole",UserRole.ADMIN);
        JdbcUserDao jdbcUserDao = new JdbcUserDao();
        User actualUser = jdbcUserDao.getByName("admin");

        assertNotNull(actualUser);
        assertEquals(expectedUser.getUserName(),actualUser.getUserName());
        assertEquals(expectedUser.getPassword(),actualUser.getPassword());
    }
}