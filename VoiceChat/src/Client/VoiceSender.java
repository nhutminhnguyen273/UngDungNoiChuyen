package Client;

import javax.sound.sampled.*;
import java.net.*;

public class VoiceSender extends Thread {
    private final String ip;
    private final int port;
    private boolean running = true;
    
    public VoiceSender(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
    
        @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket()) {
            AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);

            microphone.open(format);
            microphone.start();

            byte[] buffer = new byte[1024];
            while (running) {
                int bytesRead = microphone.read(buffer, 0, buffer.length);
                DatagramPacket packet = new DatagramPacket(buffer, bytesRead, InetAddress.getByName(ip), port);
                socket.send(packet);
            }

            microphone.stop();
            microphone.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void stopSending() {
        running = false;
    }
}
