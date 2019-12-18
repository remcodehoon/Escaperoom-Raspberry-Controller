package nl.stokperdje.escaperoom.raspberrycontroller;

import nl.stokperdje.escaperoom.raspberrycontroller.dto.IOStats;
import nl.stokperdje.escaperoom.raspberrycontroller.task.CloseLockTask;
import nl.stokperdje.escaperoom.raspberrycontroller.task.RookToggleTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

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

    // GPIO
    private RestTemplate restTemplate = new RestTemplate();
    private final GpioController gpio = GpioFactory.getInstance();

    // Gson
    private Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    // Rode knop
    private final GpioPinDigitalInput button = gpio.provisionDigitalInputPin(RaspiPin.GPIO_13);
//    private final GpioPinDigitalInput button = gpio.provisionDigitalInputPin(
//            RaspiPin.GPIO_13, PinPullResistance.PULL_DOWN
//    );
    private final GpioPinDigitalOutput buttonLED = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_12);
    private Date lastPress = Date.from(Instant.now());

    // Lasers
    private final GpioPinDigitalOutput lasers = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07);

    // Laser sensoren
    private final GpioPinDigitalInput sensor1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_25);
    private final GpioPinDigitalInput sensor2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_27);

    // Alarm schakelkastje
    private final GpioPinDigitalInput schakelKastje = gpio.provisionDigitalInputPin(RaspiPin.GPIO_29);

    // Rookmachine
    private final GpioPinDigitalOutput rookToggle = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02);
    private final GpioPinDigitalOutput rookStroom = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04);

    // Hoofdverlichting
    private final GpioPinDigitalOutput hoofdverlichting = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_15);

    // Lock
    private final GpioPinDigitalOutput lock12V = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00);
    private final GpioPinDigitalOutput lock3V = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01);

    public GpioService() {
        button.addListener((GpioPinListenerDigital) event -> {
            // High is ingedrukt, Low is weer uitgedrukt
            long diffTime = (Date.from(Instant.now()).getTime() - lastPress.getTime()) / 1000;
            if (event.getState().isHigh() && diffTime > 5) {
                this.sendIOStats();
                this.lastPress = Date.from(Instant.now());
            }
        });

        schakelKastje.addListener((GpioPinListenerDigital) event -> {
            if (event.getState().isHigh()) {
                this.sendIOStats();
            }
        });
    }

    /**
     * Rook Toggle: Klaar
     * Zet rook aan en weer uit
     */
    public void toggleRook() {
        Timer timer = new Timer();
        timer.schedule(new RookToggleTask(this.rookToggle), 0);
    }

    /**
     * Rook Stroom: Klaar
     * Zet stroom van rookmachine aan of uit
     * @param on
     */
    public void setRookStroom(boolean on) {
        if (on)
            this.rookStroom.high();
        else
            this.rookStroom.low();
    }

    /**
     * Laser Stroom: Klaar
     * Zet stroom van de lasers aan of uit
     * @param on
     */
    public void setLaserStroom(boolean on) {
        if (on)
            this.lasers.high();
        else
            this.lasers.low();
    }

    /**
     * Slot op slot: Klaar
     * Zet het slot op slot door kort 12V te geven en daarna 3V
     */
    public void closeLock() {
        Timer timer = new Timer();
        timer.schedule(new CloseLockTask(lock3V, lock12V), 0);
    }

    /**
     * Slot open: Klaar
     * Zet het slot open door de 12V en 3V laag te schrijven
     */
    public void openLock() {
        this.lock12V.low();
        this.lock3V.low();
    }

    /**
     * Hoofdverlichting: Klaar
     * Zet de hoofdverlichting aan of uit
     * @param on
     */
    public void setHoofdverlichting(boolean on) {
        if (on)
            this.hoofdverlichting.high();
        else
            this.hoofdverlichting.low();
    }

    /**
     * Button LED: Klaar
     * Zet de button LED aan of uit
     * @param on
     */
    public void setButtonLED(boolean on) {
        if (on)
            this.buttonLED.high();
        else
            this.buttonLED.low();
    }

    /**
     * IO Stats: Klaar
     * Stuurt de IOStats door naar serverapplicatie
     */
    public void sendIOStats() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(gson.toJson(this.getIOStats()), headers);
        String url = "http://192.168.2.5:8081/iostats";
        this.restTemplate.postForEntity(url, request, String.class);
    }

    /**
     * IO Stats: Klaar
     * Geeft de pinstatus van de meest belangrijke pins weer.
     * @return IOStats
     */
    public IOStats getIOStats() {
        IOStats stats = new IOStats();
        stats.setDrukknop(button.isHigh());
        stats.setDrukknopLED(buttonLED.isHigh());
        stats.setSchakelkast(schakelKastje.isHigh());
        stats.setRookmachineStroom(rookStroom.isHigh());
        stats.setRookmachineToggle(rookToggle.isHigh());
        stats.setVerlichting(hoofdverlichting.isHigh());
        stats.setSlot3V(lock3V.isHigh());
        stats.setSlot12V(lock12V.isHigh());
        stats.setLasers(lasers.isHigh());
        stats.setSensor1(sensor1.isHigh());
        stats.setSensor2(sensor2.isHigh());
        return stats;
    }
}
