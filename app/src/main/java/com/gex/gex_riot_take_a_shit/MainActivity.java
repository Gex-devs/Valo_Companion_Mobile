package com.gex.gex_riot_take_a_shit;

import static com.gex.gex_riot_take_a_shit.MainActivity.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {



    // Handler is Must to change UI_ElEMENTS outside of the  mainactivity class
    // Might not need it in Main Activity, Need to go to Fragments
    static Handler UI_Handler = new Handler();

    static FragmentManager fragmentManager;

    {
        fragmentManager = getSupportFragmentManager();
    }

    public static Current_status_Data viewModel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button Test = findViewById(R.id.Navigation_Button_Test);
        viewModel = new ViewModelProvider(this).get(Current_status_Data.class);
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, Game_Status.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null) // name can be null
                .commit();
        Test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("clicked");
                new WebsocketServer().start();
            }
        });
        /* new WebsocketServer().start();
                Starts server, Maybe make it as a startup function since The app is the Host server*/
    }



    public static void Agent_Select_fragment(){
        UI_Handler.post(new Runnable() {
            @Override
            public void run() {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, Agent_Selection_Menu.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null) // name can be null
                        .commit();
            }
        });
    }
    public static void Game_status(){
        UI_Handler.post(new Runnable() {
            @Override
            public void run() {
                viewModel.Game_state("pfft");
            }
        });
    }
    // Use this three functions incase you want to change a Ui element in the main_activity
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
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        conns.remove(conn);
        System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Game Log: " + message);
        for (WebSocket sock : conns) {
            sock.send(message);
        }

        //viewModel.Selection(message.toString());
        switch (message) {
            case "MainMenu":
                viewModel.Game_state("MainMenu");
            case "CharacterSelectPersistentLevel":
                Agent_Select_fragment();
            case "Game starting":
                System.out.println("Game Starting");
            case "match_start":
                System.out.println("Match Started");
            default:
                viewModel.Selection(message.toString());
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

