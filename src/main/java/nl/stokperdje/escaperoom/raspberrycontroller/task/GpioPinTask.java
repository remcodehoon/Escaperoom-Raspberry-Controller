package nl.stokperdje.escaperoom.raspberrycontroller.task;

import com.pi4j.io.gpio.GpioPinDigital;

import java.util.TimerTask;

public class GpioPinTask extends TimerTask {

    GpioPinDigital pin;

    public GpioPinTask(GpioPinDigital pin) {
        this.pin = pin;
    }

    @Override
    public void run() {

    }
}
