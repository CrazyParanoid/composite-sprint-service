package ru.agiletech.composite.sprint.service.client.sprint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.agiletech.composite.sprint.service.client.task.dto.Task;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CompositeSprint extends AbstractSprint{

    private Set<Task> tasks;

    public CompositeSprint(AbstractSprint   abstractSprint,
                           Set<Task>        tasks) {
        super(abstractSprint);
        this.tasks = tasks;
    }

}
