
/**
 * Write a description of class RealCell here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class RealCell implements Cell, Comparable
{
    private String input;
    public RealCell(String input)
    {
        this.input = input;
    }
    
    public int compareTo(Object cell) {
        RealCell c = (RealCell)cell;
        if (getDoubleValue() > c.getDoubleValue()) {
            return 1;
        }
        else if (getDoubleValue() < c.getDoubleValue()) {
            return -1;
        }
        else {
            return 0;
        }
    }
    
    public String getInput() {
        return input;
    }

    public double getDoubleValue () {
        return Double.parseDouble(input);
    }
    public String abbreviatedCellText() {
        String fullValue = "" + getDoubleValue(); //ex. 5 is 5.0
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
        return input;
    }
}
