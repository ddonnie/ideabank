package com.dataart.fastforward.service;

import com.dataart.fastforward.app.dto.MarkDTO;
import com.dataart.fastforward.app.model.Mark;
import com.dataart.fastforward.app.services.MarkService;
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
 * Created by logariett on 16.12.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles("test")
@ContextConfiguration(classes={DbConfig.class, WebMvcConfig.class})
public class MarkServiceTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MarkService markService;

    @Test
    public void addTest() {
        MarkDTO markDTO = new MarkDTO();
        markDTO.setMark(1);

        markService.add(markDTO, 2, "LoneT");
    }

    @Test
    public void editTest() {
        Mark mark = markService.getMark(2,"LoneT");
        MarkDTO markDTO = new MarkDTO();

        if (mark.getMark() == 1)
            markDTO.setMark(-1);
        else if (mark.getMark() == -1)
            markDTO.setMark(1);
        markService.edit(markDTO, mark);
    }

    @Test
    public void deleteTest() {
        markService.delete(2, "LoneT");
    }
}
