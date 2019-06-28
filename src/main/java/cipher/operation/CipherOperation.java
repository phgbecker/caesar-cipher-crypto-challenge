package cipher.operation;

public class CipherOperation extends Operation {

    public CipherOperation(int offset) {
        super(offset);
    }

    @Override
    char apply(Character character) {
        return (char) ('a' + (character - 'a' + offset) % 26);
    }
}
