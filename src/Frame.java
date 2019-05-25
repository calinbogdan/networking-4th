public class Frame implements Comparable<Frame> {
    private String content;
    private int index;

    public Frame(String content, int index) {
        this.content = content;
        this.index = index;
    }

    public String getContent() {
        return content;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public int compareTo(Frame o) {
        return Integer.compare(index, o.index);
    }
}
