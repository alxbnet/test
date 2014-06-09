package net.alxb.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alex Borisov
 */
public class Speedometer {

    public static final int SPEED_LIMIT_KPH = 90;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private int speedInKph;

    public int getSpeedInKph() {
        return speedInKph;
    }

    public void setSpeedInKph(int kph) {
        checkSpeedLimit(kph);
        this.speedInKph = kph;
    }

    private void checkSpeedLimit(int kph) {
        if (kph > SPEED_LIMIT_KPH) {
            logger.warn("Speed limit exceeded. Limit {}. Speed {}.", SPEED_LIMIT_KPH, kph);
        }
    }
}
