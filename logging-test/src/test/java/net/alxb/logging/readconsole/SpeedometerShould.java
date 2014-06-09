package net.alxb.logging.readconsole;

import net.alxb.logging.Speedometer;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import static net.alxb.logging.Speedometer.SPEED_LIMIT_KPH;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Test for {@code Speedometer} class
 *
 * @author Alex Borisov
 */
public class SpeedometerShould {

    private Speedometer speedometer = new Speedometer();

    private ByteArrayOutputStream loggerOutputStream;
    private PrintStream originalOut;

    @Before
    public void configureLogger() throws IOException {
        configureConsoleStream();
        configureLoggerToConsole();
    }

    private void configureConsoleStream() throws IOException {
        originalOut = System.out;

        loggerOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(loggerOutputStream));
    }

    private void configureLoggerToConsole() {
        LogManager.resetConfiguration();

        Properties props = new Properties();
        props.setProperty("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
        props.setProperty("log4j.appender.stdout.Target", "System.out");
        props.setProperty("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
        props.setProperty("log4j.appender.stdout.layout.ConversionPattern", "%p: %m%n");

        props.setProperty("log4j.rootLogger", "INFO, stdout");

        PropertyConfigurator.configure(props);
    }

    @Test
    public void writeWarnIfLimitExceeded() throws IOException {
        int speedInKph = 91;

        speedometer.setSpeedInKph(speedInKph);

        assertThat(readConsole())
                .containsIgnoringCase("WARN")
                .contains("Speed limit exceeded")
                .contains(Integer.toString(speedInKph)).contains(Integer.toString(SPEED_LIMIT_KPH));
    }

    @Test
    public void notWriteLogIfLimitNotExceeded() throws IOException {
        int speedInKph = 89;

        speedometer.setSpeedInKph(speedInKph);
        readConsole();

        assertThat(readConsole()).isEmpty();
    }

    @After
    public void resetLoggerConfiguration() throws IOException {
        LogManager.resetConfiguration();
    }

    @After
    public void resetSystemOut() throws IOException {
        System.setOut(originalOut);
        loggerOutputStream.close();
    }

    private String readConsole() throws IOException {
        return loggerOutputStream.toString();
    }

}