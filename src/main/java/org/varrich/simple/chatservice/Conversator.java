package org.varrich.simple.chatservice;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Project:  ChatService
 * Created by Daniel L. Varrichione on 2/12/14.
 */
public class Conversator {

    private ThreadSafeReader threadSafeReader;
    private ThreadSafeWriter threadSafeWriter;
    private String conversationId;
    private Chatter chatter;

    private Conversator(){}

    public static Conversator newInstance( BufferedReader bufferedreader, PrintWriter printwriter, Chatter chatter )
    {
        Conversator instance = new Conversator();
        instance.setThreadSafeReader( ThreadSafeReader.newInstance( bufferedreader, instance ) );
        instance.setThreadSafeWriter( ThreadSafeWriter.newInstance( printwriter, instance ) );
        instance.setChatter( chatter );
        return instance;
    }

    public ThreadSafeReader getThreadSafeReader() {
        return threadSafeReader;
    }

    public void setThreadSafeReader(ThreadSafeReader threadSafeReader) {
        this.threadSafeReader = threadSafeReader;
    }

    public ThreadSafeWriter getThreadSafeWriter() {
        return threadSafeWriter;
    }

    public void setThreadSafeWriter(ThreadSafeWriter threadSafeWriter) {
        this.threadSafeWriter = threadSafeWriter;
    }

    public Chatter getChatter() { return chatter; }

    public void setChatter(Chatter chatter) { this.chatter = chatter; }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
}
