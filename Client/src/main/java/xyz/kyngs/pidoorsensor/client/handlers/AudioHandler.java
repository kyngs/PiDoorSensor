package xyz.kyngs.pidoorsensor.client.handlers;

import xyz.kyngs.pidoorsensor.client.Client;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class AudioHandler extends AbstractHandler {

    private Clip clip;

    public AudioHandler(Client client) {
        super(client);
    }

    public void alert() {

        try {
            var stream = AudioSystem.getAudioInputStream(ClassLoader.getSystemResource("alert.wav"));

            if (clip == null) {
                clip = AudioSystem.getClip();
            } else {
                clip.close();
            }

            clip.open(stream);
            clip.setFramePosition(0);
            clip.start();

            stream.close();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void shutdown() {

    }
}
