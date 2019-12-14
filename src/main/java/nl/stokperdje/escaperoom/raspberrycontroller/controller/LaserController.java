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

    /**
     * Laser stroom: Klaar
     * Zet stroom van lasers aan
     * @return ResponseEntity
     */
    @GetMapping("/aan")
    public ResponseEntity laserAan() {
        this.service.setLaserStroom(true);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Laser stroom: Klaar
     * Zet stroom van lasers uit
     * @return ResponseEntity
     */
    @GetMapping("/uit")
    public ResponseEntity laserUit() {
        this.service.setLaserStroom(false);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
