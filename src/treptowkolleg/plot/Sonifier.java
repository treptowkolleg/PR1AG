/*
 * Copyright (C) 2025 Benjamin Wagner
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
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

    /**
     * Plays an audible sonification of a list of numeric values by mapping them to a frequency-modulated audio tone.
     * The sonification is rendered in real time using the system's default audio output.
     *
     * <p>This method does nothing if the input list is null or empty.</p>
     *
     * @param diffValues the list of double values to sonify; typically representing voltage differences or signal deviations
     */
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

    /**
     * Sonifies a list of numeric values and saves the resulting audio as a WAV file.
     * A file chooser dialog is presented to the user for selecting the save location and filename.
     * If the user cancels the dialog or the input data is invalid, no file is written.
     *
     * <p>The output file is always saved with a {@code .wav} extension.</p>
     *
     * @param values the list of double values to sonify and save; must not be null or empty
     * @param fileName the initial filename suggestion for the save dialog (without extension)
     */
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
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Audio speichern");
        fileChooser.setSelectedFile(new File(fileName + ".wav"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("WAV Audio Datei (*.wav)", "wav"));
        int result = fileChooser.showSaveDialog(null);

        if (result != JFileChooser.APPROVE_OPTION) {
            return;
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

    private static byte[] generateAudioData(List<Double> data) {
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

    private static byte[] createWavHeaderAndData(byte[] rawPcmData, int sampleRate, int channels, int bitsPerSample) {
        int byteRate = sampleRate * channels * bitsPerSample / 8;
        int blockAlign = channels * bitsPerSample / 8;
        int dataSize = rawPcmData.length;
        int totalSize = 36 + dataSize;
        ByteBuffer buffer = ByteBuffer.allocate(44 + dataSize);

        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put("RIFF".getBytes());
        buffer.putInt(totalSize);
        buffer.put("WAVE".getBytes());
        buffer.put("fmt ".getBytes());
        buffer.putInt(16);
        buffer.putShort((short) 1);
        buffer.putShort((short) channels);
        buffer.putInt(sampleRate);
        buffer.putInt(byteRate);
        buffer.putShort((short) blockAlign);
        buffer.putShort((short) bitsPerSample);
        buffer.put("data".getBytes());
        buffer.putInt(dataSize);
        buffer.put(rawPcmData);
        return buffer.array();
    }
}
