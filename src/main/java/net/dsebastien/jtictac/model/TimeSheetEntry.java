package net.dsebastien.jtictac.model;

import net.dsebastien.jtictac.utils.DateTimeXmlAdapter;
import net.dsebastien.jtictac.utils.DurationXmlAdapter;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * An entry in the timesheet.
 * @author Sebastien Dubois -- dSebastien
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
public class TimeSheetEntry {
    /**
     * The task corresponding to this entry (e.g., Programming, Chat, Sleep, ...)
     */
    private String task;

    /**
     * How long the task has been performed.
     */
    private Duration duration;

    /**
     * When the task was performed.
     */
    private DateTime date;

    /**
     * Comment about the entry.
     */
    private String comment;

    /**
     * Callback automatically invoked by JAXB after unmarshalling is over.
     * Doc: http://docs.oracle.com/javase/6/docs/api/javax/xml/bind/Unmarshaller.html#unmarshalEventCallback
     * @param unmarshaller
     * @param parent
     */
    void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
    }

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

    public TimeSheetEntry setComment(final String comment){
        this.comment = comment;
        return this;
    }

    @XmlAttribute
    public String getTask(){
        return task;
    }

    @XmlAttribute
    @XmlJavaTypeAdapter(DurationXmlAdapter.class)
    public Duration getDuration(){
        return duration;
    }

    @XmlAttribute
    @XmlJavaTypeAdapter(DateTimeXmlAdapter.class)
    public DateTime getDate(){
        return date;
    }

    @XmlAttribute
    public String getComment(){
        return comment;
    }

    //todo implement hashCode and equals
}
