package nl.stokperdje.escaperoom.raspberrycontroller.controller;

import nl.stokperdje.escaperoom.raspberrycontroller.GpioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/rook")
public class RookController {

    @Autowired
    private GpioService service;

    /**
     * Rook toggle: Klaar
     * Geeft 5 seconden rook
     * @return ResponseEntity
     */
    @GetMapping("/toggle")
    public ResponseEntity toggleRook() {
        this.service.toggleRook();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Rook stroom: Klaar
     * Zet stroom van rookmachine aan
     * @return ResponseEntity
     */
    @GetMapping("/aan")
    public ResponseEntity rookAan() {
        this.service.setRookStroom(true);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Rook stroom: Klaar
     * Zet stroom van rookmachine uit
     * @return ResponseEntity
     */
    @GetMapping("/uit")
    public ResponseEntity rookUit() {
        this.service.setRookStroom(false);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
