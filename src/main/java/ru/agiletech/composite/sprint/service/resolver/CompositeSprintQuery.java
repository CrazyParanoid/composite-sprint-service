package ru.agiletech.composite.sprint.service.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import ru.agiletech.composite.sprint.service.client.sprint.dto.CompositeSprint;
import ru.agiletech.composite.sprint.service.client.sprint.dto.Sprint;
import ru.agiletech.composite.sprint.service.client.sprint.SprintServiceClient;
import ru.agiletech.composite.sprint.service.client.task.dto.Task;
import ru.agiletech.composite.sprint.service.client.task.TaskServiceClient;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CompositeSprintQuery implements GraphQLQueryResolver {

    private final SprintServiceClient sprintServiceClient;
    private final TaskServiceClient   taskServiceClient;

    public CompositeSprint sprintById(String id){
        Sprint sprint = sprintServiceClient.getSprint(id);

        Set<String> rawTasks = sprint.getTasks();
        Set<Task> tasks = findTasks(rawTasks);

        return new CompositeSprint(sprint,
                tasks);
    }

    public Set<CompositeSprint> sprints(){
        Set<Sprint> sprints = sprintServiceClient.getSprints();
        Set<CompositeSprint> compositeSprints = new HashSet<>();

        if(CollectionUtils.isNotEmpty(sprints))
            sprints.forEach(sprint -> {

                Set<String> rawTasks = sprint.getTasks();
                Set<Task> tasks = findTasks(rawTasks);

                compositeSprints.add(new CompositeSprint(sprint,
                        tasks));
            });

        return compositeSprints;
    }

    private Set<Task> findTasks(Set<String> rawTasks){
        Set<Task> tasks = new HashSet<>();

        if(CollectionUtils.isNotEmpty(rawTasks))
            rawTasks.forEach(rawTask -> tasks.add(taskServiceClient.getTask(rawTask)));

        return tasks;
    }

}
