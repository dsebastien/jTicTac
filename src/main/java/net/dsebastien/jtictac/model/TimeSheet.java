package net.dsebastien.jtictac.model;

import org.apache.commons.lang3.Validate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the timesheet.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TimeSheet {
    /**
     * The timesheet entries
     */
    @XmlElement(name="timesheet-entry")
    private final List<TimeSheetEntry> entries = new ArrayList<TimeSheetEntry>();

    public TimeSheet(){
    }

    /**
     * Add an entry to the timesheet.
     * @param entry the entry to add to the timesheet
     * @return fluent interface
     */
    public TimeSheet addEntry(final TimeSheetEntry entry){
        Validate.notNull(entry,"The entry cannot be null!");
        this.entries.add(entry);
        return this;
    }

    /**
     * Returns all the entries of the timesheet.
     * @return all the entries of the timesheet.
     */
    public List<TimeSheetEntry> getEntries(){
        return entries;
    }

    /**
     * Clears the timesheet (removes all entries).
     */
    public void clear(){
        entries.clear();
    }

}
