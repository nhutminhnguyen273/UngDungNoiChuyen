package Client;

import javax.sound.sampled.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class VoiceReceiver extends Thread {
    private final int port;
    private boolean running = true;
    
    public VoiceReceiver(int port) {
        this.port = port;
    }
        @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket(port)) {
            AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine speakers = (SourceDataLine) AudioSystem.getLine(info);

            speakers.open(format);
            speakers.start();

            byte[] buffer = new byte[1024];
            while (running) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                speakers.write(packet.getData(), 0, packet.getLength());
            }

            speakers.stop();
            speakers.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopReceiving() {
        running = false;
    }
}