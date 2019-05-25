import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner consoleScanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Sequence size: ");
        int sequenceSize = consoleScanner.nextInt();

        System.out.print("Message to be sent: ");
        String message = consoleScanner.next();

        Sender sender = new Sender(sequenceSize);
        Receiver receiver = new Receiver();

        sender.setReceiver(receiver);
        sender.send(message);
    }
}

