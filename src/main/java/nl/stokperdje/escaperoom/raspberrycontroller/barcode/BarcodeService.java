package nl.stokperdje.escaperoom.raspberrycontroller.barcode;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.springframework.stereotype.Service;

@Service
public class BarcodeService {

    public BarcodeService() {
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
}
