package com.ht.note.notemark.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ht.note.common.LoginUserInfo;
import com.ht.note.notemark.entity.CUserSearchNoteMarkData;
import com.ht.note.notemark.entity.NoteGroupDaysData;
import com.ht.note.notemark.entity.NoteMark;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.note.notemark.entity.NoteMarkStatisticsData;
import com.ht.note.notemark.entity.OpsGradePointReqData;
import com.ht.note.notemark.entity.RequestRecordNote;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2023-06-03
 */
public interface INoteMarkService extends IService<NoteMark> {


    void recordNote(LoginUserInfo loginUserInfo, MultipartFile[] answerFiles,
                    String title, String answer, String type, MultipartFile[] titleFiles) throws Exception;

    IPage<NoteMark> noteMarkList(String userId, CUserSearchNoteMarkData cUserSearchNoteMarkData);

    List<NoteMarkStatisticsData> noteMarkStatisticsMonthCount(String userId, String noteMarkStatisticsMonth);

    int algorithmPushNotificationCount(String userId);

    IPage<NoteMark> algorithmPushNotificationList(String userId, long pageNo, long pageSize);

    void opsGradePoint(OpsGradePointReqData opsGradePointReqData);

    void updatePushFlagById(Long id, String pushFlag);

    void updateGradePointTask();


    void recordNoteUrlSet(LoginUserInfo loginUserInfo, RequestRecordNote requestRecordNote);

    Page<NoteGroupDaysData> noteListGroupByDays(String userId, long pageNo, long pageSize);

    List<NoteMark> queryByNoteRecordDay(String noteRecordDay);

}
