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

    @GetMapping
    public ResponseEntity toggleRook() {
        this.service.toggleRook();
        System.out.println("lalal");
        return new ResponseEntity<>(HttpStatus.OK);


    }
}
