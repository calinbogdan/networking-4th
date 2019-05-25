import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class Sender {
    private int sequenceSize;
    private Receiver receiver;
    private static final double CORRUPT_FRAME_CHANCE = 0.7;

    public Sender(int sequenceSize) {
        this.sequenceSize = sequenceSize;
    }

    public void send(String message) {
        sequencesFromMessage(message, sequenceSize).forEach(sequence -> {
            receiver.setFrameReceivedListener(sequence);

            while (sequence.hasNotBeenSent()) {
                sequence.getFrames().forEach(frame -> {
                    receiver.onSendingFrame(frame, wouldBeCorrupt());
                });
            }

            receiver.onSequenceSent();
        });

        System.out.println(receiver.getFramesReceived());
    }

    private List<Sequence> sequencesFromMessage(String message, int sequenceSize) {
        String splitRegex = format("(?<=\\G.{%d})", sequenceSize);

        AtomicInteger counter = new AtomicInteger(0);
        return stream(message.split(splitRegex))
                .map(stringSequence -> new Sequence(stream(stringSequence.split(""))
                                        .map(sequenceCharacter -> new Frame(sequenceCharacter, counter.getAndIncrement()))
                                        .collect(toList())))
                .collect(toList());
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    private boolean wouldBeCorrupt() {
        return Math.random() > CORRUPT_FRAME_CHANCE;
    }
}
