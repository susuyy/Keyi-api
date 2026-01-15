package com.ht.note.indexapi.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.note.indexapi.entity.VipUserSignIn;
import com.ht.note.indexapi.mapper.VipUserSignInMapper;
import com.ht.note.indexapi.service.IVipUserSignInService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.note.utils.DateStrUtil;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2023-07-07
 */
@Service
public class VipUserSignInServiceImpl extends ServiceImpl<VipUserSignInMapper, VipUserSignIn> implements IVipUserSignInService {

    @Override
    public Integer userSignIn(String userId,String openId) {
        LambdaQueryWrapper<VipUserSignIn> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(VipUserSignIn::getUserId, userId);
        VipUserSignIn vipUserSignIn = getOne(queryWrapper);

        String today = DateStrUtil.nowDateStrYearMoonDay();

        if (vipUserSignIn == null) {
            VipUserSignIn vipUserSignInSave = new VipUserSignIn();
            vipUserSignInSave.setUserId(Long.parseLong(userId));
            vipUserSignInSave.setOpenid(openId);
            vipUserSignInSave.setSignInDays(1);
            vipUserSignInSave.setLastSignInDate(today);
            vipUserSignInSave.setThisSignInDate(today);
            save(vipUserSignInSave);
            return vipUserSignInSave.getSignInDays();
        } else {
            if (!today.equals(vipUserSignIn.getThisSignInDate())) {
                vipUserSignIn.setSignInDays(vipUserSignIn.getSignInDays() + 1);
                vipUserSignIn.setLastSignInDate(vipUserSignIn.getThisSignInDate());
                vipUserSignIn.setThisSignInDate(today);
                updateById(vipUserSignIn);
            }
        }

        return vipUserSignIn.getSignInDays();

    }
}
