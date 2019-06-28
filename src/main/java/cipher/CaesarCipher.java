package cipher;

import cipher.operation.CipherOperation;
import cipher.operation.DecipherOperation;

public class CaesarCipher {
    private final int offset;

    public CaesarCipher(int offset) {
        this.offset = offset;
    }

    public String cipher(String message) {
        return new CipherOperation(offset).apply(message);
    }

    public String decipher(String message) {
        return new DecipherOperation(offset).apply(message);
    }
}
