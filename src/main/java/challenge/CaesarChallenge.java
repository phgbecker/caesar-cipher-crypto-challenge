package challenge;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.ApiRequest;
import request.MessageRequest;
import request.action.DecipherAction;
import request.action.HashAction;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class CaesarChallenge {
    private final Logger logger = LoggerFactory.getLogger(CaesarChallenge.class);
    private final ApiRequest apiRequest;
    private final String apiGetResource;
    private final String apiPostResource;
    private MessageRequest message;


    public CaesarChallenge(String apiGetResource, String apiPostResource) {
        this.apiRequest = new ApiRequest();

        this.apiGetResource = Objects.requireNonNull(apiGetResource, "API GET resource has not been set");
        this.apiPostResource = Objects.requireNonNull(apiPostResource, "API POST resource has not been set");
    }


    public void get() {
        logger.info("Getting challenge from resource: {}", apiGetResource);

        try {
            CloseableHttpResponse response = apiRequest.get(apiGetResource);

            message = MessageRequest.deserializeFromJson(
                    apiRequest.parseResponse(response)
            );
        } catch (IOException e) {
            logger.error("Oops, something wrong happened while getting the challenge", e);
        }
    }

    public void solve() {
        logger.info("Applying actions to solve it");

        message.applyAction(
                Arrays.asList(
                        new DecipherAction(),
                        new HashAction()
                )
        );
    }

    public void save() {
        String fileName = "answer.json";
        logger.info("Saving solution to file: {}", fileName);

        try {
            message.serializeToJson(fileName);
        } catch (IOException e) {
            logger.error("Oops, something wrong happened while saving", e);
        }
    }

    public void post() {
        logger.info("Posting solutions to resource: {}", apiPostResource);

        String apiResponse = null;
        try {
            CloseableHttpResponse response = apiRequest.post(
                    apiPostResource,
                    apiRequest.attachFileToForm("answer", "answer.json")
            );

            apiResponse = apiRequest.parseResponse(response);
        } catch (IOException e) {
            logger.error("Oops, something wrong happened while posting the solutions", e);
        }

        logger.info("Challenge submission response was {}", apiResponse);
    }
}
