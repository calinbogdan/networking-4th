import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class Receiver implements OnSendingFrameListener, OnSequenceSentListener {

    private OnFrameReceivedListener frameSentListener;
    private List<Frame> framesReceived = new ArrayList<>();

    public void setFrameReceivedListener(OnFrameReceivedListener frameSentListener) {
        this.frameSentListener = frameSentListener;
    }

    @Override
    public void onSendingFrame(Frame frame, boolean isCorrupt) {
        if (isCorrupt) {
            System.out.println(format("NACK %s", frame.getContent()));
        } else {
            System.out.println(format("ACK %s", frame.getContent()));
            framesReceived.add(frame);

            if (frameSentListener != null) {
                frameSentListener.onFrameReceived(frame);
            }
        }
    }

    @Override
    public void onSequenceSent() {
        framesReceived.sort(Comparator.comparingInt(Frame::getIndex));
    }

    public String getFramesReceived() {
        return framesReceived.stream().map(Frame::getContent).collect(Collectors.joining());
    }
}
