package cipher.operation;

public class DecipherOperation extends Operation {

    public DecipherOperation(int offset) {
        super(offset);
    }

    @Override
    char apply(Character character) {
        return (char) ('a' + (character - 'a' + (26 - offset)) % 26);
    }
}
