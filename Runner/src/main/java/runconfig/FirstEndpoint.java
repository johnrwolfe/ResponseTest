package runconfig;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;
import runconfig.CommsHandler;

public class FirstEndpoint implements WebSocket.Listener {

	@Override
	public void onOpen( WebSocket webSocket ) {
      System.out.println( "onOpen" );
      WebSocket.Listener.super.onOpen( webSocket );
	}

	@Override
	public CompletionStage<?> onText( WebSocket webSocket, CharSequence data, boolean last ) {
	  System.out.println( "onText: data = %s, last = %s", data, last );
      CommsHandler.Singleton().App().QueryOne( data );
	}

	@Override
	public CompletionStage<?> onClose( WebSocket webSocket, int statusCode, String reason ){
      System.out.println( "onClose: statusCode = %d; reason = %s", statusCode, reason );
      return null;
	}

	@Override
	public void onError( WebSocket webSocket, Throwable error ) {
      System.out.println( "onError: %s", error );
	}

	private static void userSend(clientMessage message, Session session) 
			throws IOException, EncodeException {

		try {
			session.getBasicRemote().
			sendObject(message);
		} catch (IOException | EncodeException e) {
			e.printStackTrace();
		}
	}
}
