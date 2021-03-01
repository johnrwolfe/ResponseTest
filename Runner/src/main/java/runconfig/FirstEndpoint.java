package runconfig;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;
import runconfig.CommsHandler;

public class FirstEndpoint implements WebSocket.Listener {

	@Override
	public void onOpen( WebSocket webSocket ) {
      System.out.printf( "onOpen\n" );
      WebSocket.Listener.super.onOpen( webSocket );
	}

	@Override
	public CompletionStage<?> onText( WebSocket webSocket, CharSequence data, boolean last ) {
	  System.out.printf( "onText: data = %s, last = %s\n", data, last );
	  try {
        CommsHandler.Singleton().App().QueryOne( data.toString() );
	  }
	  catch ( Exception e ) {
		System.out.printf( "Exception, %s, in onText()\n", e );
	  }
      return null;
	}

	@Override
	public CompletionStage<?> onClose( WebSocket webSocket, int statusCode, String reason ){
      System.out.printf( "onClose: statusCode = %d; reason = %s\n", statusCode, reason );
      return null;
	}

	@Override
	public void onError( WebSocket webSocket, Throwable error ) {
      System.out.printf( "onError: %s\n", error );
	}
}
