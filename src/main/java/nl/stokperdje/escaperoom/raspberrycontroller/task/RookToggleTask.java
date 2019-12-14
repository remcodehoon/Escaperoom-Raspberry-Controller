package nl.stokperdje.escaperoom.raspberrycontroller.task;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

import java.util.TimerTask;

public class RookToggleTask extends TimerTask {

    private final GpioPinDigitalOutput pin;

    public RookToggleTask(GpioPinDigitalOutput pin) {
        this.pin = pin;
    }

    private void toggleRook() {
        this.pin.high();
        try {
            Thread.sleep(5000);
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
