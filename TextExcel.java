
import java.io.FileNotFoundException;
import java.util.Scanner;

// Update this file with your own code.

public class TextExcel
{

    public static void main(String[] args)
    {
        Grid sheet = new Spreadsheet();

        System.out.println(sheet.getGridText());

        Scanner scanner = new Scanner(System.in);
        String inputLine = scanner.nextLine();
        while (!inputLine.equalsIgnoreCase("quit"))
        {
            String outputLine = sheet.processCommand(inputLine);
            System.out.println(outputLine);
            inputLine = scanner.nextLine();
        }
    }
}
