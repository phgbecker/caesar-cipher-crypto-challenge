package challenge;

public class Challenge {

    public static void main(String[] args) {
        CaesarChallenge challenge = new CaesarChallenge(
                System.getenv("api.get.resource"),
                System.getenv("api.post.resource"),
                System.getenv("challenge.output.fileName")
        );

        challenge.get();
        challenge.solve();
        challenge.save();
        challenge.post();
    }
}
