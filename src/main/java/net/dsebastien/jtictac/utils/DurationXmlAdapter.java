package net.dsebastien.jtictac.utils;

import org.joda.time.Duration;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Helper class for JAXB (adds support for JodaTime's {@link Duration} class.
 * @author Sebastien Dubois -- dSebastien
 */
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