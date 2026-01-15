package com.ht.note.notemark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ht.note.common.CacheMap;
import com.ht.note.common.LoginUserInfo;
import com.ht.note.config.NoteTypeConstant;
import com.ht.note.config.NumberConstant;
import com.ht.note.notemark.entity.*;
import com.ht.note.notemark.mapper.NoteMarkMapper;
import com.ht.note.notemark.service.INoteMarkMemoryService;
import com.ht.note.notemark.service.INoteMarkResourcesService;
import com.ht.note.notemark.service.INoteMarkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.note.uploadfile.service.ImageUploadService;
import com.ht.note.utils.DateStrUtil;
import com.ht.note.utils.StringGeneralUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2023-06-03
 */
@Service
public class NoteMarkServiceImpl extends ServiceImpl<NoteMarkMapper, NoteMark> implements INoteMarkService {

    private static final Logger logger = LoggerFactory.getLogger(NoteMarkServiceImpl.class);

    @Autowired
    private INoteMarkResourcesService iNoteMarkResourcesService;

    @Autowired
    private INoteMarkMemoryService noteMarkMemoryService;


    @Override
    @Transactional
    public void recordNote(LoginUserInfo loginUserInfo, MultipartFile[] answerFiles, String title, String answer, String type,MultipartFile[] titleFiles) throws Exception {
        List<String> resourceUrlAnswerList = new ArrayList<>();

        List<String> resourceUrlTitleList = new ArrayList<>();

        if (answerFiles!=null && answerFiles.length>0){
            resourceUrlAnswerList = ImageUploadService.uploadFiles(answerFiles);
        }

        if (titleFiles!=null && titleFiles.length>0){
            resourceUrlTitleList = ImageUploadService.uploadFiles(titleFiles);
        }

        NoteMark noteMark = new NoteMark();
        noteMark.setUserId(Long.parseLong(loginUserInfo.getUserId()));
        noteMark.setOpenId(loginUserInfo.getOpenid());
        noteMark.setTitle(title);
        noteMark.setAnswer(answer);
        noteMark.setType(type);
        noteMark.setNoteRecordYear(DateStrUtil.nowDateStrYear());
        noteMark.setNoteRecordMonth(DateStrUtil.nowDateStrYearMoon());
        noteMark.setNoteRecordDay(DateStrUtil.nowDateStrYearMoonDay());
        noteMark.setCreateAt(new Date());
        noteMark.setUpdateAt(new Date());
        noteMark.setGradePoint(NumberConstant.note_point);
        noteMark.setPushFlag(NoteTypeConstant.note_push_flag);
        save(noteMark);

        //文件图片资源保存
        List<NoteMarkResources> markResourcesAnswerArrayList = new ArrayList<>();
        if (resourceUrlAnswerList.size()>0){
            for (String url : resourceUrlAnswerList) {
                NoteMarkResources noteMarkResources = new NoteMarkResources();
                noteMarkResources.setUserId(noteMark.getUserId());
                noteMarkResources.setOpenId(noteMark.getOpenId());
                noteMarkResources.setNoteMarkId(noteMark.getId());
                noteMarkResources.setResourcesUrl(url);
                noteMarkResources.setResourcesType(NoteTypeConstant.RESOURCES_TYPE_ANSWER);
                noteMarkResources.setCreateAt(new Date());
                noteMarkResources.setUpdateAt(new Date());
                markResourcesAnswerArrayList.add(noteMarkResources);
            }
        }
        iNoteMarkResourcesService.saveBatch(markResourcesAnswerArrayList);


        //文件图片资源保存
        List<NoteMarkResources> markResourcesTitleArrayList = new ArrayList<>();
        if (resourceUrlTitleList.size()>0){
            for (String url : resourceUrlTitleList) {
                NoteMarkResources noteMarkResources = new NoteMarkResources();
                noteMarkResources.setUserId(noteMark.getUserId());
                noteMarkResources.setOpenId(noteMark.getOpenId());
                noteMarkResources.setNoteMarkId(noteMark.getId());
                noteMarkResources.setResourcesUrl(url);
                noteMarkResources.setResourcesType(NoteTypeConstant.RESOURCES_TYPE_TITLE);
                noteMarkResources.setCreateAt(new Date());
                noteMarkResources.setUpdateAt(new Date());
                markResourcesTitleArrayList.add(noteMarkResources);
            }
        }
        iNoteMarkResourcesService.saveBatch(markResourcesTitleArrayList);

    }

    @Override
    public IPage<NoteMark> noteMarkList(String userId, CUserSearchNoteMarkData cUserSearchNoteMarkData) {
        Page<NoteMark> page = new Page<>(cUserSearchNoteMarkData.getPageNo(),cUserSearchNoteMarkData.getPageSize());
        LambdaQueryWrapper<NoteMark> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NoteMark::getUserId,userId);
        if (StringGeneralUtil.checkNotNull(cUserSearchNoteMarkData.getTitle())){
            queryWrapper.like(NoteMark::getTitle,cUserSearchNoteMarkData.getTitle());
        }
        if (StringGeneralUtil.checkNotNull(cUserSearchNoteMarkData.getStartTime())){
            String startTimeStart = cUserSearchNoteMarkData.getStartTime()+" 00:00:01";
            queryWrapper.ge(NoteMark::getCreateAt,startTimeStart);
        }
        if (StringGeneralUtil.checkNotNull(cUserSearchNoteMarkData.getEndTime())){
            String endTimeEnd = cUserSearchNoteMarkData.getEndTime()+" 23:59:59";
            queryWrapper.le(NoteMark::getCreateAt,endTimeEnd);
        }
        queryWrapper.orderByDesc(NoteMark::getCreateAt);

        IPage<NoteMark> noteMarkIPage = page(page, queryWrapper);

        List<NoteMark> records = noteMarkIPage.getRecords();
        for (NoteMark record : records) {
            List<NoteMarkResources> noteMarkResourcesList = iNoteMarkResourcesService.queryByNoteMarkId(record.getId());

            List<NoteMarkResources> noteMarkResourcesAnswerList = new ArrayList<>();
            List<NoteMarkResources> noteMarkResourcesTitleList = new ArrayList<>();

            for (NoteMarkResources noteMarkResources : noteMarkResourcesList) {
                if (NoteTypeConstant.RESOURCES_TYPE_ANSWER.equals(noteMarkResources.getResourcesType())){
                    noteMarkResourcesAnswerList.add(noteMarkResources);
                }else if (NoteTypeConstant.RESOURCES_TYPE_TITLE.equals(noteMarkResources.getResourcesType())){
                    noteMarkResourcesTitleList.add(noteMarkResources);
                }
            }

            record.setNoteMarkResourcesAnswerList(noteMarkResourcesAnswerList);
            record.setNoteMarkResourcesTitleList(noteMarkResourcesTitleList);

        }

        return noteMarkIPage;
    }

    @Override
    public List<NoteMarkStatisticsData> noteMarkStatisticsMonthCount(String userId, String noteMarkStatisticsMonth) {
        String[] split = noteMarkStatisticsMonth.split("-");
        int year = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        List<String> datesInMonth = DateStrUtil.getDatesInMonth(year, month);
        List<NoteMarkStatisticsData> noteMarkStatisticsDataList = new ArrayList<>();
        for (String days : datesInMonth) {
            NoteMarkStatisticsData noteMarkStatisticsData = this.baseMapper.noteMarkStatisticsMonthCount(userId, days);
            noteMarkStatisticsData.setNoteRecordDay(days);
            noteMarkStatisticsDataList.add(noteMarkStatisticsData);
        }
        return noteMarkStatisticsDataList;
    }

    @Override
    public int algorithmPushNotificationCount(String userId) {
        QueryWrapper<NoteMark> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("push_flag",NoteTypeConstant.note_push_flag);


//        String limitDay = DateStrUtil.dateAddNumberDays(-15,new Date());
//        queryWrapper.ge("create_at",limitDay).or().le("grade_point",NumberConstant.note_push_limit_point);

        queryWrapper.and(wrapper -> wrapper.le("grade_point", 50)
                .or().nested(nestedWrapper -> nestedWrapper.apply("DATEDIFF(NOW(), create_at) in (0, 1, 2, 4, 7, 15) ")
                        .ge("grade_point", 50)));



        return count(queryWrapper);
    }

    @Override
    public IPage<NoteMark> algorithmPushNotificationList(String userId, long pageNo, long pageSize) {

        Page<NoteMark> page = new Page<>(pageNo,pageSize);
        QueryWrapper<NoteMark> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("push_flag",NoteTypeConstant.note_push_flag);

//        String limitDay = DateStrUtil.dateAddNumberDays(-15,new Date());
//        queryWrapper.ge("create_at",limitDay).or().le("grade_point",NumberConstant.note_push_limit_point);

        queryWrapper.and(wrapper -> wrapper.le("grade_point", 50)
                .or().nested(nestedWrapper -> nestedWrapper.apply("DATEDIFF(NOW(), create_at) in (0, 1, 2, 4, 7, 15) ")
                        .ge("grade_point", 50)));


        queryWrapper.orderByAsc("create_at");

        IPage<NoteMark> retPage = page(page, queryWrapper);

        List<NoteMark> records = retPage.getRecords();
        for (NoteMark record : records) {

            List<NoteMarkResources> noteMarkResourcesList = iNoteMarkResourcesService.queryByNoteMarkId(record.getId());

            List<NoteMarkResources> noteMarkResourcesAnswerList = new ArrayList<>();
            List<NoteMarkResources> noteMarkResourcesTitleList = new ArrayList<>();

            for (NoteMarkResources noteMarkResources : noteMarkResourcesList) {
                if (NoteTypeConstant.RESOURCES_TYPE_ANSWER.equals(noteMarkResources.getResourcesType())){
                    noteMarkResourcesAnswerList.add(noteMarkResources);
                }else if (NoteTypeConstant.RESOURCES_TYPE_TITLE.equals(noteMarkResources.getResourcesType())){
                    noteMarkResourcesTitleList.add(noteMarkResources);
                }
            }

            record.setNoteMarkResourcesAnswerList(noteMarkResourcesAnswerList);
            record.setNoteMarkResourcesTitleList(noteMarkResourcesTitleList);

        }


        return retPage;
    }

    @Override
    public void opsGradePoint(OpsGradePointReqData opsGradePointReqData) {
        int opsPoint = 0;
        if (NoteTypeConstant.note_ops_remember.equals(opsGradePointReqData.getOpsFlag())){
            opsPoint = NumberConstant.note_ops_point;
            this.baseMapper.updateGradePointById(opsGradePointReqData.getNoteMarkId(),opsPoint);
        }else if (NoteTypeConstant.note_ops_forget.equals(opsGradePointReqData.getOpsFlag())){
            opsPoint = NumberConstant.forget_ops_point;
            this.baseMapper.resetGradePointById(opsGradePointReqData.getNoteMarkId(),opsPoint);
        }


        NoteMark noteMarkQuery = getById(opsGradePointReqData.getNoteMarkId());
        Date afterDate = DateStrUtil.dateAddNumberDaysRetDate(15, noteMarkQuery.getCreateAt());
        if (new Date().after(afterDate)){
            NoteMarkMemory noteMarkMemory = noteMarkMemoryService.queryByNoteMarkId(opsGradePointReqData.getNoteMarkId());
            if (NoteTypeConstant.note_ops_remember.equals(opsGradePointReqData.getOpsFlag())){
                if (noteMarkMemory!=null){
                    Integer memoryCount = noteMarkMemory.getMemoryCount();
                    noteMarkMemoryService.addMemoryCountById(noteMarkMemory.getId(),1);
                    if (memoryCount>=2){
                        updatePushFlagById(noteMarkMemory.getNoteMarkId(),NoteTypeConstant.note_not_push_flag);
                    }
                }else {
                    NoteMarkMemory noteMarkMemorySave = new NoteMarkMemory();
                    noteMarkMemorySave.setNoteMarkId(noteMarkQuery.getId());
                    noteMarkMemorySave.setMemoryCount(1);
                    noteMarkMemorySave.setMemoryTime(new Date());
                    noteMarkMemorySave.setCreateAt(new Date());
                    noteMarkMemorySave.setUpdateAt(new Date());
                    noteMarkMemoryService.save(noteMarkMemorySave);
                }
            }else if (NoteTypeConstant.note_ops_forget.equals(opsGradePointReqData.getOpsFlag())){
                if (noteMarkMemory!=null){
                    Integer memoryCount = noteMarkMemory.getMemoryCount();
                    noteMarkMemoryService.addMemoryCountById(noteMarkMemory.getId(),-memoryCount);
                }
            }
        }

    }

    @Override
    public void updatePushFlagById(Long id, String pushFlag) {
        this.baseMapper.updatePushFlagById(id,pushFlag);
    }

    @Override
    public void updateGradePointTask() {
        this.baseMapper.updateGradePointTask();
    }

    @Override
    @Transactional
    public void recordNoteUrlSet(LoginUserInfo loginUserInfo, RequestRecordNote requestRecordNote) {

        NoteMark noteMark = new NoteMark();
        noteMark.setUserId(Long.parseLong(loginUserInfo.getUserId()));
        noteMark.setOpenId(loginUserInfo.getOpenid());
        noteMark.setTitle(requestRecordNote.getTitle());
        noteMark.setAnswer(requestRecordNote.getAnswer());
        noteMark.setType(requestRecordNote.getType());
        noteMark.setNoteRecordYear(DateStrUtil.nowDateStrYear());
        noteMark.setNoteRecordMonth(DateStrUtil.nowDateStrYearMoon());
        noteMark.setNoteRecordDay(DateStrUtil.nowDateStrYearMoonDay());
        noteMark.setCreateAt(new Date());
        noteMark.setUpdateAt(new Date());
        noteMark.setGradePoint(NumberConstant.note_point);
        noteMark.setPushFlag(NoteTypeConstant.note_push_flag);
        save(noteMark);

        //文件图片资源保存
        List<NoteMarkResources> markResourcesAnswerArrayList = new ArrayList<>();
        if (requestRecordNote.getAnswerImageUrlList()!=null) {
            if (requestRecordNote.getAnswerImageUrlList().size() > 0) {
                for (String url : requestRecordNote.getAnswerImageUrlList()) {
                    NoteMarkResources noteMarkResources = new NoteMarkResources();
                    noteMarkResources.setUserId(noteMark.getUserId());
                    noteMarkResources.setOpenId(noteMark.getOpenId());
                    noteMarkResources.setNoteMarkId(noteMark.getId());
                    noteMarkResources.setResourcesUrl(url);
                    noteMarkResources.setResourcesType(NoteTypeConstant.RESOURCES_TYPE_ANSWER);
                    noteMarkResources.setCreateAt(new Date());
                    noteMarkResources.setUpdateAt(new Date());
                    markResourcesAnswerArrayList.add(noteMarkResources);
                }
                iNoteMarkResourcesService.saveBatch(markResourcesAnswerArrayList);
            }
        }

        //文件图片资源保存
        List<NoteMarkResources> markResourcesTitleArrayList = new ArrayList<>();
        if (requestRecordNote.getTitleImageUrlList()!=null) {
            if (requestRecordNote.getTitleImageUrlList().size() > 0) {
                for (String url : requestRecordNote.getTitleImageUrlList()) {
                    NoteMarkResources noteMarkResources = new NoteMarkResources();
                    noteMarkResources.setUserId(noteMark.getUserId());
                    noteMarkResources.setOpenId(noteMark.getOpenId());
                    noteMarkResources.setNoteMarkId(noteMark.getId());
                    noteMarkResources.setResourcesUrl(url);
                    noteMarkResources.setResourcesType(NoteTypeConstant.RESOURCES_TYPE_TITLE);
                    noteMarkResources.setCreateAt(new Date());
                    noteMarkResources.setUpdateAt(new Date());
                    markResourcesTitleArrayList.add(noteMarkResources);
                }
                iNoteMarkResourcesService.saveBatch(markResourcesTitleArrayList);
            }
        }

    }

    @Override
    public Page<NoteGroupDaysData> noteListGroupByDays(String userId, long pageNo, long pageSize) {
        Page<NoteGroupDaysData> page = new Page<>(pageNo,pageSize);
        return this.baseMapper.noteListGroupByDays(page,userId);
    }

    @Override
    public List<NoteMark> queryByNoteRecordDay(String noteRecordDay) {
        LambdaQueryWrapper<NoteMark> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NoteMark::getNoteRecordDay,noteRecordDay);
        queryWrapper.orderByDesc(NoteMark::getNoteRecordDay);
        return list(queryWrapper);
    }
}
