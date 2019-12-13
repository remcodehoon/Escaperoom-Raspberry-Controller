package nl.stokperdje.escaperoom.raspberrycontroller.controller;

import nl.stokperdje.escaperoom.raspberrycontroller.GpioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/slot")
public class SlotController {

    @Autowired
    private GpioService service;

    @GetMapping("/open")
    public ResponseEntity slotOpen() {
        this.service.openLock();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/dicht")
    public ResponseEntity slotDicht() {
        this.service.closeLock();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
