package nl.stokperdje.escaperoom.raspberrycontroller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import nl.stokperdje.escaperoom.raspberrycontroller.dto.Status;
import nl.stokperdje.escaperoom.raspberrycontroller.task.GpioPinTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Date;
import java.util.Timer;

@Service
public class GpioService {

    private RestTemplate restTemplate = new RestTemplate();
    private final GpioController gpio = GpioFactory.getInstance();

    @Value("${stokperdje.escaperoom.server.ip}")
    private String serverIP;

    private Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    // Rode knop
    private final GpioPinDigitalInput button = gpio.provisionDigitalInputPin(
            RaspiPin.GPIO_03, PinPullResistance.PULL_DOWN
    );
    private Date lastPress = Date.from(Instant.now());

    // Laser sensor 1
    private final GpioPinDigitalInput sensor1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_10);

    // Laser sensor 1
    private final GpioPinDigitalInput sensor2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_09);

    // Alarm schakelkastje
    private final GpioPinDigitalInput schakelKastje = gpio.provisionDigitalInputPin(RaspiPin.GPIO_08);

    // Beide lasers
    private final GpioPinDigitalOutput lasers = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07);

    // Rookmachine
    private final GpioPinDigitalOutput rookToggle = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02);
    private final GpioPinDigitalOutput rookStroom = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04);

    // Hoofdverlichting
    private final GpioPinDigitalOutput hoofdverlichting = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_15);

    // Lock
    private final GpioPinDigitalOutput lock12V = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00);
    private final GpioPinDigitalOutput lock3V = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01);

    public GpioService() {
        System.out.println("Service instantiated. Server IP is: " + this.serverIP);
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

    public Status getIOStats() {
        Status status = new Status();
        status.setSensor1Status(sensor1.isHigh());
        status.setSensor2Status(sensor2.isHigh());
        status.setSchakelkastStatus(schakelKastje.isHigh());
        status.setLaserStatus(lasers.isHigh());
        status.setVerlichtingStatus(hoofdverlichting.isHigh());
        status.setLockStatus(lock12V.isHigh());
        return status;
    }

    public void toggleRook() {
        Timer timer = new Timer();
        timer.schedule(new GpioPinTask(this.rookToggle), 0);
    }

    public void setRookStroomAan() {
        this.rookStroom.high();
    }

    public void setRookStroomUit() {
        this.rookStroom.low();
    }

    public void setLasersAan() {
        this.lasers.high();
    }

    public void setLasersUit() {
        this.lasers.low();
    }

    public void closeLock() {
        this.lock3V.high();
        this.lock12V.high();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.lock12V.low();
        }
    }

    public void openLock() {
        this.lock12V.low();
        this.lock3V.low();
    }

    public void setHoofdverlichting(boolean on) {
        if (on)
            this.hoofdverlichting.low();
        else
            this.hoofdverlichting.high();
    }

    // Todo: Testen
    public void TestSendIOStats() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(gson.toJson(this.getIOStats()), headers);
        String url = "http://192.168.0.105:8081/iostats";
        this.restTemplate.postForEntity(url, request, String.class);
    }
}
