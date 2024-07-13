package org.example.hakmana.model.overviewTable;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import org.example.hakmana.model.noteHndling.NoteTable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogFileReader {
    private static LogFileReader logFileReaderInstance=null;
    private LogFileReader(){

    }
    public static LogFileReader getInstance(){
        if(logFileReaderInstance==null){
            logFileReaderInstance = new LogFileReader();
        }
        return logFileReaderInstance;
    }
    public static ArrayList<LogEntry> readLogFile(ChoiceBox<String> user,ChoiceBox<String> device,ChoiceBox<String> deviceId) throws IOException {
        ArrayList <LogEntry> logEntries=new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/logs/systemuser.log"))) {
            String line;
            while ((line = br.readLine()) != null) {
                LogEntry entry = parseLogEntry(line,user,device,deviceId);
                if (entry != null) {
                    logEntries.add(entry);
                }
            }
        }
        return logEntries;
    }

    private static LogEntry parseLogEntry(String logLine,ChoiceBox<String> user,ChoiceBox<String> device,ChoiceBox<String> deviceId) {
        String time;
        String process;
        String details;

        System.out.println(deviceId.getValue());
        String[] parts = logLine.split("/", 6);

        if (parts.length < 3) {
            return null;
        }
        if (user.getValue().equals(parts[4]) && deviceId.getValue().equals(parts[3]) &&  device.getValue().equals(parts[5]) ) {
            time = parts[0];
            process = parts[1];
            details = parts[2];
            return new LogEntry(details, time, process);

        }
       else if (user.getValue().equals(parts[4]) && deviceId.getValue().equals("all") &&  device.getValue().equals(parts[5]) ) {
            time = parts[0];
            process = parts[1];
            details = parts[2];
            return new LogEntry(details, time, process);
        }
        else if (user.getValue().equals(parts[4]) && device.getValue().equals("all")) {
            time = parts[0];
            process = parts[1];
            details = parts[2];
            return new LogEntry(details, time, process);
        }

        else if (user.getValue().equals("all") && deviceId.getValue().equals(parts[3]) &&  device.getValue().equals(parts[5]) ) {
            time = parts[0];
            process = parts[1];
            details = parts[2];
            return new LogEntry(details, time, process);
        }
        else if (user.getValue().equals("all") && device.getValue().equals("all")) {
            time = parts[0];
            process = parts[1];
            details = parts[2];
            return new LogEntry(details, time, process);
        }

        else if (user.getValue().equals("all") && device.getValue().equals(parts[5]) && deviceId.getValue().equals("all")) {
            time = parts[0];
            process = parts[1];
            details = parts[2];
            return new LogEntry(details, time, process);
        }

        else{
            return null;
        }

    }
}

