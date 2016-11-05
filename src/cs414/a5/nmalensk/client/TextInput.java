package cs414.a5.nmalensk.client;

import java.util.Scanner;

public class TextInput {

    public static String userInput() {
        Scanner input = new Scanner(System.in);
        String selection;

        selection = input.next();
        return selection;
    }

    public static void pressEnter() {
        while (true) {
            Scanner input = new Scanner(System.in);
            String selection;

            selection = input.nextLine();
            if (selection.equals("")) {
                break;
            } else {
                System.out.println("Please press enter");
            }
        }
    }
}
