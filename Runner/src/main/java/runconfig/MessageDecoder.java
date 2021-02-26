package runconfig;

import java.net.http.WebSocket;
import com.google.gson.Gson;

public class MessageDecoder implements Decoder.Text<clientMessage> {

    private static Gson gson = new Gson();

    @Override
    public clientMessage decode(String s) throws DecodeException {
        return gson.fromJson(s, clientMessage.class);
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
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
