package pr1.tests.crypto;

import treptowkolleg.edu.extension.streams.StreamUtil;
import treptowkolleg.edu.text.IOApplication;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Authenticate extends IOApplication {
    private static final HashMap<User, PrivateKey> users = new HashMap<>();

    public static void main(String[] args) {
        new Authenticate();

    }

    @Override
    public void run() throws Exception {
        // TODO: noch zu erledigen
        /*withFileScanner("user.txt", s -> {
            s.useDelimiter("\\R");
            users.putAll(s.tokens()
                    .map(StreamUtil.TRIM)
                    .filter(StreamUtil.IS_NOT_EMPTY)
                    .map(StreamUtil.SPLIT_AT_WHITESPACE)
                    .filter(a -> a.length == 3)
                            .map(parts -> {
                                User u = new User(parts[0], parts[1]);
                                try {
                                    PrivateKey pk = (PrivateKey) KeyEncoder.toKey(parts[2]);
                                } catch (NoSuchAlgorithmException |
                                         InvalidKeySpecException e) {
                                    throw new RuntimeException(e);
                                }
                                return null;
                            })
                    .collect(Collectors.toMap()));
        });*/
    }
}
