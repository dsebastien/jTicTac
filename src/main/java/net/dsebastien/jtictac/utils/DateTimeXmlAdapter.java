package net.dsebastien.jtictac.utils;

import org.joda.time.DateTime;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Date;

/**
 * Helper class for JAXB (adds support for JodaTime's {@link DateTime} class.
 * @author Sebastien Dubois -- dSebastien
 */
public class DateTimeXmlAdapter extends XmlAdapter<Date,DateTime> {

    @Override
    public DateTime unmarshal(Date date) throws Exception {
        return new DateTime(date.getTime());
    }

    @Override
    public Date marshal(DateTime dateTime) throws Exception {
        return new Date(dateTime.getMillis());
    }
}