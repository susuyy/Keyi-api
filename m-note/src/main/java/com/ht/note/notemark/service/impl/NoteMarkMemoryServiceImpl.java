package com.ht.note.notemark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ht.note.notemark.entity.NoteMarkMemory;
import com.ht.note.notemark.mapper.NoteMarkMemoryMapper;
import com.ht.note.notemark.service.INoteMarkMemoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2023-06-09
 */
@Service
public class NoteMarkMemoryServiceImpl extends ServiceImpl<NoteMarkMemoryMapper, NoteMarkMemory> implements INoteMarkMemoryService {

    @Override
    public NoteMarkMemory queryByNoteMarkId(Long noteMarkId) {
        LambdaQueryWrapper<NoteMarkMemory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NoteMarkMemory::getNoteMarkId,noteMarkId);
        return getOne(queryWrapper);
    }

    @Override
    public void addMemoryCountById(Long id,int number) {
        this.baseMapper.addMemoryCountById(id,number);
    }
}
