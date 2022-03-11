package com.gex.gex_riot_take_a_shit;

import static com.gex.gex_riot_take_a_shit.MainActivity.*;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    Button Connect_btn;

    @SuppressLint("StaticFieldLeak")
    static TextView Game_Status;

    @SuppressLint("StaticFieldLeak")
    static TextView Con_status;

    static Handler UI_Handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Game_Status = findViewById(R.id.Status);
        Connect_btn = findViewById(R.id.connect_btn);
        Con_status = findViewById(R.id.Connection_status);

        Connect_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new WebsocketServer().start();
            }
        });
    }



    public static void Connected(){
        UI_Handler.post(new Runnable() {
            @Override
            public void run() {
                Con_status.setText("Connected");
            }
        });
    }
    public static void Game_status(String stat){
        UI_Handler.post(new Runnable() {
            @Override
            public void run() {
                Game_Status.setText(stat);
            }
        });
    }
    public static void disconnected() {
        UI_Handler.post(new Runnable() {
            @Override
            public void run() {
                Con_status.setText("disconnected");
            }
        });
    }
}


class WebsocketServer extends WebSocketServer {

    private static int TCP_PORT = 4444;

    private Set<WebSocket> conns;

    public WebsocketServer() {
        super(new InetSocketAddress(TCP_PORT));
        conns = new HashSet<>();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conns.add(conn);
        System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
        Connected();
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        conns.remove(conn);
        System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
        disconnected();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Game Log: " + message);
        for (WebSocket sock : conns) {
            sock.send(message);
        }
        /*switch (message){
            case "MainMenu":
                Game_status("MainMenu");
            case "CharacterSelectPersistentLevel":
                Game_status("Agent Select");
        }*/
        if(message.equals("MainMenu")){
            Game_status("Main Menu");
        }else if(message.equals("CharacterSelectPersistentLevel")){
            Game_status("Agent Select");
        }else if(message.equals("Game starting")){
            Game_status("Game starting");
        }else if(message.equals("match_start")){
            Game_status("Game Started");
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        //ex.printStackTrace();
        if (conn != null) {
            conns.remove(conn);
            // do some thing if required
        }
        System.out.println(ex);
    }

    @Override
    public void onStart() {

        System.out.println("started");
    }
}

