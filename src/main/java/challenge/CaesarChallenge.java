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
    private final String challengeAnswerFileName;
    private MessageRequest message;


    public CaesarChallenge(String apiGetResource, String apiPostResource, String challengeAnswerFileName) {
        this.apiRequest = new ApiRequest();

        try {
            this.apiGetResource = Objects.requireNonNull(apiGetResource, "API GET resource");
            this.apiPostResource = Objects.requireNonNull(apiPostResource, "API POST resource");
            this.challengeAnswerFileName = Objects.requireNonNull(challengeAnswerFileName, "Challenge answer file name");
        } catch (NullPointerException e) {
            throw new NullPointerException("Variable \"" + e.getMessage() + "\" has not been set");
        }
    }


    public void get() {
        logger.info("Getting challenge from resource: {}", apiGetResource);

        try (
                CloseableHttpResponse getResponse = apiRequest.get(apiGetResource)
        ) {
            message = MessageRequest.deserializeFromJson(
                    apiRequest.parseResponse(getResponse)
            );
        } catch (IOException e) {
            logger.error("Oops, something wrong happened while getting the challenge", e);
        }
    }

    public void solve() {
        logger.info("Applying actions to solve challenge: \"{}\"", message.getCipheredMessage());

        message.applyAction(
                Arrays.asList(
                        new DecipherAction(),
                        new HashAction()
                )
        );

        logger.info("Deciphered message: \"{}\"", message.getDecipheredMessage());
        logger.info("Hashed message: \"{}\"", message.getHashedMessage());
    }

    public void save() {
        logger.info("Saving solution to file: \"{}\"", challengeAnswerFileName);

        try {
            message.serializeToJson(challengeAnswerFileName);
        } catch (IOException e) {
            logger.error("Oops, something wrong happened while saving", e);
        }
    }

    public void post() {
        logger.info("Posting solutions to resource: {}", apiPostResource);

        String apiResponse = null;
        try (
                CloseableHttpResponse postResponse = apiRequest.post(
                        apiPostResource,
                        apiRequest.attachFileToForm("answer", challengeAnswerFileName)
                )
        ) {
            apiResponse = apiRequest.parseResponse(postResponse);
        } catch (IOException e) {
            logger.error("Oops, something wrong happened while posting the solutions", e);
        }

        logger.info("Challenge submission response was: {}", apiResponse);
    }
}
