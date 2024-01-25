package com.gex.gex_riot_take_a_shit.Background;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.gex.gex_riot_take_a_shit.Current_status_Data;
import com.gex.gex_riot_take_a_shit.ThirdParty.OfficalValorantApi;
import com.gex.gex_riot_take_a_shit.Utils.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.*;
public class XMPPServer {
    private String bufferedMessage = "";
    String XMPPRegion = "la1";
    String RSO = OfficalValorantApi.getInstance().GetAccessToken();
    String PAS = OfficalValorantApi.getInstance().GetPSA();
    String addr = "la1.chat.si.riotgames.com";
    SSLSocket sslSocket;
    private Current_status_Data _viewModel;

    String[] messages = {
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><stream:stream to=\"" + XMPPRegion + ".pvp.net\" xml:lang=\"en\" version=\"1.0\" xmlns=\"jabber:client\" xmlns:stream=\"http://etherx.jabber.org/streams\">",
            "<auth mechanism=\"X-Riot-RSO-PAS\" xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\"><rso_token>" + RSO + "</rso_token><pas_token>" + PAS + "</pas_token></auth>",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><stream:stream to=\"" + XMPPRegion + ".pvp.net\" xml:lang=\"en\" version=\"1.0\" xmlns=\"jabber:client\" xmlns:stream=\"http://etherx.jabber.org/streams\">",
            "<iq id=\"_xmpp_bind1\" type=\"set\"><bind xmlns=\"urn:ietf:params:xml:ns:xmpp-bind\"><puuid-mode enabled=\"true\"/><resource>RC-2709252368</resource></bind></iq>",
            "<iq id=\"_xmpp_session1\" type=\"set\"><session xmlns=\"urn:ietf:params:xml:ns:xmpp-session\"/></iq>",
            "<iq type=\"get\" id=\"2\"><query xmlns=\"jabber:iq:riotgames:roster\" last_state=\"true\" /></iq>",
            "<presence/>"
    };

    private  OutputStream outputStream;
    private  InputStream inputStream;
    public XMPPServer(Current_status_Data viewModel) throws IOException, ExecutionException, InterruptedException {
        // Init Server connection here
        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        sslSocket = (SSLSocket) sslSocketFactory.createSocket(addr, 5223);

        // Enable all the suites
        String[] supportedCipherSuites = sslSocket.getSupportedCipherSuites();
        sslSocket.setEnabledCipherSuites(supportedCipherSuites);

        _viewModel = viewModel;
        // Start the handshake
        sslSocket.startHandshake();

        inputStream = sslSocket.getInputStream();
        outputStream = sslSocket.getOutputStream();

    }

    private void readMessages() throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            String receivedData = new String(buffer, 0, bytesRead);
            // Call your callback method with the received message
            OnMessage(receivedData);

        }
    }

    public void OnMessage(String message) throws IOException {
        processData(message);
        // Implement your logic here to handle the received message
//        Log.d("XMPPServer", "OnMessage: " + message);
    }

    private int indexCount = 0;
    private void SendInitMessages() throws IOException {
        if (indexCount  < messages.length) {
            SendMessage(messages[indexCount]);
            indexCount++;
        }
    }

    private void SendMessage(String data) throws IOException{
        outputStream.write(data.getBytes(StandardCharsets.UTF_8));
    }

    public void Start() throws IOException {
        Thread readerThread = new Thread(() -> {
            try {
                readMessages();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        readerThread.start();
        SendInitMessages();
    }

    private void processData(String data) throws IOException {
        if (data.startsWith("<?xml")) return;

        String oldBufferedMessage = null;

        while (!bufferedMessage.equals(oldBufferedMessage))
        {
            oldBufferedMessage = bufferedMessage;
            data = bufferedMessage + data;
            if (data.equals("")) return;
            if (!data.startsWith("<")) {
                Log.e("XMPPServer", "processData: " + "XML presence data doesn't start with '<'!");
                return;
            }
            //"stream:features"

            String firstTagName = data.substring(1, data.indexOf('>')).split(" ")[0];

            // check for self-closing tag eg <presence />
            if (findSelfClosingTagIndex(data) == 0) {
                data = data.replace("/>", "></" + firstTagName + ">");
                System.out.println(data);
            }

            int closingTagIndex = data.indexOf("</" + firstTagName + ">");
            if (closingTagIndex == -1) {
                // message is split, we need to wait for the end
                bufferedMessage = data;
                break;
            }

            int containedTags = 0;
            int nextTagIndex = data.indexOf("<" + firstTagName, 1);
            while (nextTagIndex != -1 && nextTagIndex < closingTagIndex) {
                containedTags++;
                nextTagIndex = data.indexOf("<" + firstTagName, nextTagIndex + 1);
            }

            while (containedTags > 0) {
                closingTagIndex = data.indexOf("</" + firstTagName + ">", closingTagIndex + 1);
                containedTags--;
            }

            int firstTagEnd = closingTagIndex + ("</" + firstTagName + ">").length();
            bufferedMessage = data.substring(firstTagEnd); // will be an empty string if only one tag
            data = data.substring(0, firstTagEnd);

            if (firstTagName.equals("presence")) {
                ProcessXMLDATA(data);
            }
        }

        try {
            SendInitMessages();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void ProcessXMLDATA(String data){
        String puuid = data.substring(16, 52);
        String valorantData = extractDataFromXML(data, "valorant");
        if (valorantData != null){
            String data64 = extractDataFromXML(valorantData, "p");
            long timeStamp = Long.parseLong(Objects.requireNonNull(extractDataFromXML(valorantData, "s.t")));
            try {
                // TODO: Make sure the decoder actually works
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    byte[] decoded = Base64.getDecoder().decode(data64);
                    JSONObject presenceData = new JSONObject(new String(decoded));
                    processPresenceData(puuid, presenceData, timeStamp);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private String extractDataFromXML(String xml, String tagName){

        int dataStartIndex = xml.indexOf("<" + tagName + ">");
        int dataEndIndex = xml.indexOf("</" + tagName + ">", dataStartIndex);
        if (dataStartIndex >= 0 && dataEndIndex > dataStartIndex) {
                String data = xml.substring(dataStartIndex + tagName.length() + 2, dataEndIndex);
            return data;
        }
        return null;
    }

    private void processPresenceData(String puuid, JSONObject presenceData, long timeStamp) throws JSONException {
        try {
            presenceData.put("puuid",puuid);
            presenceData.put("logtime",timeStamp);
            _viewModel.SetSocialFriendsData(presenceData);
//            int matchMap = presenceData.getString("matchMap").split("/").length - 1;
//            // TODO: This is stupid, Make change later
//            String map = presenceData.getString("provisioningFlow") == "ShootingRange"? "Range" : presenceData.getString("matchMap").split("/")[presenceData.getString("matchMap").split("/").length - 1];
//            String name = OfficalValorantApi.getInstance().GetNameByPuuid(puuid);
////            String gameMode = util.GetRespectiveGameMode(presenceData.getString("queueID")).getDisplayName();
//            String partyState = presenceData.getString("partyState");

        } catch (JSONException  e) {
            throw new RuntimeException(e);
        }
    }

    private int findSelfClosingTagIndex(String data) {
        String regex = "<[^<>]+/>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);

        if (matcher.find()) {
            return matcher.start();
        } else {
            return -1; // Return -1 if no match is found
        }
    }
}
