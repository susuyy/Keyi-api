package com.ht.note.notemark.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ht.note.notemark.entity.NoteGroupDaysData;
import com.ht.note.notemark.entity.NoteMark;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.note.notemark.entity.NoteMarkStatisticsData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2023-06-03
 */
public interface NoteMarkMapper extends BaseMapper<NoteMark> {


    @Select("select count(*) as count_number,note_record_day from note_mark where user_id = #{userId} and note_record_day = #{days} ")
    NoteMarkStatisticsData noteMarkStatisticsMonthCount(@Param("userId") String userId, @Param("days") String days);

    @Update("update note_mark set grade_point = grade_point + #{opsPoint} where id = #{noteMarkId} and grade_point > 0 ")
    void updateGradePointById(@Param("noteMarkId")Long noteMarkId,@Param("opsPoint")int opsPoint);

    @Update("update note_mark set grade_point = grade_point - 1 where grade_point > 0 and push_flag = 'push' ")
    void updateGradePointTask();

    @Update("update note_mark set push_flag =  #{pushFlag} where id = #{id} ")
    void updatePushFlagById(@Param("id")Long id, @Param("pushFlag")String pushFlag);

    @Select(" select note_record_day from note_mark WHERE user_id = #{userId} GROUP BY note_record_day order by note_record_day desc ")
    Page<NoteGroupDaysData> noteListGroupByDays(Page<NoteGroupDaysData> page, @Param("userId") String userId);

    @Update("update note_mark set grade_point = #{opsPoint} where id = #{noteMarkId} and grade_point > 0 ")
    void resetGradePointById(@Param("noteMarkId")Long noteMarkId,@Param("opsPoint")int opsPoint);

}
