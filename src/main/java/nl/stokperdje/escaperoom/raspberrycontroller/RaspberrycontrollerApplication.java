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

	@EventListener(ApplicationReadyEvent.class)
	public void registerBarcodeListener() {
		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}

		GlobalScreen.addNativeKeyListener(new BackgroundKeyListener());
	}

	@PreDestroy
	public void CloseGPIO() {
		GpioController gpio = GpioFactory.getInstance();
		gpio.shutdown();
	}


}
