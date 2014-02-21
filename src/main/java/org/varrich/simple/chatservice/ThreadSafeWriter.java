package org.varrich.simple.chatservice;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Project:  ChatService
 * Created by Daniel L. Varrichione on 2/21/14.
 */
public class ThreadSafeWriter {

    private PrintWriter printWriter;
    private Thread thread;
    private Conversator conversator;
    private boolean finished;

    private ThreadSafeWriter(){}

    public static ThreadSafeWriter newInstance( PrintWriter printWriter, Conversator conversator )
    {
        ThreadSafeWriter instance = new ThreadSafeWriter();
        instance.setPrintWriter( printWriter );
        instance.setConversator( conversator );
        instance.setThread( new Thread( instance.setUpRunnable() ) );
        instance.startThread();
        return instance;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public void setPrintWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public Conversator getConversator() {
        return conversator;
    }

    public void setConversator(Conversator conversator) {
        this.conversator = conversator;
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
                startWriting();
            }
        };
    }

    private void startWriting()
    {
        while( !this.isFinished() )
        {
            this.getConversator().getChatter()
                    .getMessageQueuer()
                    .retrieveQueuedMessages(
                            this.getConversator().getConversationId(),
                            this.getPrintWriter()
                    );
        }
    }

    private void startThread()
    {
        this.getThread().start();
    }

}
