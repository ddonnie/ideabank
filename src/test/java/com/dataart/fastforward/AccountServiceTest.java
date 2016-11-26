package com.dataart.fastforward;

import com.dataart.fastforward.config.root.DbConfig;
import com.dataart.fastforward.config.servlet.WebMvcConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;

/**
 * Created by logariett on 25.11.2016.*/


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles("test")
@ContextConfiguration(classes={DbConfig.class, WebMvcConfig.class})
public class AccountServiceTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testDataSource(){
        System.out.println(dataSource);
    }
}
