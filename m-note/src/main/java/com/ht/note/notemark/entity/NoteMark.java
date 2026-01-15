package com.ht.note.notemark.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class NoteMark implements Serializable {

    private static final long serialVersionUID=1L;


    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String openId;

    private String title;

    private String answer;

    private String type;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 修改时间
     */
    private Date updateAt;

    private String noteRecordYear;

    private String noteRecordMonth;

    private String noteRecordDay;

    private Integer gradePoint;

    private String pushFlag;

    /**
     * 资源列表
     */
    @TableField(exist = false)
    private List<NoteMarkResources> noteMarkResourcesAnswerList;

    /**
     * 资源列表
     */
    @TableField(exist = false)
    private List<NoteMarkResources> noteMarkResourcesTitleList;

}
