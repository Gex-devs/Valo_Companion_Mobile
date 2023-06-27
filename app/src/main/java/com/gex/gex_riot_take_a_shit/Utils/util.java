package com.gex.gex_riot_take_a_shit.Utils;

import com.gex.gex_riot_take_a_shit.R;

public class util {

    public static int get_respective_image(String t){
        switch (t){
            case "Clay":
                return R.drawable.raze_background;
            case "Pandemic":
                return R.drawable.viper_background;
            case "Wraith":
                return R.drawable.omen_background;
            case "Hunter":
                return R.drawable.sova_background;
            case "Thorne":
                return R.drawable.sage_background;
            case "Phoenix":
                return R.drawable.phx_background;
            case "Wushu":
                return R.drawable.jett_backgroun;
            case "Gumshoe":
                return R.drawable.cypher_background;
            case "Sarge":
                return R.drawable.brimstone_background;
            case "Breach":
                return R.drawable.breach_background;
            case "Vampire":
                return R.drawable.reyna_background;
            case "Killjoy":
                return R.drawable.killjoy_background;
            case "Guide":
                return R.drawable.skye_background;
            case "Stealth":
                return R.drawable.yoru_background;
            case "Rift":
                return R.drawable.astra_background;
            case "Grenadier":
                return R.drawable.kayo_background;
            case "Deadeye":
                return R.drawable.chamber_background;
            case "Sprinter":
                return R.drawable.neon_background;
            case "BountyHunter":
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
}
