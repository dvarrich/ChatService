package org.varrich.simple.chatservice;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Project:  ChatService
 * Created by Daniel L. Varrichione on 2/20/14.
 */
public class ThreadSafeReader {

    private BufferedReader bufferedReader;
    private Conversator conversator;
    private Thread thread;
    private boolean finished;

    private ThreadSafeReader(){}

    public static ThreadSafeReader newInstance( BufferedReader bufferedReader, Conversator conversator )
    {
        ThreadSafeReader instance = new ThreadSafeReader();
        instance.setBufferedReader( bufferedReader );
        instance.setConversator( conversator );
        instance.setThread(new Thread(instance.setUpRunnable()));
        instance.startThread();
        return instance;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public Conversator getConversator() {
        return conversator;
    }

    public void setConversator(Conversator conversator) {
        this.conversator = conversator;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    private Runnable setUpRunnable()
    {
        return new Runnable(){
            @Override
            public void run() {
                startReading();
            }
        };
    }

    private void startReading()
    {
        try{
            while( !this.isFinished() )
            {
                String someString = this.getBufferedReader().readLine();
                System.out.println( "Conversator" + this.getThread().getId() + " just read:  " + someString );
                int delimIndx = someString.indexOf( "::::" );

                String conversationId = this.getConversator().getConversationId();
                String user = someString.substring(0, ( delimIndx == -1 ? 0 : delimIndx ));
                String message = someString.substring( ( delimIndx == -1 ? 0 : delimIndx+4 ));

                this.getConversator().getChatter()
                            .getMessageQueuer()
                            .addMessage( conversationId, user, message );
            }
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }

    private void startThread()
    {
        this.getThread().start();
        this.getConversator().setConversationId( this.getThread().getName() + "-" + this.getThread().getId() );
    }
}
