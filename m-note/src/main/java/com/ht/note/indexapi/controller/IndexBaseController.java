package com.ht.note.indexapi.controller;

import com.ht.note.common.LoginUserInfo;
import com.ht.note.indexapi.service.IVipUserSignInService;
import com.ht.note.notemark.entity.AphorismData;
import com.ht.note.notemark.entity.FeedBack;
import com.ht.note.utils.AphorismUtil;
import com.ht.note.utils.DateStrUtil;
import com.ht.note.utils.StringGeneralUtil;
import com.ht.note.webauthconfig.interceptor.AccessContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@Api(value = "基础通用", tags = {"基础通用"})
@RestController
@RequestMapping("/index-base")
@CrossOrigin(allowCredentials = "true")
public class IndexBaseController {

    @Autowired
    private IVipUserSignInService vipUserSignInService;

    /**
     * 获取今日鸡汤
     * @throws Exception
     */
    @ApiOperation("获取今日鸡汤")
    @GetMapping("/getTodayChickenSoup")
    public AphorismData getTodayChickenSoup() throws Exception {
        String yearMoonDay = DateStrUtil.nowDateStrYearMoonDay();
        String todayChickenSoup = AphorismUtil.AphorismMap.get(yearMoonDay);
        if (StringGeneralUtil.checkNotNull(todayChickenSoup)){
            AphorismData aphorismData = new AphorismData();
            String[] split = todayChickenSoup.split("-");
            aphorismData.setAphorismStr(split[0]);
            aphorismData.setAphorismAuthor(split[1]);
            return aphorismData;
        }else {
            AphorismUtil.AphorismMap.clear();

            //初始化推送鸡汤
            int min = 1; // 最小值（包含）
            int max = AphorismUtil.SOUPS.size(); // 最大值（包含）
            Random random = new Random();
            int randomNumber = random.nextInt(max - min + 1) + min;
            String saveStr = AphorismUtil.SOUPS.get(randomNumber);
            AphorismUtil.AphorismMap.put(yearMoonDay,saveStr);

            AphorismData aphorismData = new AphorismData();
            String[] split = saveStr.split("-");
            aphorismData.setAphorismStr(split[0]);
            aphorismData.setAphorismAuthor(split[1]);
            return aphorismData;
        }
    }


    /**
     * 签到
     * @throws Exception
     */
    @ApiOperation("签到")
    @GetMapping("/userSignIn")
    public Integer userSignIn() throws Exception {
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
        return vipUserSignInService.userSignIn(loginUserInfo.getUserId(),loginUserInfo.getOpenid());
    }


}
