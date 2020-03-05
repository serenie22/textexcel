
/**
 * Write a description of class FormulaCell here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class FormulaCell extends RealCell
{
    private Spreadsheet sheet;
    public FormulaCell(String input, Spreadsheet s)
    {
        super (input); //input is ( 1 + 2 )
        this.sheet = s;
    }

    public double getDoubleValue (String input) { //input has no parens
        if (input.toUpperCase().contains("SUM") || input.toUpperCase().contains("AVG")) {
            String shorter = input.substring(4, input.length()); //A1-A2
            int dash = shorter.indexOf("-");           
            double total = 0.0;
            double count = 0.0;
            Location start = new SpreadsheetLocation(shorter.substring(0,dash));
            Location end = new SpreadsheetLocation(shorter.substring(dash + 1 ));

            for (int row = start.getRow(); row <= end.getRow(); row ++) { //finding the total
                for (int col = start.getCol(); col <= end.getCol(); col ++) {
                    String column = String.valueOf((char)(col + 65));
                    Location x = new SpreadsheetLocation (column + (row + 1));
                    if (((RealCell)sheet.getCell(x)).fullCellText().matches(".*[A-Z].*")){ //if references another formula
                        String form = sheet.getCell(x).fullCellText(); //"(A1 + 1)"
                        String cut = form.substring(2, form.length() - 2); //"A1 + 1" MINUS 1
                        total += getDoubleValue(cut);
                        count ++;
                    }
                    else { //doesn't reference another formula
                        total += ((RealCell)sheet.getCell(x)).getDoubleValue(); 
                        count ++;
                    }
                }
            }   
            if (input.toUpperCase().contains("SUM")) {
                return total;
            }
            return total/count;
        }
        else {
            String [] array = input.split(" "); //splits the string, getting rid of all spaces, array of strings

            for (int y = 0; y < array.length; y ++) { //for every thing in the array, convert references
                if (array[y].matches(".*[A-Z].*") || array[y].matches (".*[a-z].*")) /*Character.isLetter(array[x].charAt(0))*/{ //if the string contains a letter (A1 contains A) please revise this
                    String s = array[y];
                    Location loc = new SpreadsheetLocation(array[y]);
                    if (((RealCell)sheet.getCell(loc)).fullCellText().matches(".*[A-Z].*")){ //if references another formula
                        Location ref = new SpreadsheetLocation(s);    
                        String form = sheet.getCell(ref).fullCellText(); //"(A1 + 1)"
                        String cut = form.substring(2, form.length() - 1); //"A1 + 1"
                        String accessed = "" + getDoubleValue(cut); //retrieved double value using recursion
                        array[y] = accessed;
                    }
                    else {
                        array[y] = "" + ((RealCell)sheet.getCell(loc)).getDoubleValue(); //access value of cell at loc, casting it to real cell,gets double value and turns into string
                    }
                }
            }

            for (int x = 0; x < array.length - 1; x ++) { //for every thing in the array
                if (array[x].equals("+")) {
                    double sum = Double.parseDouble(array[x-1]) + Double.parseDouble(array[x+1]);//add nums before and after +
                    array [x+1] = Double.toString(sum); //set sum as val at index of second num
                }
                if (array[x].equals("*")) {
                    double product = Double.parseDouble(array[x-1]) * Double.parseDouble(array[x+1]);
                    array[x+1] = Double.toString(product);
                }
                if (array[x].equals("/")) {
                    double quo = Double.parseDouble(array[x-1]) / Double.parseDouble(array[x+1]);
                    array[x+1] = Double.toString(quo);
                }     
                if (array[x].equals("-")) {
                    double diff = Double.parseDouble(array[x-1]) - Double.parseDouble(array[x+1]);
                    array[x+1] = Double.toString(diff);
                } 
            }
            return Double.parseDouble(array[array.length - 1]); //return last val, which is overall result, or if just ( 1 )
        }
    }

    public double accessVal (String s) { //passed a certain cell name
        Location loc = new SpreadsheetLocation(s);
        return ((RealCell)sheet.getCell(loc)).getDoubleValue();
    }

    public String abbreviatedCellText() {
        String fullValue = "" + this.getDoubleValue(getInput()); //caculates the formula
        String padded = fullValue;
        if (fullValue.length() < 10){ //string less than 10 spaces
            for (int x = 0; x < (10 - fullValue.length()); x ++) {
                padded += " ";
            }
            return padded;
        }
        else {
            return fullValue.substring(0, 10); //contains more than 10 spaces
        }
    } 

    public String fullCellText() {
        return "( " + super.fullCellText() + " )"; //what will be returned by cell inspection, this 
    }
}
