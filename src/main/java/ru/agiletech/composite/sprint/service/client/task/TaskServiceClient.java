package ru.agiletech.composite.sprint.service.client.task;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.agiletech.composite.sprint.service.client.task.dto.Task;
import ru.agiletech.composite.sprint.service.config.FeignClientConfig;

@FeignClient(value = "task-service", configuration = FeignClientConfig.class,
        fallbackFactory = TaskServiceFallBackFactory.class)
public interface TaskServiceClient {

    @GetMapping(value = "/api/tasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    Task getTask(@PathVariable String id);

}
