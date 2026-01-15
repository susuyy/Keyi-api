package com.ht.note.task;

import com.ht.note.notemark.service.INoteMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NoteGradePointTask {

    @Autowired
    private INoteMarkService noteMarkService;

    @Scheduled(cron = "0 0 2 * * ?")
    public void gradePointTasks() {
        noteMarkService.updateGradePointTask();
    }

}
