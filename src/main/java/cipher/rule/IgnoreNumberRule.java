package cipher.rule;

import java.util.function.Predicate;

public class IgnoreNumberRule implements Predicate<Character> {

    @Override
    public boolean test(Character character) {
        return String.valueOf(character).matches("[0-9]");
    }
}
