package nl.stokperdje.escaperoom.raspberrycontroller;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import nl.stokperdje.escaperoom.raspberrycontroller.task.GpioPinTask;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Timer;
import java.util.TimerTask;

@Service
public class GpioService {

    private RestTemplate restTemplate = new RestTemplate();
    private final GpioController gpio = GpioFactory.getInstance();

    // Rode knop
    private final GpioPinDigitalInput button = gpio.provisionDigitalInputPin(
            RaspiPin.GPIO_03, PinPullResistance.PULL_DOWN
    );

    // Alarm schakelkastje
    private final GpioPinDigitalInput schakelKastje = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02);

    // Beide lasers
    private final GpioPinDigitalOutput lasers = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_09);

    // Rookmachine
    private final GpioPinDigitalOutput rook = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07);

    public GpioService() {
        System.out.println("Service instantiated");
        button.addListener((GpioPinListenerDigital) event -> {
            // High is ingedrukt, Low is weer uitgedrukt
            if (event.getState().isHigh()) {
                // Todo: Http request
//                String url = "http://192.168.0.105:8081/barcode/test/" + event.getState();
//                restTemplate.getForEntity(url, String.class);
            }
        });

        schakelKastje.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                lasers.high();
            } else {
                lasers.low();
            }
        });
    }

    public void toggleRook() {
        Timer timer = new Timer();
        timer.schedule(new GpioPinTask(this.rook) {
            @Override
            public void run() {
                
            }
        }, 0);
    }
}
