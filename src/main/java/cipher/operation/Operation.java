package cipher.operation;

import cipher.exception.InvalidMessageException;
import cipher.rule.IgnoreNumberRule;
import cipher.rule.IgnorePunctuationRule;

import java.util.stream.Stream;

public abstract class Operation {
    protected final int offset;

    public Operation(int offset) {
        this.offset = offset;
    }

    abstract char apply(Character character);

    public String apply(String message) {
        if (message == null || message.trim().isEmpty()) {
            throw new InvalidMessageException("The given message is null or empty");
        }

        StringBuilder appliedMessage = new StringBuilder();
        for (Character character : message.toLowerCase().toCharArray()) {
            if (matchesAnyRules(character)) {
                appliedMessage.append(character);
            } else {
                appliedMessage.append(apply(character));
            }
        }

        return appliedMessage.toString();
    }

    private boolean matchesAnyRules(Character letter) {
        return Stream.of(letter).anyMatch(
                new IgnoreNumberRule().or(new IgnorePunctuationRule())
        );
    }

}
