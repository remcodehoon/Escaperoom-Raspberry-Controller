package nl.stokperdje.escaperoom.raspberrycontroller.controller;

import nl.stokperdje.escaperoom.raspberrycontroller.GpioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/lasers")
public class LaserController {

    @Autowired
    private GpioService service;

    @GetMapping("/uit")
    public ResponseEntity laserUit() {
        this.service.setLasersUit();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/aan")
    public ResponseEntity laserAan() {
        this.service.setLasersAan();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
