package net.dsebastien.jtictac.model;

import org.apache.commons.lang3.Validate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TimeSheet {
    @XmlElement(name="timesheet-entry")
    private final List<TimeSheetEntry> entries = new ArrayList<TimeSheetEntry>();

    public TimeSheet(){
    }

    public TimeSheet addEntry(final TimeSheetEntry entry){
        Validate.notNull(entry,"The entry cannot be null!");
        this.entries.add(entry);
        return this;
    }
    
    public List<TimeSheetEntry> getEntries(){
        return entries;
    }

}
