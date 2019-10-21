package nl.stokperdje.escaperoom.raspberrycontroller.barcode;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.awt.event.KeyEvent;

public class BackgroundKeyListener implements NativeKeyListener {

    private String code = "";
    private final HttpRequestSender client;
    private boolean isScanningBarcode = false;

    public BackgroundKeyListener() {
        this.client = new HttpRequestSender();
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
        System.out.println("Typed : " + KeyEvent.getKeyText(nativeKeyEvent.getRawCode()));
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        int keycode = nativeKeyEvent.getKeyCode();
        if (keycode == 69) {
            this.code = "";
            this.isScanningBarcode = true;
        } else if (keycode == 28 && isScanningBarcode) {
            client.sendBarcode(this.code);
        } else {
            if (isScanningBarcode) {
                String key = NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode());
                this.code = this.code + key;
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
//        System.out.println("Released : " + NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));
    }
}
