package pr1.a07.plot;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import java.util.List;

public class Sonifier {

    public static void sonify(List<Double> diffValues) {
        if (diffValues == null || diffValues.isEmpty()) {
            return;
        }
        byte[] audioData = generateAudioData(diffValues);
        int sampleRate = 44100;

        try {
            AudioFormat format = new AudioFormat(sampleRate, 16, 1, true,
                    false);
            SourceDataLine line = AudioSystem.getSourceDataLine(format);

            line.open(format);
            line.start();
            line.write(audioData, 0, audioData.length);
            line.drain();
            line.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] generateAudioData(List<Double> data) {
        if (data == null || data.isEmpty()) {
            return new byte[0];
        }
        final double V_MIN = 0.0;
        final double V_MAX = 5000.0;
        final double F_MIN = 60.0;
        final double F_MAX = 1800.0;
        final int MS_PER_POINT = 20;
        int durationMs = Math.max(500, data.size() * MS_PER_POINT);
        int sampleRate = 44100;
        int totalSamples = (int) (sampleRate * durationMs / 1000.0);
        byte[] audioData = new byte[totalSamples * 2];
        double phase = 0.0;

        for (int i = 0; i < totalSamples; i++) {
            double t = (double) i / totalSamples;
            double pos = t * (data.size() - 1);
            int idx = (int) pos;
            double frac = pos - idx;
            double voltage = (idx >= data.size() - 1)
                    ? data.get(data.size() - 1)
                    : data.get(idx) * (1 - frac) + data.get(idx + 1) * frac;
            voltage = Math.max(V_MIN, Math.min(V_MAX, voltage));
            double norm = (voltage - V_MIN) / (V_MAX - V_MIN);
            double freq = F_MIN + norm * (F_MAX - F_MIN);
            double envelope = norm;
            phase += 2.0 * Math.PI * freq / sampleRate;
            double sample = envelope * Math.sin(phase);
            sample = Math.max(-1.0, Math.min(1.0, sample));
            short val = (short) (sample * 32767);

            audioData[2 * i] = (byte) (val & 0xFF);
            audioData[2 * i + 1] = (byte) ((val >> 8) & 0xFF);
        }
        return audioData;
    }
}
