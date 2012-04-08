package net.dsebastien.jtictac.utils;

import org.joda.time.Interval;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlAdapter;

@XmlTransient
public class IntervalXmlAdapter extends XmlAdapter<String, Interval>{

    @Override
    public Interval unmarshal(String v) throws Exception {
        int dashIndex = v.indexOf('-');
        long start = Long.valueOf(v.substring(0, dashIndex));
        long end = Long.valueOf(v.substring(dashIndex + 1));
        return new Interval(start, end);
    }

    @Override
    public String marshal(Interval v) throws Exception {
        return v.getStartMillis() + "-" + v.getEndMillis();
    }

}