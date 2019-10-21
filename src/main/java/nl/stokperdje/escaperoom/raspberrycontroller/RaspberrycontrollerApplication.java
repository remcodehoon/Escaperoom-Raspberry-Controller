package nl.stokperdje.escaperoom.raspberrycontroller;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import nl.stokperdje.escaperoom.raspberrycontroller.barcode.BackgroundKeyListener;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import javax.annotation.PreDestroy;

@SpringBootApplication
public class RaspberrycontrollerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RaspberrycontrollerApplication.class, args);
	}

	@PreDestroy
	public void CloseGPIO() {
		GpioController gpio = GpioFactory.getInstance();
		gpio.shutdown();
	}


}
