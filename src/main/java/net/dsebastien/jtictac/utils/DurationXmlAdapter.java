package net.dsebastien.jtictac.utils;

import org.joda.time.Duration;
import org.joda.time.Interval;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlAdapter;

@XmlTransient
public class DurationXmlAdapter extends XmlAdapter<Long, Duration> {

    @Override
    public Duration unmarshal(final Long d) throws Exception {
        return new Duration(d);
    }

    @Override
    public Long marshal(final Duration d) throws Exception {
        return d.getMillis();
    }

}