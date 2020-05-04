package ru.agiletech.composite.sprint.service.client.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private String      summary;
    private String      description;
    private String      projectKey;
    private String      workFlowStatus;
    private String      id;
    private String      sprintId;
    private LocalDate   startDate;
    private LocalDate   endDate;
    private Long        workHours;
    private Long        workDays;
    private String      assignee;
    private String      priority;

}
