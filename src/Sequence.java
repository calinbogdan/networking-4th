import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class Sequence implements OnFrameReceivedListener {
    private Map<Frame, Boolean> framesToSend;

    public Sequence(List<Frame> framesToSend) {
        this.framesToSend = framesToSend.stream()
                                    .collect(toMap(frame -> frame, frame -> false));
    }

    @Override
    public void onFrameReceived(Frame frame) {
        // frame has been sent
        framesToSend.put(frame, true);
    }

    public boolean hasNotBeenSent() {
        return framesToSend.values().stream().anyMatch(value -> !value);
    }

    public List<Frame> getFrames() {
        return framesToSend.entrySet().stream()
                .filter(entry -> !entry.getValue())
                .map(Map.Entry::getKey)
                .collect(toList());
    }

}
