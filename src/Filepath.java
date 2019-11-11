import java.util.HashMap;
import java.util.Map;
import java.sql.*;

public class Filepath {
    static String background_image;
    static String background_play;
    static String background_music;
    static String background_music1;
    static String rocket;
    static String hearth;
    static String[] rockets = new String[5];
    static Map<String,String> chickens = new HashMap<String, String>();
    static Map<String,String> eggs = new HashMap<String, String>();
    static Map<String,String> munition = new HashMap<String, String>();
    static Map<String,String> MainMenu = new HashMap<String,String>();
    static Map<String,String> Options = new HashMap<String, String>();
    static Map<String,String> StateButtons = new HashMap<String, String>();
    static Map<String,String> Controls = new HashMap<String, String>();
    static Map<String,String> SoundMenu = new HashMap<String, String>();
    static Map<String,String> Gameplay = new HashMap<String, String>();
    static Map<Integer,String> Numbers = new HashMap<Integer, String>();
    public Filepath() {
        Map<String,String> databasemap = Database.GetFilepaths();
        background_image = databasemap.get("background_image");
        background_play = databasemap.get("background_play");
        background_music = databasemap.get("background_music");
        background_music1 = databasemap.get("background_music1");
        rocket = databasemap.get("rocket");
        hearth = databasemap.get("hearth");
        for(int i = 0;i<rockets.length;i++){
            String str = "rocket[" + i + "]";
            rockets[i] = databasemap.get(str);
        }

        chickens.put("VioletChicken",databasemap.get("VioletChicken"));
        chickens.put("BabyChicken",	databasemap.get("BabyChicken"));
        chickens.put("BabyPlusChicken", databasemap.get("BabyPlusChicken"));
        chickens.put("SoldierChicken", databasemap.get("SoldierChicken"));
        chickens.put("BadassChicken", databasemap.get("BadassChicken"));
        chickens.put("NormalChicken", databasemap.get("NormalChicken"));
        chickens.put("ArmyGeneralChicken",databasemap.get("ArmyGeneralChicken"));

        eggs.put("EasterEgg",databasemap.get("EasterEgg"));
        eggs.put("PoisonedEgg",databasemap.get("PoisonedEgg"));
        eggs.put("Omlette",databasemap.get("Omlette"));
        eggs.put("EasterTraditionalEgg",databasemap.get("EasterTraditionalEgg"));
        eggs.put("BabyBornEgg",databasemap.get("BabyBornEgg"));
        eggs.put("Shit",databasemap.get("Shit"));
        eggs.put("SpecialEgg",databasemap.get("SpecialEgg"));
        eggs.put("Bicolor",databasemap.get("Bicolor"));

        munition.put("Blue_Fire", databasemap.get("Blue_Fire"));
        munition.put("Dynamite",  databasemap.get("Dynamite"));
        munition.put("Fork",      databasemap.get("Fork"));
        munition.put("Knife",     databasemap.get("Knife"));
        munition.put("Red_Fire",  databasemap.get("Red_Fire"));

        MainMenu.put("start_new_game",databasemap.get("start_new_game"));
        MainMenu.put("resume_game",databasemap.get("resume_game"));
        MainMenu.put("options",databasemap.get("options"));
        MainMenu.put("hall_of_fame",databasemap.get("hall_of_fame"));
        MainMenu.put("exit",databasemap.get("exit"));
        MainMenu.put("title",databasemap.get("title"));
        MainMenu.put("subtitle",databasemap.get("subtitle"));

        Options.put("options_title",databasemap.get("options_title"));
        Options.put("control",databasemap.get("control"));
        Options.put("sound",databasemap.get("sound"));
        Options.put("rocket",databasemap.get("rocket"));
        Options.put("speed",databasemap.get("speed"));
        Options.put("back",databasemap.get("back"));

        StateButtons.put("back",databasemap.get("back_button"));
        StateButtons.put("pause",databasemap.get("pause"));
        StateButtons.put("play",databasemap.get("play"));
        StateButtons.put("stop",databasemap.get("stop"));

        Controls.put("controls_title",databasemap.get("controls_title"));
        Controls.put("up",databasemap.get("up"));
        Controls.put("down",databasemap.get("down"));
        Controls.put("left",databasemap.get("left"));
        Controls.put("right",databasemap.get("right"));
        Controls.put("shoot",databasemap.get("shoot"));
        Controls.put("shoot_extra",databasemap.get("shoot_extra"));

        SoundMenu.put("sound_title",databasemap.get("sound_title"));
        SoundMenu.put("change_music",databasemap.get("change_music"));
        SoundMenu.put("volume_up",databasemap.get("volume_up"));
        SoundMenu.put("volume_down",databasemap.get("volume_down"));

        Gameplay.put("score",databasemap.get("score"));
        Gameplay.put("health",databasemap.get("health"));
        Gameplay.put("munition",databasemap.get("munition"));
        Gameplay.put("killed",databasemap.get("killed"));
        Gameplay.put("strips",databasemap.get("strips"));

        for(int i = 0 ;i<=50;i++){
            Numbers.put(i,databasemap.get(i + ""));
        }
    }
}
