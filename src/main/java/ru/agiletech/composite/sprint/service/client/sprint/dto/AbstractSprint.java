package ru.agiletech.composite.sprint.service.client.sprint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractSprint {

    private String      id;
    private String      name;
    private String      goal;
    private String      projectKey;
    private String      status;
    private long        sprintDays;
    private LocalDate   startDate;
    private LocalDate   endDate;

    protected AbstractSprint(AbstractSprint abstractSprint){
        this.id         = abstractSprint.id;
        this.name       = abstractSprint.name;
        this.goal       = abstractSprint.goal;
        this.projectKey = abstractSprint.projectKey;
        this.status     = abstractSprint.status;
        this.sprintDays = abstractSprint.sprintDays;
        this.startDate  = abstractSprint.startDate;
        this.endDate    = abstractSprint.endDate;
    }

}
