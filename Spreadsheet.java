import java.util.*;
// Update this file with your own code.
public class Spreadsheet implements Grid
{   
    Cell [][] sheet;

    public Spreadsheet (){
        sheet = new Cell [20][12];//2D array of cells all containing EmptyCell objects
        for (int x = 0; x < sheet.length; x ++) { //every row
            for(int y = 0; y < sheet[x].length; y ++) {// every column
                sheet[x][y] = new EmptyCell(); //initialize to non-null
            }
        }
    }

    @Override
    public String processCommand(String command)
    {
        String result = "";
        int equalSign = command.indexOf("=");
        if (command.length() == 2 || command.length() == 3){ // basic way to check if comm is A1
            Location s = new SpreadsheetLocation(command);
            return getCell(s).fullCellText();
        }
        if (command.contains("=")) {
            if (command.contains("%")) {//percent
                String input = command.substring(equalSign + 2, command.length() - 1);//doesn't include percent sign
                String place = command.substring(0, command.indexOf(" "));
                Location s = new SpreadsheetLocation(place);
                sheet[s.getRow()][s.getCol()] = new PercentCell(input);
                return getGridText();
            }
            if (command.contains("\"")){//string
                String place = command.substring(0, equalSign - 1);
                String val = command.substring(equalSign + 3, command.length()- 1);
                Location s = new SpreadsheetLocation(place);
                sheet[s.getRow()][s.getCol()] = new TextCell(val);//val
                return getGridText();
            }            
            if (command.contains("( ")) {//formula cell, passes "(sifhsfh)"
                String place = command.substring(0, equalSign - 1);
                String val = command.substring(command.indexOf("(") + 2, command.length() - 2);//TEST: pass just sifhsfh
                Location s = new SpreadsheetLocation(place);
                sheet[s.getRow()][s.getCol()] = new FormulaCell(val, this); //pass in the spreadsheet class
                return getGridText();                
            }
            String input = command.substring(equalSign + 2); //real val
            String place = command.substring(0, command.indexOf(" "));
            Location s = new SpreadsheetLocation(place);
            sheet[s.getRow()][s.getCol()] = new ValueCell(input);
        }
        if (command.toLowerCase().equals("clear")){
            for (int x = 0; x < sheet.length; x ++) { //every row
                for(int y = 0; y < sheet[x].length; y ++) {// every column
                    sheet[x][y] = new EmptyCell(); //initialize to non-null
                }
            }
            return getGridText();
        }
        if (command.toLowerCase().contains("clear ")){
            String place = command.substring(6);
            Location s = new SpreadsheetLocation(place);
            sheet[s.getRow()][s.getCol()] = new EmptyCell();
            return getGridText();
        }
        if (command.toLowerCase().contains ("sort")) {
            String type = command.substring(0, 5); //type of sort
            String trim = command.substring(6); //range
            sort (trim, type);
            //return type;
            return getGridText();
        }
        if (command.length() == 0) {
            return "";
        }
        return getGridText();
    }

    public void sort (String input, String type) {
        int dash = input.indexOf("-");
        String start = input.substring(0, dash); //start loc
        String end = input.substring(dash + 1); //end loc
        Location s = new SpreadsheetLocation (start); 
        Location e = new SpreadsheetLocation (end);
        int rowLength = e.getRow()-s.getRow() + 1; //number of cells across
        int colLength = e.getCol()-s.getCol() + 1; //num cells down

        Cell [][] arrSorted = new Cell [rowLength][colLength]; //only used to get length
        Cell [] arr = new Cell [rowLength*colLength];
        for (int row  = s.getRow(), count = 0; row <= e.getRow(); row ++ ){ //fill arr[] with the cells in the range
            for (int col = s.getCol(); col <= e.getCol(); col ++) {
                String column = String.valueOf((char)(col + 65));
                Location x = new SpreadsheetLocation (column + (row + 1));
                arr[count] = getCell(x);
                count ++;
            }
        }

        if (type.toLowerCase().equals("sorta")) { //bubble sort
            for (int i = 0; i < arr.length - 1; i ++) { 
                for (int j = 0; j < arr.length - i - 1; j ++) {
                    if (arr[j].compareTo(arr[j+1]) > 0 ){ //either compareTo in TextCell or compareTo cells in RealCell
                        //if first lexi is more than second, first is alphabetically later
                        Cell temp = arr[j];
                        arr[j] = arr[j+1];
                        arr[j+1] = temp;
                    }
                }
            }
        }
        else {//descending
            for (int i = 0; i < arr.length - 1; i ++) {
                for (int j = 0; j < arr.length - i - 1; j ++) {
                    if (arr[j].compareTo(arr[j+1]) < 0 ){
                        //if first lexi is less than second, first is alphabetically later
                        Cell temp = arr[j];
                        arr[j] = arr[j+1];
                        arr[j+1] = temp;
                    }
                }
            }
        }

        for (int rowS = 0, count = 0; rowS < arrSorted.length; rowS ++ ) { //set new sorted vals to the double array of cells
            for (int colS = 0; colS < arrSorted[0].length; colS ++) {                 
                sheet[rowS + s.getRow()][colS + s.getCol()] = arr[count]; //set to that object                
                count ++;
            }
        }
    }


    @Override
    public int getRows()
    {
        return 20;
    }

    @Override
    public int getCols()
    {
        return 12;
    }

    @Override
    public Cell getCell(Location loc) //returns the cell at the location
    {
        return sheet[loc.getRow()][loc.getCol()];
    }

    @Override
    public String getGridText()
    {
        String grid = "";
        grid = "   |";
        for(char alph = 'A'; alph <= 'L'; alph ++){ //top row of column names
            grid += alph + "         |";
        }
        grid += "\n";
        for (int x = 1; x < getRows() + 1; x ++){ //1 to 20
            if (x < 10) {
                grid += x + "  |"; 
            } 
            else if (x >= 10) {
                grid += x + " |";
            }
            for(int y = 0; y < getCols(); y ++) { //0 to 13 aka A to L
                String address = String.valueOf((char)(y + 65)) + x; //"A1"
                Location l = new SpreadsheetLocation(address);
                Cell cell = getCell(l);
                grid += cell.abbreviatedCellText() + "|";
            }
            grid += "\n";
        }
        return grid;
    }
}
