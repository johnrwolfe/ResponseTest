package runconfig;

import java.net.http.WebSocket;
import com.google.gson.Gson;

public class MessageEncoder implements Encoder.Text<clientMessage> {

    private static Gson gson = new Gson();

    @Override
    public String encode(clientMessage message) throws EncodeException {
        return gson.toJson(message);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
    }

    @Override
    public void destroy() {
        // Close resources
    }
}
