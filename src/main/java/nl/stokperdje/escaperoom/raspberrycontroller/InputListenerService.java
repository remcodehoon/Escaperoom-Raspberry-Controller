package nl.stokperdje.escaperoom.raspberrycontroller;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InputListenerService {

    private RestTemplate restTemplate = new RestTemplate();

    private final GpioController gpio = GpioFactory.getInstance();
    private final GpioPinDigitalInput button = gpio.provisionDigitalInputPin(
            RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN
    );

    public InputListenerService() {
        System.out.println("Service instantiated");
        button.addListener((GpioPinListenerDigital) event -> {
            String url = "http://192.168.2.51:8081/barcode/test/" + event.getState();
            restTemplate.getForEntity(url, String.class);
        });
    }
}
