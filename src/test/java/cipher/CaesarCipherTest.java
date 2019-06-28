package cipher;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CaesarCipherTest {

    @Test
    public void givenMessage_whenCipher_thenIsEquals() {
        String givenMessage = "a ligeira raposa marrom saltou sobre o cachorro cansado";
        String expectedMessage = "d oljhlud udsrvd pduurp vdowrx vreuh r fdfkruur fdqvdgr";

        assertThat(
                new CaesarCipher(3).cipher(givenMessage)
        ).isEqualTo(expectedMessage);
    }

    @Test
    public void givenMessage_whenDecipher_thenIsEquals() {
        String givenMessage = "d oljhlud udsrvd pduurp vdowrx vreuh r fdfkruur fdqvdgr";
        String expectedMessage = "a ligeira raposa marrom saltou sobre o cachorro cansado";

        assertThat(
                new CaesarCipher(3).decipher(givenMessage)
        ).isEqualTo(expectedMessage);
    }
}
