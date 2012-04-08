package net.dsebastien.jtictac.model;

import org.joda.time.DateTime;

public class TimeSheetEntry {
    private final String task;
    private int hours;
    private int minutes;
    private final DateTime date;

    
    public TimeSheetEntry(final String task, int hours, int minutes, final DateTime date){
        this.task = task;
        this.hours = hours;
        this.minutes = minutes;
        this.date = date;
    }
}
