package com.ht.note.notemark.service;

import com.ht.note.notemark.entity.NoteMarkMemory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2023-06-09
 */
public interface INoteMarkMemoryService extends IService<NoteMarkMemory> {

    NoteMarkMemory queryByNoteMarkId(Long noteMarkId);


    void addMemoryCountById(Long id,int number);

}
