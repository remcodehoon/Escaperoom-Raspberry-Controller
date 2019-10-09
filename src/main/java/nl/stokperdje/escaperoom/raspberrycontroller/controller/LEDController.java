package nl.stokperdje.escaperoom.raspberrycontroller.controller;

import com.pi4j.io.gpio.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/led")
public class LEDController {

    private final GpioController gpio = GpioFactory.getInstance();
    private final GpioPinDigitalOutput led = gpio.provisionDigitalOutputPin(
            RaspiPin.GPIO_00, "Blinking LED", PinState.HIGH
    );

    @GetMapping(value = "/toggle")
    public ResponseEntity toggle() {
        led.toggle();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
