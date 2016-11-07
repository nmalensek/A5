//package cs414.a5.nmalensk.delete;
//
//import cs414.a5.nmalensk.client.AdministratorClient;
//import cs414.a5.nmalensk.client.CustomerUI;
//
//import java.rmi.RemoteException;
//
//public class MainMenu {
//
//    private static int amount;

//    public static void main(String[] args) {
//        System.out.println("Enter the garage capacity:");
//        amount = setCapacity();
////        CustomerUI pg = new CustomerUI(amount);
//        while (true) {
//            mainMenu();
//            verifyInput(userInput(), pg);
//        }
//    }

//    private static int setCapacity() {
//        try{
//            return Integer.parseInt(userInput());
//        } catch (NumberFormatException e) {
//            System.out.println("Enter a number!");
//            return Integer.parseInt(userInput());
//        }
//    }

//    private static void mainMenu() {
//        System.out.println("Enter 1 to access parking garage as a Customer");
//        System.out.println("Enter 2 to access parking garage as a Garage Administrator");
//    }
//
//    public static void verifyInput(String input, CustomerUI park) {
//        if (input.equals("1")) {
//            System.out.println("Entering as Customer");
//            try {
//                park.initialMessage();
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        } else if (input.equals("2")) {
//            System.out.println("Entering as Garage Administrator");
//            AdministratorClient.administratorMenu(park);
//        } else {
//            System.out.println("Not a valid option, please re-enter");
//        }
//    }
//}

