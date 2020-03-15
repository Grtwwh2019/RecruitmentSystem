package com.zzj.recruitment;

import com.zzj.recruitment.dao.UserMapper;
import com.zzj.recruitment.pojo.User;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-11 16:34:22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RecruitmentApplication.class)
@Slf4j
public class ApplicationTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void test() throws Exception {
        User user = userMapper.selectByPrimaryKey(11);
        log.info(user.getNickname());
    }

}
