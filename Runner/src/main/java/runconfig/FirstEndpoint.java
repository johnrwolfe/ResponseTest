package runconfig;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import runconfig.CommsHandler;

public class FirstEndpoint extends WebSocketServer {
	public static WebSocket client;  // For now, handle only one client at at time
	
	public FirstEndpoint( InetSocketAddress address ) {
	  super( address );
	  System.out.printf( "FirstEndpoint()\n" );
	}

	@Override
	public void onOpen( WebSocket webSocket, ClientHandshake handshake ) {
	  client = webSocket;
      System.out.printf( "onOpen\n" );
      webSocket.send( "Welcome to the server!" );
	}

	@Override
	public void onMessage( WebSocket webSocket, String message ) {
	  System.out.printf( "onMessage: message = %s\n", message );
	  try {
        CommsHandler.Singleton().App().QueryOne( message );
	  }
	  catch ( Exception e ) {
		System.out.printf( "Exception, %s, in onMessage()\n", e );
	  }
	}
	
	@Override
	public void onMessage( WebSocket webSocket, ByteBuffer message ) {
		System.out.println( "onMessage: received byte buffer\n" );
	}

	@Override
	public void onClose( WebSocket webSocket, int statusCode, String reason, boolean remote ){
      System.out.printf( "onClose: statusCode = %d; reason = %s\n", statusCode, reason );
	}

	@Override
	public void onError( WebSocket webSocket, Exception ex ) {
      System.out.printf( "onError: %s\n", ex );
	}
	
	@Override
	public void onStart() {
	  System.out.printf( "onStart: server started successfully\n" );
	}
}
