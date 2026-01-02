package treptowkolleg.plot;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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
        } catch (Exception ignored) {
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
            double envelope = (voltage - V_MIN) / (V_MAX - V_MIN);
            double freq = F_MIN + envelope * (F_MAX - F_MIN);
            phase += 2.0 * Math.PI * freq / sampleRate;
            double sample = envelope * Math.sin(phase);
            sample = Math.max(-1.0, Math.min(1.0, sample));
            short val = (short) (sample * 32767);

            audioData[2 * i] = (byte) (val & 0xFF);
            audioData[2 * i + 1] = (byte) ((val >> 8) & 0xFF);
        }
        return audioData;
    }

    public static void sonifyAndSave(List<Double> values, String fileName) {
        if (values == null || values.isEmpty()) {
            return;
        }
        byte[] rawAudio = generateAudioData(values);

        if (rawAudio.length == 0) {
            return;
        }
        int sampleRate = 44100;
        int channels = 1;
        int bitsPerSample = 16;

        // JFileChooser erstellen
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Audio speichern");
        fileChooser.setSelectedFile(new File(fileName + ".wav"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("WAV Audio Datei (*.wav)", "wav"));

        int result = fileChooser.showSaveDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) {
            return; // Abbruch durch Benutzer
        }

        File file = fileChooser.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".wav")) {
            file = new File(file.getAbsolutePath() + ".wav");
        }
        try (FileOutputStream fos = new FileOutputStream(file)) {
            byte[] wavData = createWavHeaderAndData(rawAudio, sampleRate, channels, bitsPerSample);
            fos.write(wavData);

            System.out.println("Audio gespeichert: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Erzeugt einen vollständigen WAV-Bytestrom (Header + PCM-Daten).
     */
    private static byte[] createWavHeaderAndData(byte[] rawPcmData, int sampleRate, int channels, int bitsPerSample) {
        int byteRate = sampleRate * channels * bitsPerSample / 8;
        int blockAlign = channels * bitsPerSample / 8;
        int dataSize = rawPcmData.length;
        int totalSize = 36 + dataSize; // WAV-Header ist 44 Bytes, aber "RIFF"-Chunk enthält nur 36 + data

        ByteBuffer buffer = ByteBuffer.allocate(44 + dataSize);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        // RIFF Header
        buffer.put("RIFF".getBytes());
        buffer.putInt(totalSize);           // Größe ab nächstem Feld
        buffer.put("WAVE".getBytes());

        // fmt subchunk
        buffer.put("fmt ".getBytes());
        buffer.putInt(16);                  // Größe des fmt-Chunks
        buffer.putShort((short) 1);         // PCM = 1
        buffer.putShort((short) channels);
        buffer.putInt(sampleRate);
        buffer.putInt(byteRate);
        buffer.putShort((short) blockAlign);
        buffer.putShort((short) bitsPerSample);

        // data subchunk
        buffer.put("data".getBytes());
        buffer.putInt(dataSize);
        buffer.put(rawPcmData);

        return buffer.array();
    }
}
