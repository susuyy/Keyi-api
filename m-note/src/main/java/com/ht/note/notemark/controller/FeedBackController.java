package com.ht.note.notemark.controller;


import com.ht.note.common.LoginUserInfo;
import com.ht.note.notemark.entity.FeedBack;
import com.ht.note.notemark.entity.RequestFeedBack;
import com.ht.note.notemark.entity.RequestRecordNote;
import com.ht.note.notemark.service.IFeedBackService;
import com.ht.note.webauthconfig.interceptor.AccessContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2023-07-07
 */
@Api(value = "反馈", tags = {"反馈相关"})
@RestController
@RequestMapping("/feed-back")
@CrossOrigin(allowCredentials = "true")
public class FeedBackController {

    @Autowired
    private IFeedBackService feedBackService;

    /**
     * 提交用户反馈
     * @param feedBackRequest
     * @throws Exception
     */
    @ApiOperation("提交用户反馈")
    @PostMapping("/submit")
    public void submit(@RequestBody RequestFeedBack feedBackRequest) throws Exception {
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
        FeedBack feedBack = new FeedBack();
        feedBack.setUserId(Long.parseLong(loginUserInfo.getUserId()));
        feedBack.setOpenId(loginUserInfo.getOpenid());
        feedBack.setContent(feedBackRequest.getContent());

        List<String> imageUrlList = feedBackRequest.getImageUrlList();
        if (imageUrlList!=null && imageUrlList.size()>0){
            for (int i = 0; i < imageUrlList.size(); i++) {
                if (i==0){
                    feedBack.setImageOne(imageUrlList.get(i));
                }
                if (i==1){
                    feedBack.setImageTwo(imageUrlList.get(i));
                }
                if (i==2){
                    feedBack.setImageThree(imageUrlList.get(i));
                }
            }
        }

        feedBack.setContactMessage(feedBackRequest.getContactMessage());
        feedBackService.save(feedBack);
    }

}

