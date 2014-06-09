package net.alxb.logging.writerappender;

import net.alxb.logging.Speedometer;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.WriterAppender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

import static net.alxb.logging.Speedometer.SPEED_LIMIT_KPH;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Test for {@code Speedometer} class
 *
 * @author Alex Borisov
 */
public class SpeedometerShould {

    private Speedometer speedometer = new Speedometer();

    private StringWriter stringWriter;
    private WriterAppender appender;

    @Before
    public void addOutputAppender() throws IOException {
        stringWriter = new StringWriter();
        appender = new WriterAppender(new SimpleLayout(), stringWriter);

        Logger logger = Logger.getLogger(Speedometer.class);
        logger.addAppender(appender);
    }

    @Test
    public void writeWarnIfLimitExceeded() {
        int speedInKph = 91;

        speedometer.setSpeedInKph(speedInKph);

        assertThat(readLog())
                .containsIgnoringCase("WARN")
                .contains("Speed limit exceeded")
                .contains(Integer.toString(speedInKph)).contains(Integer.toString(SPEED_LIMIT_KPH));
    }

    @Test
    public void notWriteLogIfLimitNotExceeded() {
        int speedInKph = 89;

        speedometer.setSpeedInKph(speedInKph);

        assertThat(readLog()).isEmpty();
    }

    @After
    public void removeOutputAppender() throws IOException {
        Logger.getLogger(Speedometer.class).removeAppender(appender);

        stringWriter.close();
    }

    private String readLog() {
        return stringWriter.getBuffer().toString();
    }

}
