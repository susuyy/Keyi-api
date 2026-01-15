package com.ht.note.uservip.service.impl;

import com.ht.note.uservip.entity.AuthCode;
import com.ht.note.uservip.mapper.AuthCodeMapper;
import com.ht.note.uservip.service.AuthCodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * <p>
 * 记录手机号验证码 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2020-06-16
 */
@Service
public class AuthCodeServiceImpl extends ServiceImpl<AuthCodeMapper, AuthCode> implements AuthCodeService {

    /**
     * 根据手机查询验证码
     *
     * @param authKey
     * @return
     */
    @Override
    public AuthCode queryByAuthKey(String authKey) {
        return this.baseMapper.selectByAuthKey(authKey);
    }


    @Override
    public void updateByAuthKey(String authKey, String code, Date validityDate) {
        this.baseMapper.updateByAuthKey(authKey, code,validityDate);
    }


}
