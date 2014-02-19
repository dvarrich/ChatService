package org.varrich.simple.chatservice;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * Project:  ChatService
 * Created by Daniel L. Varrichione on 2/12/14.
 */
public class Chatter implements Runnable {

    private int port;
    private boolean finished = true;
    private List<Conversator> list;
    private ServerSocket listener;

    private Chatter(){}

    public static Chatter newInstance( int port )
    {
        Chatter instance = new Chatter();
        instance.setPort(port);
        return instance;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public List<Conversator> getList() {
        return list;
    }

    public void setList(List<Conversator> list) {
        this.list = list;
    }

    public ServerSocket getListener() {
        return listener;
    }

    public void setListener(ServerSocket listener) {
        this.listener = listener;
    }

    public synchronized void startListening()
    {
        try{
            this.setListener(new ServerSocket(this.getPort()));

            System.out.println( "Chatter:  Listener bound state=" + listener.isBound() );
            System.out.println( "Chatter:  Listening on port number " + this.getPort() );

            this.setFinished( false );
            this.setList( new ArrayList<Conversator>() );

            Socket client = null;
            while( !this.isFinished() )
            {
                client = listener.accept();

                Conversator conversator = Conversator.newInstance(
                        new BufferedReader(new InputStreamReader( client.getInputStream() )),
                        new PrintWriter(client.getOutputStream(), true)
                );

                System.out.println( "Chatter:  Spawning new conversation thread" );
                Thread t = new Thread( conversator );
                t.start();
                conversator.setThreadid( t.getId() );

                System.out.println("Chatter:  Adding Conversation to list");
                this.getList().add(conversator);
            }

            if( null != client && !client.isClosed() ) client.close();
            if( null != listener && !listener.isClosed() )
            {
                listener.close();
                System.out.println( "Shutting down.  Goodbye!" );
            }
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        this.startListening();
    }

}
