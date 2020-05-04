package ru.agiletech.composite.sprint.service.client.sprint;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.agiletech.composite.sprint.service.client.sprint.dto.Sprint;
import ru.agiletech.composite.sprint.service.config.FeignClientConfig;

import java.util.Set;

@FeignClient(value = "sprint-service", configuration = FeignClientConfig.class,
        fallbackFactory = SprintServiceFallBackFactory.class)
public interface SprintServiceClient {

    @GetMapping(value = "/api/sprints/{id}")
    @ResponseStatus(HttpStatus.OK)
    Sprint getSprint(@PathVariable String id);

    @GetMapping(value = "/api/sprints")
    @ResponseStatus(HttpStatus.OK)
    Set<Sprint> getSprints();

}
