package nl.stokperdje.escaperoom.raspberrycontroller.dto;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class Status {

    // Drukknop
    @Expose private boolean drukknop;
    @Expose private boolean drukknopLED;

    // Schakelkastje
    @Expose private boolean schakelkast;

    // Rookmachine
    @Expose private boolean rookmachineStroom;
    @Expose private boolean rookmachineToggle;

    // Verlichting
    @Expose private boolean verlichting;

    // Slot
    @Expose private boolean slot12V;
    @Expose private boolean slot3V;

    // Lasers
    @Expose private boolean lasers;

    // Sensoren
    @Expose private boolean sensor1;
    @Expose private boolean sensor2;
}
