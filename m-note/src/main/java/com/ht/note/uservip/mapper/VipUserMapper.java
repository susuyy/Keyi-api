package com.ht.note.uservip.mapper;

import com.ht.note.uservip.entity.VipUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author suyangyu
 * @since 2020-06-09
 */
public interface VipUserMapper extends BaseMapper<VipUser> {

    /**
     * 根据手机号查询用户信息
     *
     * @param phoneNum
     * @return
     */
    @Select("SELECT id,phone_num,openid,create_time,update_time FROM vip_user WHERE phone_num = #{phoneNum}")
    VipUser selectByPhoneNum(@Param("phoneNum") String phoneNum);

    /**
     * 根据openid查询用户信息
     *
     * @param openId
     * @return
     */
    @Select("SELECT id,phone_num,openid,vip_level,point,nick_name,head_img FROM vip_user WHERE openid = #{openId}")
    VipUser selectByOpenid(@Param("openId") String openId);


}
