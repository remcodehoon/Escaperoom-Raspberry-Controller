package nl.stokperdje.escaperoom.raspberrycontroller.task;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

import java.util.TimerTask;

public class GpioPinTask extends TimerTask {

    GpioPinDigitalOutput pin;

    public GpioPinTask(GpioPinDigitalOutput pin) {
        this.pin = pin;
    }

    private void toggleRook() {
        this.pin.high();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        } finally {
            this.pin.low();
            this.cancel();
        }
    }

    @Override
    public void run() {
        this.toggleRook();
    }
}
