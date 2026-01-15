package com.ht.note.uservip.mapper;

import com.ht.note.uservip.entity.AuthCode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

/**
 * <p>
 * 记录手机号验证码 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2020-06-16
 */
public interface AuthCodeMapper extends BaseMapper<AuthCode> {

    /**
     * 根据手机查询验证码
     *
     * @param authKey
     * @return
     */
    @Select("select * from auth_code where auth_key = #{authKey}")
    AuthCode selectByAuthKey(@Param("authKey") String authKey);

    /**
     * 根据手机更新验证码
     * @param authKey
     * @param code
     * @param validityDate
     */
    @Update("update auth_code set auth_code = #{code} , validity_date = #{validityDate}  where auth_key = #{authKey}")
    void updateByAuthKey(@Param("authKey") String authKey, @Param("code") String code, @Param("validityDate") Date validityDate);
}
