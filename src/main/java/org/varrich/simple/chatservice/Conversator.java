package org.varrich.simple.chatservice;

import java.io.*;

/**
 * Project:  ChatService
 * Created by Daniel L. Varrichione on 2/12/14.
 */
public class Conversator implements Runnable {

    private BufferedReader bufferedreader;
    private PrintWriter printwriter;
    private long threadid;
    private boolean finished;

    private Conversator(){}

    public static Conversator newInstance( BufferedReader bufferedreader, PrintWriter printwriter )
    {
        Conversator instance = new Conversator();
        instance.setBufferedreader( bufferedreader );
        instance.setPrintwriter( printwriter );
        return instance;
    }

    public BufferedReader getBufferedreader() {
        return bufferedreader;
    }

    public void setBufferedreader(BufferedReader bufferedreader ) {
        this.bufferedreader = bufferedreader;
    }

    public PrintWriter getPrintwriter() {
        return printwriter;
    }

    public void setPrintwriter(PrintWriter printwriter) {
        this.printwriter = printwriter;
    }

    public long getThreadid() {
        return threadid;
    }

    public void setThreadid(long threadid) {
        this.threadid = threadid;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public synchronized void startConversing()
    {
        try{
            while( !this.isFinished() )
            {
                String someString = this.getBufferedreader().readLine();
                System.out.println( "Conversator" + this.getThreadid() + " just read:  " + someString );
                this.getPrintwriter().println("Echoing:  " + someString );
            }
            System.out.println( "Conversator" + this.getThreadid() + " isFinished=" + this.isFinished() + " Shutting down reader" );

            this.getPrintwriter().close();
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        this.startConversing();
    }
}
