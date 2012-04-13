package net.dsebastien.jtictac.config;

import net.dsebastien.jtictac.model.TimeSheet;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.awt.*;
import java.io.File;
import java.util.ResourceBundle;

/**
 * Configuration singleton.
 * Use to load/save configuration & data.
 * @author Sebastien Dubois -- dSebastien
 */
public class Configuration {
    private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);
    private static final Configuration INSTANCE = new Configuration();

    private org.apache.commons.configuration.Configuration config;
    private JAXBContext jaxbContext;
    private Unmarshaller jaxbUnmarshaller;
    private Marshaller jaxbMarshaller;

    private TimeSheet timeSheet;
    private ResourceBundle resourceBundle;

    private final static String CONFIG_PATH_DATA_FILE = "path.data.file";
    private final static String CONFIG_FILE = "config.properties";
    private final static String CONFIG_DATETIME_PATTERN = "datetime.pattern";
    private final static String CONFIG_PATH_SYSTEM_TRAY_ICON = "path.system.tray.icon";
    private final static String CONFIG_APPLICATION_NAME = "application.name";
    private final static String CONFIG_APPLICATION_VERSION = "application.version";
    private final static String CONFIG_RESOURCE_BUNDLE_NAME = "resource.bundle.name";
    
    private Configuration(){
        LOGGER.info("Loading configuration");
        try {
            config = new PropertiesConfiguration(CONFIG_FILE);
        } catch (ConfigurationException e) {
            LOGGER.error("Could not load the configuration file!");
        }

        LOGGER.info("Initializing JAXB");
        try {
            jaxbContext = JAXBContext.newInstance(TimeSheet.class);
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (JAXBException e) {
            LOGGER.error("Could not initialize JAXB context",e);
        }
    }

    public static Configuration getInstance(){
        return INSTANCE;
    }
    
    public String getApplicationName(){
        return config.getString(CONFIG_APPLICATION_NAME);
    }

    public String getApplicationVersion(){
        return config.getString(CONFIG_APPLICATION_VERSION);
    }

    public File getDataFile(){
        return new File(config.getString(CONFIG_PATH_DATA_FILE));
    }
    
    public java.awt.Image getSystemTrayIcon(){
        return Toolkit.getDefaultToolkit().getImage(config.getString(CONFIG_PATH_SYSTEM_TRAY_ICON));
    }
    
    public String getApplicationIconPath(){
        return config.getString(CONFIG_PATH_SYSTEM_TRAY_ICON);
    }

    public ResourceBundle getResourceBundle(){
        if(resourceBundle == null){
            resourceBundle = ResourceBundle.getBundle(config.getString(CONFIG_RESOURCE_BUNDLE_NAME));
        }
        return resourceBundle;
    }
    
    public String getDateTimePattern(){
        return config.getString(CONFIG_DATETIME_PATTERN);
    }

    public DateTimeFormatter getDateTimeFormatter(){
        return DateTimeFormat.forPattern(getDateTimePattern());
    }

    //todo review/put somewhere else
    public TimeSheet loadTimeSheet(){
        if(timeSheet == null){ // initialize
            LOGGER.info("Loading timesheet data");
            File dataFile = Configuration.getInstance().getDataFile();
            if(!dataFile.exists()){
                timeSheet = new TimeSheet();
            }else{
                try {
                    timeSheet = (TimeSheet) jaxbUnmarshaller.unmarshal(dataFile);
                }catch (JAXBException e) {
                    LOGGER.error("Could not initialize JAXB",e);
                    timeSheet = new TimeSheet();
                }
            }
        }
        return timeSheet;
    }

    /**
     * Save the given timesheet to the configured location.
     * @param timeSheet the timesheet to save
     * @return true if success, false otherwise
     */
    public boolean saveTimeSheet(final TimeSheet timeSheet){
        boolean retVal = true;
        try {
            jaxbMarshaller.marshal(timeSheet,this.getDataFile());
        } catch (JAXBException e) {
            LOGGER.error("Could not save the timesheet data",e);  //To change body of catch statement use File | Settings | File Templates.
            retVal = false;
        }
        return retVal;
    }

}
