package com.ht.note.notemark.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ht.note.common.LoginUserInfo;
import com.ht.note.config.NoteTypeConstant;
import com.ht.note.notemark.entity.BaseRequestPageData;
import com.ht.note.notemark.entity.CUserSearchNoteMarkData;
import com.ht.note.notemark.entity.NoteGroupDaysData;
import com.ht.note.notemark.entity.NoteMark;
import com.ht.note.notemark.entity.NoteMarkResources;
import com.ht.note.notemark.entity.NoteMarkStatisticsData;
import com.ht.note.notemark.entity.OpsGradePointReqData;

import com.ht.note.notemark.entity.RequestRecordNote;
import com.ht.note.notemark.entity.SearchStatisticsMonthCount;
import com.ht.note.notemark.service.INoteMarkResourcesService;
import com.ht.note.notemark.service.INoteMarkService;
import com.ht.note.uservip.entity.VipUser;
import com.ht.note.webauthconfig.interceptor.AccessContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2023-06-03
 */
@Api(value = "笔记相关", tags = {"笔记相关"})
@CrossOrigin(allowCredentials = "true")
@RestController
@RequestMapping("/note-mark")
public class NoteMarkController {


    @Autowired
    private INoteMarkService noteMarkService;

    @Autowired
    private INoteMarkResourcesService noteMarkResourcesService;

    /**
     * 用户上传笔记
     *
     * @param answerFiles
     * @param titleFiles
     * @param title
     * @param answer
     * @param type
     * @throws Exception
     */
    @ApiOperation("用户上传笔记--图片资源文件上传")
    @PostMapping("/recordNote")
    public void recordNote(@RequestParam(value = "answerFiles", required = false) MultipartFile[] answerFiles,
                           @RequestParam(value = "titleFiles", required = false) MultipartFile[] titleFiles,
                           @RequestParam("title") String title,
                           @RequestParam("answer") String answer,
                           @RequestParam("type") String type) throws Exception {
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
        noteMarkService.recordNote(loginUserInfo, answerFiles, title, answer, type, titleFiles);
    }

    /**
     * 用户上传笔记--图片资源url上传
     *
     * @param requestRecordNote
     * @throws Exception
     */
    @ApiOperation("用户上传笔记--图片资源url上传")
    @PostMapping("/recordNoteUrlSet")
    public void recordNoteUrlSet(@RequestBody RequestRecordNote requestRecordNote) throws Exception {
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
        noteMarkService.recordNoteUrlSet(loginUserInfo, requestRecordNote);
    }

    /**
     * 用户所有笔记查询列表
     *
     * @param cUserSearchNoteMarkData
     * @return
     * @throws Exception
     */
    @ApiOperation("用户全部笔记列表")
    @PostMapping("/noteMarkList")
    public IPage<NoteMark> noteMarkList(@RequestBody CUserSearchNoteMarkData cUserSearchNoteMarkData) throws Exception {
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
        return noteMarkService.noteMarkList(loginUserInfo.getUserId(), cUserSearchNoteMarkData);
    }

    /**
     * 笔记按月统计每天条数
     *
     * @param searchStatisticsMonthCount
     * @return
     * @throws Exception
     */
    @ApiOperation("笔记按月统计每天条数")
    @PostMapping("/noteMarkStatisticsMonthCount")
    public List<NoteMarkStatisticsData> noteMarkStatisticsMonthCount(@RequestBody SearchStatisticsMonthCount searchStatisticsMonthCount) throws Exception {
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
        return noteMarkService.noteMarkStatisticsMonthCount(loginUserInfo.getUserId(), searchStatisticsMonthCount.getNoteMarkStatisticsMonth());
    }

    /**
     * 推送笔记条目
     *
     * @throws Exception
     */
    @ApiOperation("推送笔记条目")
    @PostMapping("/pushNotificationCount")
    public int pushNotificationCount() throws Exception {
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
        return noteMarkService.algorithmPushNotificationCount(loginUserInfo.getUserId());
    }

    /**
     * 推送笔记列表
     *
     * @throws Exception
     */
    @ApiOperation("推送笔记列表")
    @PostMapping("/pushNotificationList")
    public IPage<NoteMark> pushNotificationList(@RequestBody BaseRequestPageData baseRequestPageData) throws Exception {
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
        return noteMarkService.algorithmPushNotificationList(loginUserInfo.getUserId(), baseRequestPageData.getPageNo(), baseRequestPageData.getPageSize());
    }

    /**
     * 记住,遗忘
     *
     * @throws Exception
     */
    @ApiOperation("记住,遗忘")
    @PostMapping("/opsGradePoint")
    public void opsGradePoint(@RequestBody OpsGradePointReqData opsGradePointReqData) throws Exception {
        noteMarkService.opsGradePoint(opsGradePointReqData);
    }


    /**
     * 按日分组展示列表
     *
     * @param cUserSearchNoteMarkData
     * @return
     * @throws Exception
     */
    @ApiOperation("按日分组展示列表")
    @PostMapping("/noteListGroupByDays")
    public Page<NoteGroupDaysData> noteListGroupByDays(@RequestBody CUserSearchNoteMarkData cUserSearchNoteMarkData) throws Exception {
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
        Page<NoteGroupDaysData> page = noteMarkService.noteListGroupByDays(loginUserInfo.getUserId(),
                cUserSearchNoteMarkData.getPageNo(),
                cUserSearchNoteMarkData.getPageSize());

        for (NoteGroupDaysData record : page.getRecords()) {

            List<NoteMark> noteMarkList = noteMarkService.queryByNoteRecordDay(record.getNoteRecordDay());

            for (NoteMark noteMark : noteMarkList) {
                List<NoteMarkResources> noteMarkResourcesList = noteMarkResourcesService.queryByNoteMarkId(noteMark.getId());

                List<NoteMarkResources> noteMarkResourcesAnswerList = new ArrayList<>();
                List<NoteMarkResources> noteMarkResourcesTitleList = new ArrayList<>();

                for (NoteMarkResources noteMarkResources : noteMarkResourcesList) {
                    if (NoteTypeConstant.RESOURCES_TYPE_ANSWER.equals(noteMarkResources.getResourcesType())) {
                        noteMarkResourcesAnswerList.add(noteMarkResources);
                    } else if (NoteTypeConstant.RESOURCES_TYPE_TITLE.equals(noteMarkResources.getResourcesType())) {
                        noteMarkResourcesTitleList.add(noteMarkResources);
                    }
                }

                noteMark.setNoteMarkResourcesAnswerList(noteMarkResourcesAnswerList);
                noteMark.setNoteMarkResourcesTitleList(noteMarkResourcesTitleList);
            }

            record.setNoteMarkList(noteMarkList);
        }

        return page;
    }


    /**
     * 删除笔记
     *
     * @param noteMark
     * @return
     * @throws Exception
     */
    @ApiOperation("删除笔记")
    @PostMapping("/deleteById")
    public void deleteById(@RequestBody NoteMark noteMark) throws Exception {
        noteMarkService.removeById(noteMark.getId());
        List<NoteMarkResources> noteMarkResourcesList = noteMarkResourcesService.queryByNoteMarkId(noteMark.getId());
        for (NoteMarkResources noteMarkResources : noteMarkResourcesList) {
            noteMarkResourcesService.removeById(noteMarkResources.getId());
        }
    }

    /**
     * 编辑修改笔记
     *
     * @param noteMark
     * @return
     * @throws Exception
     */
    @ApiOperation("编辑修改笔记")
    @PostMapping("/updateNoteMark")
    public void updateNoteMark(@RequestBody NoteMark noteMark) throws Exception {
        NoteMark noteMarkQuery = noteMarkService.getById(noteMark.getId());
        noteMarkQuery.setTitle(noteMark.getTitle());
        noteMarkQuery.setAnswer(noteMark.getAnswer());

        noteMarkService.updateById(noteMarkQuery);

        //原始资源删除
        List<NoteMarkResources> noteMarkResourcesList = noteMarkResourcesService.queryByNoteMarkId(noteMark.getId());
        for (NoteMarkResources noteMarkResources : noteMarkResourcesList) {
            noteMarkResourcesService.removeById(noteMarkResources.getId());
        }

        //新资源保存
        List<NoteMarkResources> noteMarkResourcesAnswerList = noteMark.getNoteMarkResourcesAnswerList();
        if (noteMarkResourcesAnswerList != null && noteMarkResourcesAnswerList.size() > 0) {
            for (NoteMarkResources noteMarkResources : noteMarkResourcesAnswerList) {
                NoteMarkResources noteMarkResourcesAnswerSave = new NoteMarkResources();
                noteMarkResourcesAnswerSave.setUserId(noteMarkQuery.getUserId());
                noteMarkResourcesAnswerSave.setOpenId(noteMarkQuery.getOpenId());
                noteMarkResourcesAnswerSave.setNoteMarkId(noteMarkQuery.getId());
                noteMarkResourcesAnswerSave.setResourcesUrl(noteMarkResources.getResourcesUrl());
                noteMarkResourcesAnswerSave.setResourcesType(NoteTypeConstant.RESOURCES_TYPE_ANSWER);
                noteMarkResourcesService.save(noteMarkResourcesAnswerSave);
            }
        }


        List<NoteMarkResources> noteMarkResourcesTitleList = noteMark.getNoteMarkResourcesTitleList();
        if (noteMarkResourcesTitleList != null && noteMarkResourcesTitleList.size() > 0) {
            for (NoteMarkResources noteMarkResources : noteMarkResourcesTitleList) {
                NoteMarkResources noteMarkResourcesTitleSave = new NoteMarkResources();
                noteMarkResourcesTitleSave.setUserId(noteMarkQuery.getUserId());
                noteMarkResourcesTitleSave.setOpenId(noteMarkQuery.getOpenId());
                noteMarkResourcesTitleSave.setNoteMarkId(noteMarkQuery.getId());
                noteMarkResourcesTitleSave.setResourcesUrl(noteMarkResources.getResourcesUrl());
                noteMarkResourcesTitleSave.setResourcesType(NoteTypeConstant.RESOURCES_TYPE_TITLE);
                noteMarkResourcesService.save(noteMarkResourcesTitleSave);
            }
        }
    }

    /**
     * 根据id获取笔记详情
     *
     * @param noteMark
     * @return
     * @throws Exception
     */
    @ApiOperation("根据id获取笔记详情")
    @PostMapping("/queryById")
    public NoteMark queryById(@RequestBody NoteMark noteMark) throws Exception {
        NoteMark noteMarkQuery = noteMarkService.getById(noteMark.getId());

        List<NoteMarkResources> noteMarkResourcesList = noteMarkResourcesService.queryByNoteMarkId(noteMarkQuery.getId());

        List<NoteMarkResources> noteMarkResourcesAnswerList = new ArrayList<>();
        List<NoteMarkResources> noteMarkResourcesTitleList = new ArrayList<>();

        for (NoteMarkResources noteMarkResources : noteMarkResourcesList) {
            if (NoteTypeConstant.RESOURCES_TYPE_ANSWER.equals(noteMarkResources.getResourcesType())) {
                noteMarkResourcesAnswerList.add(noteMarkResources);
            } else if (NoteTypeConstant.RESOURCES_TYPE_TITLE.equals(noteMarkResources.getResourcesType())) {
                noteMarkResourcesTitleList.add(noteMarkResources);
            }
        }

        noteMarkQuery.setNoteMarkResourcesAnswerList(noteMarkResourcesAnswerList);
        noteMarkQuery.setNoteMarkResourcesTitleList(noteMarkResourcesTitleList);

        return noteMarkQuery;
    }
}

