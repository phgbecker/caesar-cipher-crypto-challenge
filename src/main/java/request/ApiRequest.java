package request;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

public class ApiRequest {

    public CloseableHttpResponse get(String resource) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(resource);

        return httpClient.execute(httpGet);
    }

    public CloseableHttpResponse post(String resource, HttpEntity entity) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(resource);
        post.setEntity(entity);

        return httpClient.execute(post);
    }

    public String parseResponse(CloseableHttpResponse httpResponse) throws IOException {
        try (CloseableHttpResponse response = httpResponse) {
            return EntityUtils.toString(response.getEntity());
        }
    }

    public HttpEntity attachFileToForm(String fieldName, String fileName) {
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        entityBuilder.addBinaryBody(fieldName, new File(fileName));

        return entityBuilder.build();
    }
}