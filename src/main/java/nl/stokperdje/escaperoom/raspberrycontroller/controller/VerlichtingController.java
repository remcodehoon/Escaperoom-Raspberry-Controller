package nl.stokperdje.escaperoom.raspberrycontroller.controller;

import nl.stokperdje.escaperoom.raspberrycontroller.GpioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/verlichting")
public class VerlichtingController {

    @Autowired
    private GpioService service;

    @GetMapping("/hoofdverlichting/aan")
    public ResponseEntity hoofdverlichtingAan() {
        this.service.setHoofdverlichting(true);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/hoofdverlichting/uit")
    public ResponseEntity hoofdverlichtingUit() {
        this.service.setHoofdverlichting(false);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}