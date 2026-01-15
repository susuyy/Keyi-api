package com.ht.note.notemark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ht.note.notemark.entity.NoteMarkResources;
import com.ht.note.notemark.mapper.NoteMarkResourcesMapper;
import com.ht.note.notemark.service.INoteMarkResourcesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2023-06-06
 */
@Service
public class NoteMarkResourcesServiceImpl extends ServiceImpl<NoteMarkResourcesMapper, NoteMarkResources> implements INoteMarkResourcesService {

    @Override
    public List<NoteMarkResources> queryByNoteMarkId(Long noteMarkId) {
        LambdaQueryWrapper<NoteMarkResources> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NoteMarkResources::getNoteMarkId,noteMarkId);
        return list(queryWrapper);
    }
}
