package ru.agiletech.composite.sprint.service.client.sprint;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.agiletech.composite.sprint.service.client.sprint.dto.Sprint;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class SprintServiceFallBackFactory implements FallbackFactory<SprintServiceClient> {

    @Override
    public SprintServiceClient create(Throwable ex) {
        log.error(ex.getMessage(), ex);

        return new SprintServiceClient() {

            @Override
            public Sprint getSprint(String id) {
                return createFailedSprint();
            }

            @Override
            public Set<Sprint> getSprints() {
                return new HashSet<>();
            }
        };

    }

    private Sprint createFailedSprint(){
        Sprint sprint = new Sprint();
        sprint.setName("There are some internal problems. " +
                "Please try again later");

        return sprint;
    }

}
