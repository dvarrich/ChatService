package org.varrich.simple.chatservice;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Project:  ChatService
 * Created by Daniel L. Varrichione on 2/20/14.
 */
public class MessageQueuer {

    private Map<String,Queue<String>> messageQueues;
    private List<String> messengers;

    private MessageQueuer(){}

    public static MessageQueuer newInstance()
    {
        MessageQueuer instance = new MessageQueuer();
        instance.setMessageQueues( new HashMap<String, Queue<String>>() );
        instance.setMessengers( new ArrayList<String>() );
        return instance;
    }

    public Map<String, Queue<String>> getMessageQueues() {
        return messageQueues;
    }

    public void setMessageQueues(Map<String, Queue<String>> messageQueues) {
        this.messageQueues = messageQueues;
    }

    public List<String> getMessengers() {
        return messengers;
    }

    public void setMessengers(List<String> messengers) {
        this.messengers = messengers;
    }

    public synchronized boolean addMessage( String conversationKey, String user, String message )
    {
        System.out.println( "Adding to Queue:  conversationKey:"+conversationKey+"; user:" + user + "; message:" + message );
        if( !this.getMessengers().contains( user ) )
        {
            this.getMessengers().add( user );
        }

        String sendMessage = user + ":  " + message;
        Iterator<Queue<String>> it = this.getMessageQueues().values().iterator();
        for( ; it.hasNext(); )
        {
            it.next().add( sendMessage );
        }

        if( !this.getMessageQueues().containsKey( conversationKey ) )
        {
            Queue<String> queue = new ArrayDeque<String>();
            queue.add( sendMessage );
            this.getMessageQueues().put( conversationKey, queue );
        }
        System.out.println( "messagequeue size=" + this.getMessageQueues().size() );
        System.out.println( "Notifying all after message add" );
        return true;
    }

    public synchronized boolean retrieveQueuedMessages( String conversationId, PrintWriter printWriter )
    {
//        System.out.println( "Getting messages from queue with size:  " + this.getMessageQueues().size() );
        if( this.getMessageQueues().containsKey( conversationId ) )
        {
            Queue<String> queue = this.getMessageQueues().get(conversationId);
            for( ;queue.iterator().hasNext(); )
            {
//                String message = queue.remove();
//                System.out.println( "printWriter with conversationId:" + conversationId + "; sending message:" + message );
                printWriter.println( queue.remove() );
            }
            return true;
        }
        return false;
    }
}
