package com.ht.note;


import com.ht.note.uservip.entity.*;

import com.ht.note.uservip.service.VipUserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VipUserTest {


    @Autowired
    VipUserService vipUserService;


    @Test
    public void registerTest()   {
        RegisterVipUserData registerVipUserData = new RegisterVipUserData();
        registerVipUserData.setPhoneNum("18708940720");
        registerVipUserData.setNickName("小明");
        registerVipUserData.setOpenid("test789456");
        registerVipUserData.setHeadImg("");
        vipUserService.register(registerVipUserData);
    }

}
