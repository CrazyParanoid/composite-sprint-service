package ru.agiletech.composite.sprint.service;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTest;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.agiletech.composite.sprint.service.client.sprint.SprintServiceClient;
import ru.agiletech.composite.sprint.service.client.sprint.dto.Sprint;
import ru.agiletech.composite.sprint.service.client.task.TaskServiceClient;
import ru.agiletech.composite.sprint.service.client.task.dto.Task;
import ru.agiletech.composite.sprint.service.config.TestConfiguration;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertTrue;


@Slf4j
@GraphQLTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Import(TestConfiguration.class)
@AutoConfigureWireMock(port = 0)
@ContextConfiguration(classes = Application.class)
public class TestSprint {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Autowired
    @MockBean
    private SprintServiceClient sprintServiceClient;

    @Autowired
    @MockBean
    private TaskServiceClient taskServiceClient;

    @Before
    public void setup() {
        Set<Sprint> sprints = new HashSet<>();
        sprints.add(createSprintResponse());

        Mockito.when(sprintServiceClient.getSprint(ArgumentMatchers.anyString()))
                .thenReturn(createSprintResponse());
        Mockito.when(taskServiceClient.getTask(ArgumentMatchers.anyString()))
                .thenReturn(createTaskResponse());
        Mockito.when(sprintServiceClient.getSprints())
                .thenReturn(sprints);
    }

    @Test
    public void testGetAllSprints() throws IOException {
        GraphQLResponse response = graphQLTestTemplate.perform("queries/find-sprints.graphql", null);
        assertTrue(response.isOk());
    }

    @Test
    public void testSprintById() throws IOException {
        GraphQLResponse response = graphQLTestTemplate.perform("queries/find-sprint-by-id.graphql", null);
        assertTrue(response.isOk());
    }

    @NotNull
    private Sprint createSprintResponse(){
        Sprint sprint = new Sprint();

        sprint.setName("Test Name");
        sprint.setGoal("Test Goal");
        sprint.setId("7cd80807-807c-498f-8cf9-fe0c2bed403c");
        sprint.setStatus("INACTIVE");
        sprint.setProjectKey("TST");

        return sprint;
    }

    @NotNull
    private Task createTaskResponse(){
        Task task = new Task();

        String assigneeId = UUID.randomUUID().toString();
        String taskId = UUID.randomUUID().toString();

        task.setSummary("Test Summary");
        task.setAssignee(assigneeId);
        task.setDescription("Test Description");
        task.setId(taskId);
        task.setPriority("LOW");
        task.setWorkFlowStatus("TODO");
        task.setSprintId("7cd80807-807c-498f-8cf9-fe0c2bed403c");
        task.setProjectKey("TST");

        return task;
    }

}
