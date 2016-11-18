package cs414.a5.nmalensk.deprecated.domain_logic;

public class OccupancySign {

    private int totalSpaces;
    private int filledSpaces = 0;

    public OccupancySign(int totalSpaces) {
        this.totalSpaces = totalSpaces;
    }

    public void welcomeMessage() {
        System.out.println("Welcome to the parking garage!\n");
        System.out.println("Space available in garage: " + getOpenSpaces());
        System.out.println();
    }

    public int getOpenSpaces() {
        int openSpaces = (this.totalSpaces - filledSpaces);
        return openSpaces;
    }

    public void addOpenSpaces(int newlyOpenSpace) {
        filledSpaces -= newlyOpenSpace;
    }
}
