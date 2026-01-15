package com.ht.note.notemark.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2023-07-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FeedBack implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String openId;

    /**
     * 文字内容
     */
    private String content;

    /**
     * 图片1
     */
    private String imageOne;

    /**
     * 图片2
     */
    private String imageTwo;

    /**
     * 图片3
     */
    private String imageThree;

    /**
     * 联系方式
     */
    private String contactMessage;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 修改时间
     */
    private Date updateAt;


}
