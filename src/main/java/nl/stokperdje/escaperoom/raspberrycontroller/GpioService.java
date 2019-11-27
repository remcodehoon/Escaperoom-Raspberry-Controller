package nl.stokperdje.escaperoom.raspberrycontroller;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import nl.stokperdje.escaperoom.raspberrycontroller.task.GpioPinTask;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;
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
    private Date lastPress = Date.from(Instant.now());

    // Alarm schakelkastje
    private final GpioPinDigitalInput schakelKastje = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02);

    // Beide lasers
    private final GpioPinDigitalOutput lasers = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_09);

    // Rookmachine
    private final GpioPinDigitalOutput rook = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07);

    // Hoofdverlichting
    private final GpioPinDigitalOutput hoofdverlichting = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_15);

    public GpioService() {
        System.out.println("Service instantiated");
        button.addListener((GpioPinListenerDigital) event -> {
            // High is ingedrukt, Low is weer uitgedrukt
            long diffTime = (lastPress.getTime() - Date.from(Instant.now()).getTime()) / 1000;
            if (event.getState().isHigh() && diffTime > 5) {
                // Todo: Http request. Server handle action
//                String url = "http://192.168.0.105:8081/barcode/test/" + event.getState();
//                restTemplate.getForEntity(url, String.class);
            }
        });

        schakelKastje.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                // Todo: HTTP request. Server should send another HTTP request to turn off lasers
            }
        });
    }

    public void toggleRook() {
        Timer timer = new Timer();
        timer.schedule(new GpioPinTask(this.rook), 0);
    }

    public void setHoofdverlichting(boolean on) {
        if (on)
            this.hoofdverlichting.high();
        else
            this.hoofdverlichting.low();
    }
}
