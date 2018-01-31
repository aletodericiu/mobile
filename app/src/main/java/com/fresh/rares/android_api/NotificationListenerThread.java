package com.fresh.rares.android_api;

import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by bogda on 1/3/2018.
 */

public class NotificationListenerThread extends AsyncTask
{
    // the server socket
    ServerSocket serverSocket;
    // the port
    private Integer port;
    // the executor
    private Executor executor;
    // the context
    private Context context;

    public NotificationListenerThread(Integer port, Context context)
    {
        // we initialize the port
        this.port = port;
        // we initialize the context
        this.context = context;
        // we initialize the executor
        this.executor = Executors.newCachedThreadPool();
        System.out.println("Doing stuff");

    }


    //@Override
    public void run()
    {
        try
        {
            // we initialize the server socket
            ServerSocket serverSocket = new ServerSocket(this.port);
            // we start listening
            while (true)
            {
                try
                {
                    Socket socket = new Socket("10.0.2.2", 6789);
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                    outputStream.writeBytes("Salut");
                }
                catch (Exception e)
                {

                }
                // we accept the connection
                Socket clientSocket = serverSocket.accept();
                // we get the message
                BufferedReader inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.drawable.notification)
                                .setContentTitle("Notification")
                                .setContentText(inputStream.readLine());


                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(1, mBuilder.build());

            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    protected Object doInBackground(Object[] objects)
    {
        run();
        return null;
    }
}
