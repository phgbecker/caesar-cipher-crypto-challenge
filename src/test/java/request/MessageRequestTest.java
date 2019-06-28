package request;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import request.action.DecipherAction;
import request.action.HashAction;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MessageRequestTest {
    private MessageRequest messageRequest;

    @Before
    public void setUp() {
        messageRequest = new MessageRequest(
                3,
                "0000000000000000000000000000000000000000",
                "d oljhlud udsrvd pduurp vdowrx vreuh r fdfkruur fdqvdgr",
                Arrays.asList(
                        new DecipherAction(),
                        new HashAction()
                )
        );
    }

    @Test
    public void givenJsonFileName_whenDeserializeFromJson_thenIsEquals() throws IOException {
        String jsonFileName = Objects.requireNonNull(System.getenv("json.fileName"));
        messageRequest.serializeToJson(jsonFileName);

        assertThat(
                MessageRequest.deserializeFromJson(new FileInputStream(jsonFileName))
        ).isEqualTo(messageRequest);
    }

    @Test
    public void givenJsonPayload_whenDeserializeFromJson_thenIsEquals() throws IOException {
        String jsonPayload = Objects.requireNonNull(System.getenv("json.payload"));

        assertThat(
                MessageRequest.deserializeFromJson(jsonPayload)
        ).isEqualTo(messageRequest);
    }
}