package net.kukinet.smsback.logger;

import android.os.Environment;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by chenchuk on 1/29/2016.
 */
public class Logger {

    public static final String LOG_FILENAME = "log.log";
    public static final String LOG_DIRECTORY= "mysms";

    public static final Logger instance = new Logger();


    private Logger(){
        // singleton
        appendToFile("----------------------------------LOGGER STARTED-------------------------");
    }

    public static void deleteLogfile(){
        try {
            File logs_dir = new File(Environment.getExternalStorageDirectory(), LOG_DIRECTORY);
            if (!logs_dir.exists()) {
                logs_dir.mkdirs();
            }
            File file = new File(logs_dir, LOG_FILENAME);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //appendToFile("----------------------------------LOGGER STARTED-------------------------");
    }

    public static Logger getLogger(){
        return instance;
    }

    public void appendToFile(String message) {
        //android.util.Log.e("ok","ok");
        try {
            File logs_dir = new File(Environment.getExternalStorageDirectory(), LOG_DIRECTORY);
            if (!logs_dir.exists()) {
                logs_dir.mkdirs();
            }
            File file = new File(logs_dir, LOG_FILENAME);
            FileWriter fw = new FileWriter(file, true);
            fw.write(message + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logMessage(String message){
        //android.util.Log.e("chenchuk","message");
        //Log.e("xxxxxxxx", message);
        appendToFile( android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", new java.util.Date()) + ":" + message);
    }

    public void debug(String message){
        logMessage("DEGUB\t" + message);
    }
    public void info(String message){
        logMessage("INFO\t" + message);
    }
    public void error(String message){
        logMessage("ERROR\t" + message);
    }
    public void trace(String message){
        logMessage("TRACE\t" + message);
    }
}
