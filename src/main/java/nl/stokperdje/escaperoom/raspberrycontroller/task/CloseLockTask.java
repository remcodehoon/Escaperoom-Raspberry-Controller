package nl.stokperdje.escaperoom.raspberrycontroller.task;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

import java.util.TimerTask;

public class CloseLockTask extends TimerTask {

    private final GpioPinDigitalOutput pin3V;
    private final GpioPinDigitalOutput pin12V;

    public CloseLockTask(GpioPinDigitalOutput pin3V, GpioPinDigitalOutput pin12V) {
        this.pin3V = pin3V;
        this.pin12V = pin12V;
    }

    private void closeLock() {
        this.pin3V.high();
        this.pin12V.high();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.pin12V.low();
            this.cancel();
        }
    }

    @Override
    public void run() {
        this.closeLock();
    }
}
