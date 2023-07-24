package com.gex.gex_riot_take_a_shit.Utils;

public class PlayerStaticInfos {
    private static String myID;


    public static boolean setMyID(String TheID){
        myID = TheID;
        return true;
    }
    public static String getMyID(){

        return myID;
    }
}
