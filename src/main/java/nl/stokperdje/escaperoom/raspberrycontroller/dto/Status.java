package nl.stokperdje.escaperoom.raspberrycontroller.dto;

import lombok.Data;

@Data
public class Status {

    private boolean sensor1Status;
    private boolean sensor2Status;
    private boolean schakelkastStatus;
    private boolean lockStatus;
    private boolean laserStatus;
    private boolean verlichtingStatus;

}
