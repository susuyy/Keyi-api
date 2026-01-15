package com.ht.note.notemark.service;

import com.ht.note.notemark.entity.NoteMarkResources;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2023-06-06
 */
public interface INoteMarkResourcesService extends IService<NoteMarkResources> {

    List<NoteMarkResources> queryByNoteMarkId(Long noteMarkId);

}
