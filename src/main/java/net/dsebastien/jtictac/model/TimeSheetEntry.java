package net.dsebastien.jtictac.model;

import net.dsebastien.jtictac.utils.DateTimeXmlAdapter;
import net.dsebastien.jtictac.utils.DurationXmlAdapter;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class TimeSheetEntry {
    @XmlAttribute
    private String task;
    @XmlAttribute
    @XmlJavaTypeAdapter(DurationXmlAdapter.class)
    private Duration duration;
    @XmlAttribute
    @XmlJavaTypeAdapter(DateTimeXmlAdapter.class)
    private DateTime date;

    public TimeSheetEntry(){
    }

    public TimeSheetEntry setTask(final String task){
        this.task = task;
        return this;
    }

    public TimeSheetEntry setDuration(final Duration duration){
        this.duration = duration;
        return this;
    }

    public TimeSheetEntry setDate(final DateTime date){
        this.date = date;
        return this;
    }

    public DateTime getDate(){
        return date;
    }
    
    public String getDateAsString(final DateTimeFormatter dateTimeFormatter){
        return date.toString(dateTimeFormatter);
    }

    public Duration getDuration(){
        return duration;
    }

    public String getTask(){
        return task;
    }
}
