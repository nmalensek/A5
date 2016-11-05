package cs414.a5.nmalensk.client;

public class AdministratorView {

    public static void administratorMenu(ParkingGarage pg) {
        while(verifyInput(pg)) {
            //show menu
        }
    }

    public static void mainOptions() {
        System.out.println("Enter 1 to analyze occupancy by hour");
        System.out.println("Enter 2 to analyze sales");
        System.out.println("Enter q to go back");
    }

    private static boolean verifyInput(ParkingGarage park) {
        mainOptions();
        String decision = TextInput.userInput();
//        if (decision.equals("1")) { park.generateCustomReport("occupancy"); }
//        if (decision.equals("2")) { park.generateCustomReport("sales"); }
        if (decision.equals("q")) { return false; }
        return true;
    }
}
