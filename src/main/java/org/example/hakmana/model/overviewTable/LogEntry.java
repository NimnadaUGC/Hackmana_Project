package org.example.hakmana.model.overviewTable;

public class LogEntry {
    private String timestamp;
    private String process;
    private String details;
    private String user;

    public LogEntry(String details, String timestamp, String process,String user) {
        this.details = details;
        this.timestamp = timestamp;
        this.process = process;
        this.user = user;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getProcess() {
        return process;
    }

    public String getDetails() {
        return details;
    }
    public String getUser() {
        return user;
    }
}