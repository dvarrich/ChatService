package org.varrich.simple.chatservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Project:  ChatService
 * Created by Daniel L. Varrichione on 2/12/14.
 *
 * Deficiencies:
 *
 * 1.  No way to shut down gracefully
 * 2.  Does not allow for two way conversation
 * 3.  Is not aware of other chatters on the same box
 *
 * Must implement a two way connection for client and server to both send and recieve messages.
 */
public class ChatterBox {

    public static void main( String [] args )
    {
        Chatter chat = Chatter.newInstance( 1886 );
        Thread t = new Thread( chat );
        t.start();
    }
}
