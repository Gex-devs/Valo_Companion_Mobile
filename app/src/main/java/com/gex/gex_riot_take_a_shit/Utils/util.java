package com.gex.gex_riot_take_a_shit.Utils;

import com.gex.gex_riot_take_a_shit.R;
import com.gex.gex_riot_take_a_shit.enums.GameModes;
import com.gex.gex_riot_take_a_shit.enums.InfoType;

import org.json.JSONException;
import org.json.JSONObject;

public class util {

    public static int get_respective_image(String Agent){

        switch (Agent){
            case "Raze":
                return R.drawable.raze_background;
            case "Viper":
                return R.drawable.viper_background;
            case "Omen":
                return R.drawable.omen_background;
            case "Sova":
                return R.drawable.sova_background;
            case "Sage":
                return R.drawable.sage_background;
            case "Phoenix":
                return R.drawable.phx_background;
            case "Jett":
                return R.drawable.jett_backgroun;
            case "Cypher":
                return R.drawable.cypher_background;
            case "Brimstone":
                return R.drawable.brimstone_background;
            case "Breach":
                return R.drawable.breach_background;
            case "Reyna":
                return R.drawable.reyna_background;
            case "Killjoy":
                return R.drawable.killjoy_background;
            case "Skye":
                return R.drawable.skye_background;
            case "Yoru":
                return R.drawable.yoru_background;
            case "Astra":
                return R.drawable.astra_background;
            case "KAY/o":
                return R.drawable.kayo_background;
            case "Chamber":
                return R.drawable.chamber_background;
            case "Neon":
                return R.drawable.neon_background;
            case "Fade":
                return R.drawable.fade_background;
        }
        return R.drawable.valo_place_holder;
    }


    public static String get_respective_name(String char_name){
        switch (char_name){
            case "Clay":
                return "Raze";
            case "Pandemic":
                return "Viper";
            case "Wraith":
                return "Omen";
            case "Hunter":
                return "Sova";
            case "Thorne":
                return "Sage";
            case "Phoenix":
                return "Phoenix";
            case "Wushu":
                return "Jett";
            case "Gumshoe":
                return "Cypher";
            case "Sarge":
                return "Brimstone";
            case "Breach":
                return "Breach";
            case "Vampire":
                return "Reyna";
            case "Killjoy":
                return "Kill Joy";
            case "Guide":
                return "Skye";
            case "Stealth":
                return "Yoru";
            case "Rift":
                return "Astra";
            case "Grenadier":
                return "Kayo";
            case "Deadeye":
                return "Chamber";
            case "Sprinter":
                return "Neon";
            case "BountyHunter":
                return "Fade";
        }
        return "Agent";
    }
    public static String get_respective_map_name(String map){
        switch (map){
            case "/Game/Maps/Triad/Triad":
                return "Haven";
            case "/Game/Maps/Duality/Duality":
                return ("Bind");
            case "/Game/Maps/Bonsai/Bonsai":
                return ("Split");
            case "/Game/Maps/Ascent/Ascent":
                return("Ascent");
            case "/Game/Maps/Port/Port":
                return("Icebox");
            case "/Game/Maps/Foxtrot/Foxtrot":
                return("Breeze");
            case "/Game/Maps/Canyon/Canyon":
                return("Fracture");
            case "/Game/Maps/Pitt/Pitt":
                return("Pearl");
        }
        return null;
    }
    public static int get_respective_map_image(String map){
        switch (map){
            case "/Game/Maps/Triad/Triad":
                return R.drawable.heaven;
            case "/Game/Maps/Duality/Duality":
                return R.drawable.bind;
            case "/Game/Maps/Bonsai/Bonsai":
                return R.drawable.split;
            case "/Game/Maps/Ascent/Ascent":
                return R.drawable.ascent;
            case "/Game/Maps/Port/Port":
                return R.drawable.icebox_1;
            case "/Game/Maps/Foxtrot/Foxtrot":
                return R.drawable.breeze;
            case "/Game/Maps/Canyon/Canyon":
                return R.drawable.fracture;
            case "/Game/Maps/Pitt/Pitt":
                return R.drawable.pearl;
        }
        return 0;
    }

    public static int get_respective_map_imageBasedOnName(String map) {
        int imageResource;
        switch (map) {
            case "Haven":
                imageResource = R.drawable.heaven;
                break;
            case "Bind":
                imageResource = R.drawable.bind;
                break;
            case "Split":
                imageResource = R.drawable.split;
                break;
            case "Ascent":
                imageResource = R.drawable.ascent;
                break;
            case "Icebox":
                imageResource = R.drawable.heaven;
                break;
            case "Breeze":
                imageResource = R.drawable.ascent;
                break;
            case "Fracture":
                imageResource = R.drawable.fracture;
                break;
            case "Pearl":
                imageResource = R.drawable.pearl;
                break;
            default:
                // Set a default image resource or handle the case when the map is not recognized
                imageResource = R.drawable.blue_button_background;
                break;
        }
        return imageResource;
    }
    public static GameModes GetRespectiveGameMode(String codeName){
        switch (codeName){
            case "unrated":
                return GameModes.UNRATED;
            case "competitive":
                return GameModes.COMPETITIVE;
            case "swiftplay":
                return GameModes.SWIFT_PLAY;
            case "spikerush":
                return GameModes.SPIKE_RUSH;
            case "deathmatch":
                return GameModes.DEATH_MATCH;
            case "ggteam":
                return GameModes.ESCALATION;
            case "hurm":
                return GameModes.TEAM_DEATHMATCH;
            default:
                return GameModes.DEATH_MATCH;

        }
    }

    public static InfoType IdentifyDataType(String jsonString) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonString);

        String type = jsonObject.getString("type");

        if (type.equals(InfoType.Chat.getDisplayName())){
            return InfoType.Chat;
        }else {
            return InfoType.Interface;
        }
    }
}
