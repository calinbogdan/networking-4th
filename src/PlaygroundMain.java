import java.util.*;

public class PlaygroundMain {
    public static void main(String[] args) {
        List<String> strings = new ArrayList<>(Arrays.asList("1", "2", "3", "4"));

        ListIterator<String> stringIterator = strings.listIterator();

        while (stringIterator.hasNext()) {
            stringIterator.add("$");
            String theString = stringIterator.next();

            System.out.println(strings);
        }
    }
}
