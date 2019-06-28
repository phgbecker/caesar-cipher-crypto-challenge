package request;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import request.action.Action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

public class MessageRequest {
    private Integer offset;
    private String token;
    private String cipheredMessage;
    private String decipheredMessage;
    private String hashedMessage;

    public MessageRequest() {
    }

    public MessageRequest(Integer offset, String token, String cipheredMessage, List<Action> actions) {
        this.offset = offset;
        this.token = token;
        this.cipheredMessage = cipheredMessage;

        applyAction(actions);
    }

    public void applyAction(List<Action> actions) {
        actions.forEach(
                action -> action.apply(this)
        );
    }

    public byte[] serializeToJson() throws IOException {
        return new ObjectMapper().writeValueAsBytes(this);
    }

    public void serializeToJson(String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        objectWriter.writeValue(
                new File(fileName),
                this
        );
    }

    public static MessageRequest deserializeFromJson(String json) throws IOException {
        return new ObjectMapper().readValue(
                json,
                MessageRequest.class
        );
    }

    public static MessageRequest deserializeFromJson(InputStream json) throws IOException {
        return new ObjectMapper().readValue(
                json,
                MessageRequest.class
        );
    }

    @JsonGetter("numero_casas")
    public Integer getOffset() {
        return offset;
    }

    @JsonSetter("numero_casas")
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @JsonGetter("token")
    public String getToken() {
        return token;
    }

    @JsonSetter("token")
    public void setToken(String token) {
        this.token = token;
    }

    @JsonGetter("cifrado")
    public String getCipheredMessage() {
        return cipheredMessage;
    }

    @JsonSetter("cifrado")
    public void setCipheredMessage(String cipheredMessage) {
        this.cipheredMessage = cipheredMessage;
    }

    @JsonGetter("decifrado")
    public String getDecipheredMessage() {
        return decipheredMessage;
    }

    @JsonSetter("decifrado")
    public void setDecipheredMessage(String decipheredMessage) {
        this.decipheredMessage = decipheredMessage;
    }

    @JsonGetter("resumo_criptografico")
    public String getHashedMessage() {
        return hashedMessage;
    }

    @JsonSetter("resumo_criptografico")
    public void setHashedMessage(String hashedMessage) {
        this.hashedMessage = hashedMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageRequest that = (MessageRequest) o;
        return Objects.equals(offset, that.offset) &&
                Objects.equals(token, that.token) &&
                Objects.equals(cipheredMessage, that.cipheredMessage) &&
                Objects.equals(decipheredMessage, that.decipheredMessage) &&
                Objects.equals(hashedMessage, that.hashedMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offset, token, cipheredMessage, decipheredMessage, hashedMessage);
    }
}
