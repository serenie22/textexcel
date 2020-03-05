
/**
 * Write a description of class ValueCell here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ValueCell extends RealCell
{
    private String input;
    public ValueCell(String input)
    {
        super(input); //pass the num to RealCell, initializing input
    }
    public String getInput() {
        return getInput();
    }
    public double getDoubleValue (String input) {
        return getDoubleValue();// use the super method
    }
    public String abbreviatedCellText() {
        String fullValue = "" + getDoubleValue(input); //ex. 5 is 5.0
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
}
