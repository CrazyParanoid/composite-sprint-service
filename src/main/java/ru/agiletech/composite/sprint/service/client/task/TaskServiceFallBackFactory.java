package ru.agiletech.composite.sprint.service.client.task;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.agiletech.composite.sprint.service.client.task.dto.Task;

@Slf4j
@Component
public class TaskServiceFallBackFactory implements FallbackFactory<TaskServiceClient> {

    @Override
    public TaskServiceClient create(Throwable ex) {
        log.error(ex.getMessage(), ex);

        return id -> createFailedTask();

    }

    private Task createFailedTask(){
        Task task = new Task();
        task.setSummary("There are some internal problems. " +
                "Please try again later");

        return task;
    }

}
