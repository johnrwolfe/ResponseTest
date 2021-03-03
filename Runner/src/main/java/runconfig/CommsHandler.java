package runconfig;


import io.ciera.runtime.summit.application.IApplication;
import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.components.Component;
import io.ciera.runtime.summit.exceptions.BadArgumentException;
import io.ciera.runtime.summit.exceptions.EmptyInstanceException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.util.LOG;
import io.ciera.runtime.summit.util.impl.LOGImpl;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import java.net.InetSocketAddress;

import interfaces.IDuplexComm;
import runconfig.FirstEndpoint;

import runconfig.commshandler.CommsHandlerApp;


public class CommsHandler extends Component<CommsHandler> {

    private Map<String, Class<?>> classDirectory;
    private static CommsHandler Singleton;
    
    public static CommsHandler Singleton() {
    	return Singleton;
    }

    public CommsHandler(IApplication app, IRunContext runContext, int populationId) {
        super(app, runContext, populationId);
        LOG = null;
        classDirectory = new TreeMap<>();
        Singleton = this;
    }

    // domain functions
    public void ForwardResponse( final String p_message ) throws XtumlException {
        context().LOG().LogInfo( "CommsHandler.ForwardResponse()" );
        if ( FirstEndpoint.client != null ) {
          System.out.printf( "ForwardResponse: sending %s to client.\n", p_message );
          FirstEndpoint.client.send( p_message );
        }
        else
          System.out.printf( "ForwardResponse: no client connected, so not sending.\n" );
    }
    
    public void SanityCheck() throws XtumlException {
        context().LOG().LogInfo( "CommsHandler.SanityCheck()" );
    }


    // relates and unrelates


    // instance selections


    // relationship selections


    // ports
    private CommsHandlerApp CommsHandlerApp;
    public CommsHandlerApp App() {
        if ( null == CommsHandlerApp ) CommsHandlerApp = new CommsHandlerApp( this, null );
        return CommsHandlerApp;
    }


    // utilities
    private LOG LOG;
    public LOG LOG() {
        if ( null == LOG ) LOG = new LOGImpl<>( this );
        return LOG;
    }


    // component initialization function
    @Override
    public void initialize() throws XtumlException {
      System.out.printf( "CommsHandler.initialize(): creating server\n" );
      FirstEndpoint firstServer = new FirstEndpoint( 
        new InetSocketAddress( "localhost", 8887) );
      System.out.printf( "CommsHandler.initialize(): starting server\n" );
      firstServer.start();
      System.out.printf( "CommsHandler.initialize(): server started\n" );
    }

    @Override
    public String getVersion() {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("CommsHandlerProperties.properties"));
        } catch (IOException e) { /* do nothing */ }
        return prop.getProperty("version", "Unknown");
    }
    @Override
    public String getVersionDate() {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("CommsHandlerProperties.properties"));
        } catch (IOException e) { /* do nothing */ }
        return prop.getProperty("version_date", "Unknown");
    }

    @Override
    public boolean addInstance( IModelInstance<?,?> instance ) throws XtumlException {
        if ( null == instance ) throw new BadArgumentException( "Null instance passed." );
        if ( instance.isEmpty() ) throw new EmptyInstanceException( "Cannot add empty instance to population." );

        return false;
    }

    @Override
    public boolean removeInstance( IModelInstance<?,?> instance ) throws XtumlException {
        if ( null == instance ) throw new BadArgumentException( "Null instance passed." );
        if ( instance.isEmpty() ) throw new EmptyInstanceException( "Cannot remove empty instance from population." );

        return false;
    }

    @Override
    public CommsHandler context() {
        return this;
    }

    @Override
    public Class<?> getClassByKeyLetters(String keyLetters) {
        return classDirectory.get(keyLetters);
    }

}
