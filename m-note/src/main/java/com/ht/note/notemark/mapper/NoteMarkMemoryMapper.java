package com.ht.note.notemark.mapper;

import com.ht.note.notemark.entity.NoteMarkMemory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2023-06-09
 */
public interface NoteMarkMemoryMapper extends BaseMapper<NoteMarkMemory> {

    @Update("update note_mark_memory set memory_count = memory_count + #{number} where id = #{id}")
    void addMemoryCountById(@Param("id") Long id,@Param("number") int number);

}
