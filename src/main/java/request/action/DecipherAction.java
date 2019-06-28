package request.action;

import cipher.CaesarCipher;
import request.MessageRequest;

public class DecipherAction implements Action {

    @Override
    public void apply(MessageRequest message) {
        CaesarCipher cipher = new CaesarCipher(message.getOffset());
        String decipheredMessage = cipher.decipher(message.getCipheredMessage());

        message.setDecipheredMessage(decipheredMessage);
    }
}
