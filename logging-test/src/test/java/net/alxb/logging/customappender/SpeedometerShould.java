package net.alxb.logging.customappender;

import net.alxb.logging.Speedometer;
import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static net.alxb.logging.Speedometer.SPEED_LIMIT_KPH;
import static org.apache.log4j.Level.WARN;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Test for {@code Speedometer} class
 *
 * @author Alex Borisov
 */
public class SpeedometerShould {

    private Speedometer speedometer = new Speedometer();

    private Appender appender;
    private List<LoggingEvent> loggingEvents = newArrayList();

    @Before
    public void addCustomAppender() {

        Logger logger = Logger.getLogger(Speedometer.class);
        appender = new AppenderSkeleton() {
            @Override
            protected void append(LoggingEvent event) {
                loggingEvents.add(event);
            }

            @Override
            public void close() {}

            @Override
            public boolean requiresLayout() {
                return false;
            }
        };
        logger.addAppender(appender);
    }

    @Test
    public void writeWarnIfLimitExceeded() {
        int speedInKph = 95;

        speedometer.setSpeedInKph(speedInKph);

        assertThat(loggingEvents).hasSize(1);

        LoggingEvent event = loggingEvents.get(0);
        String eventMessage = event.getMessage().toString();

        assertThat(event.getLevel()).isEqualTo(WARN);
        assertThat(eventMessage)
                .contains("Speed limit exceeded")
                .contains(Integer.toString(speedInKph)).contains(Integer.toString(SPEED_LIMIT_KPH));
    }

    @Test
    public void notWriteLogIfLimitNotExceeded() {
        int speedInKph = 87;

        speedometer.setSpeedInKph(speedInKph);

        assertThat(loggingEvents).isEmpty();
    }

    @After
    public void removeOutputAppender() {
        Logger.getLogger(Speedometer.class).removeAppender(appender);
    }

}
