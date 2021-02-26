package runconfig;

import javax.websocket;
import runconfig.CommsHandler;

@ServerEndpoint(value = "/gizmo", decoders = MessageDecoder.class,  encoders = MessageEncoder.class )
    public class FirstEndpoint {

        private Session gizmoSession;

        @OnOpen
        public void onOpen( Session session ) throws IOException {
     
            this.gizmoSession = session;

            clientMessage message = new clientMessage();
            message.setFrom("gizmo");
            message.setContent("Connected!");
            userSend(message, this.gizmoSession);
        }

        @OnMessage
        public void onMessage(Session session, clientMessage message) throws IOException {
		  String msg = message.getContent();  
          context().LOG().LogInfo( msg + " message received from " + message.getFrom() );
          
		  if ( msg == "first" ) {
			  CommsHandler.Singleton().App().QueryOne( msg );
          }
		  if ( msg == "second" ) {
			  CommsHandler.Singleton().App().QueryTwo( msg );
          }
		}

        @OnClose
        public void onClose(Session session) throws IOException {
            clientMessage message = new clientMessage();
            message.setFrom("gizmo");
            message.setContent("Connected!");
            userSend(message, this.gizmoSession);
        }

        @OnError
        public void onError(Session session, Throwable throwable) {
            // Do error handling here
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
