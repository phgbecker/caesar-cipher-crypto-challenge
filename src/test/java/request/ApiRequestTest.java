package request;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiRequestTest {
    private ApiRequest apiRequest;

    @Before
    public void setUp() {
        apiRequest = new ApiRequest();
    }

    @Test
    public void givenResource_whenGet_thenResponseIsEquals() {
        String resource = Objects.requireNonNull(System.getenv("api.get.resource"));
        String expectedResponse = Objects.requireNonNull(System.getenv("api.get.expectedResponse"));

        try (CloseableHttpResponse response = apiRequest.get(resource)) {
            assertThat(
                    apiRequest.parseResponse(response).trim()
            ).isEqualTo(expectedResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenResource_whenPost_thenResponseIsEquals() {
        String resource = Objects.requireNonNull(System.getenv("api.post.resource"));
        String expectedResponse = Objects.requireNonNull(System.getenv("api.post.expectedResponse"));
        String fieldName = Objects.requireNonNull(System.getenv("api.post.fieldName"));
        String fileName = Objects.requireNonNull(System.getenv("api.post.fileName"));

        HttpEntity formData = apiRequest.attachFileToForm(fieldName, fileName);

        try (CloseableHttpResponse response = apiRequest.post(resource, formData)) {
            assertThat(
                    apiRequest.parseResponse(response).trim()
            ).isEqualTo(expectedResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}