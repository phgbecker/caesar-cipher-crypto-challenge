package request.action;

import org.apache.commons.codec.digest.DigestUtils;
import request.MessageRequest;

public class HashAction implements Action {

    @Override
    public void apply(MessageRequest message) {
        String hashedMessage = DigestUtils.sha1Hex(message.getDecipheredMessage());

        message.setHashedMessage(hashedMessage);
    }
}
