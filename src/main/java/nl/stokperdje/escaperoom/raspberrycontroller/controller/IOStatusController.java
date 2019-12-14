package nl.stokperdje.escaperoom.raspberrycontroller.controller;

import nl.stokperdje.escaperoom.raspberrycontroller.GpioService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/iostats")
public class IOStatusController {

    @Autowired
    private GpioService service;

    private Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    /**
     * IOStatus: Klaar
     * Geeft de pinstatus van de meest belangrijke pins weer.
     * @return IOStats
     */
    @GetMapping
    public ResponseEntity getStatus() {
        return new ResponseEntity<>(gson.toJson(service.getIOStats()), HttpStatus.OK);
    }
}
