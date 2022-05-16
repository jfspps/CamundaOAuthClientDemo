package com.example.camundaoauthclientdemo.web.controllers;

import com.example.camundaoauthclientdemo.constants.PathConstants;
import com.example.camundaoauthclientdemo.model.camunda.StartProcess;
import com.example.camundaoauthclientdemo.model.camunda.Task;
import com.example.camundaoauthclientdemo.model.camunda.request.DemoRequest;
import com.example.camundaoauthclientdemo.model.camunda.request.DemoRequests;
import com.example.camundaoauthclientdemo.model.camunda.request.Value;
import com.example.camundaoauthclientdemo.model.camunda.response.DemoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.List;

import static com.example.camundaoauthclientdemo.config.CamundaEngineConfig.*;

@Controller
@RequestMapping(PathConstants.SECURED)
public class DemoController {

    private final HttpClient client = HttpClient.create()
            .responseTimeout(Duration.ofMillis(TIMEOUT_MS));

    private final WebClient webClient = WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(client))
            .baseUrl(BASE_URL)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    private final Logger logger = LoggerFactory.getLogger(DemoController.class);

    // the process definition id is normally required for all other service tasks for the current process instance and
    // made available as static String
    static String processDefId;

    @PostMapping("/restricted_resource")
    public String postRequest(Model model){
        logger.debug("Resource server request received");

        DemoRequest demoRequest = new DemoRequest();
        demoRequest.setUsername(new Value("Harry"));

        DemoRequests demoRequests = new DemoRequests(demoRequest, true);

        // start (claim) a Camunda process and submit the form (this only needs to be performed once per process);
        // make sure it is cleared at the end of a completed process
        if (processDefId == null || processDefId.isBlank()){
            logger.debug("Camunda process definition id not initialised. Requesting a new one.");
            processDefId = startProcessSubmitForm(demoRequests);
        }

        logger.debug("Current process definition id: " + processDefId);

        // search for task at this stage of the process
        String taskId = getTaskId(processDefId, "Retrieved Get Restricted Resource task Id OK.");

        // now go ahead and complete the pending User task
        DemoResponse response = completeAccessRestrictedResourceTask(taskId, demoRequests);

        model.addAttribute("restricted_resource", response.getRestricted_resource_variable().getValue());

        // force a new process instance to start in future
        processDefId = null;

        return "demoPage";
    }

    /**
     * Use this to complete Camunda process task (requires a valid task ID) and receive the latest copy of Camunda Process
     * variables
     * @param taskId User Task ID that is awaiting user input
     * @param demoRequests process variables sent as part of the task completion request
     * @return updated process variables
     */
    private DemoResponse completeAccessRestrictedResourceTask(String taskId, DemoRequests demoRequests) {
        Mono<DemoResponse> resultMono = webClient.post()
                .uri(BASE_URL + "/engine-rest/task/" + taskId + "/complete")
                .body(Mono.just(demoRequests), DemoRequests.class)
                .retrieve()
                .bodyToMono(DemoResponse.class)
                .doOnSuccess(response -> logger.debug("Retrieval of restricted resource OK."))
                .timeout(Duration.ofMillis(TIMEOUT_MS));

        DemoResponse demoResponse = resultMono.block();

        if (demoResponse != null){
            logger.info("Restricted resource: " + demoResponse.getRestricted_resource_variable().getValue());
        } else {
            logger.error("Problem processing JSON response");
        }
        return demoResponse;
    }

    /**
     * This instructs Camunda to start the process and wait for user interaction at the next user task
     * @param demoRequests Process form pre-requisites, including if variables are returned
     * @return Process Instance ID for the given Process Definition
     */
    private String startProcessSubmitForm(DemoRequests demoRequests) {
        Mono<StartProcess> processMono = webClient.post()
                .uri(BASE_URL + "/engine-rest/process-definition/key/" + CAMUNDA_PROCESS_NAME + "/submit-form")
                .body(Mono.just(demoRequests), DemoRequests.class)
                .retrieve()
                .bodyToMono(StartProcess.class)
                .doOnSuccess(response -> logger.debug("Process started and form submitted OK."))
                .timeout(Duration.ofMillis(TIMEOUT_MS));

        String definitionId;
        StartProcess process = processMono.block();

        if (process != null){
            definitionId = process.getId();
            logger.debug("Camunda process definition id: " + definitionId);
            return definitionId;
        } else {
            logger.debug("Process not found. Could not retrieve Camunda process id");
            return "";
        }
    }

    /**
     * Use this to get the ID of the current User Task awaiting input; needed to advance the Camunda Process Instance
     * @param processDefId Corresponding process definition ID
     * @param messageOK Some custom message indicating the request was received by Camunda (a '200' response)
     * @return the task ID (blank if no task found)
     */
    private String getTaskId(String processDefId, String messageOK) {
        Mono<List<Task>> taskMono = webClient.get()
                .uri(BASE_URL + "/engine-rest/task/?processInstanceId=" + processDefId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Task>>() {})
                .doOnSuccess(response -> logger.debug(messageOK))
                .timeout(Duration.ofMillis(TIMEOUT_MS));

        String taskId;
        List<Task> tasks = taskMono.block();

        // todo: might need to update this
        if (tasks != null && tasks.size() > 1){
            logger.warn("More than one task received. Getting first entry.");
        }

        if (tasks != null && tasks.size() >= 1){
            Task task = tasks.get(0);
            if (task != null) {
                taskId = task.getId();
                logger.debug("Camunda task stage: " + task.getName() + ", id: " + taskId);
                return taskId;
            }
        }

        logger.debug("No tasks found. Could not retrieve Camunda task id");
        return "";
    }

}
