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

    /**
     * Hoofdverlichting stroom: Klaar
     * Zet hoofdverlichting aan
     * @return ResponseEntity
     */
    @GetMapping("/hoofdverlichting/aan")
    public ResponseEntity hoofdverlichtingAan() {
        this.service.setHoofdverlichting(true);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Hoofdverlichting stroom: Klaar
     * Zet hoofdverlichting uit
     * @return ResponseEntity
     */
    @GetMapping("/hoofdverlichting/uit")
    public ResponseEntity hoofdverlichtingUit() {
        this.service.setHoofdverlichting(false);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Knop LED stroom: Klaar
     * Zet Knop LED aan
     * @return ResponseEntity
     */
    @GetMapping("/knopled/aan")
    public ResponseEntity knopledAan() {
        this.service.setButtonLED(true);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Knop LED stroom: Klaar
     * Zet Knop LED uit
     * @return ResponseEntity
     */
    @GetMapping("/knopled/uit")
    public ResponseEntity knopledUit() {
        this.service.setButtonLED(false);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
