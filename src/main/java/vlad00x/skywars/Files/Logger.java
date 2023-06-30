package vlad00x.skywars.Files;

import vlad00x.skywars.SkyWars;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static File file;

    public static void create_log(){
        file = new File(SkyWars.get_instance().getDataFolder(), "report.txt");
        if (! file.exists()){
            file.getParentFile().mkdirs();
            SkyWars.get_instance().saveResource("report.txt", false);
        }
    }

    public static void add_message(String summary, String message){
        DateFormat dateFormat = new SimpleDateFormat("[yyy/MM/dd HH:mm:ss.SSS]");
        String date = dateFormat.format(new Date());
        String finalMessage = date + " : "+ summary + " - " + message + "\n";

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.append(finalMessage);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// [2004/12/32 14:56:07.005] : Chest - Failed to get chest 1:1:1