package xyz.kyngs.pidoorsensor.rpi.handlers;

import com.pi4j.io.gpio.*;
import xyz.kyngs.pidoorsensor.rpi.RaspberryPi;

public class PiHandler extends AbstractHandler {

    private final GpioController gpio;
    private final GpioPinDigitalInput doorSwitch;

    public PiHandler(RaspberryPi pi) {
        super(pi);
        GpioFactory.setDefaultProvider(new RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING));
        gpio = GpioFactory.getInstance();

        doorSwitch = gpio.provisionDigitalInputPin(RaspiPin.getPinByAddress(pi.getPin()), "door_switch", PinPullResistance.PULL_DOWN);
    }

    public GpioPinDigitalInput getDoorSwitch() {
        return doorSwitch;
    }

    @Override
    public void shutdown() {
        gpio.shutdown();
    }
}
