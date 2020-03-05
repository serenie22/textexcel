
/**
 * Write a description of class PercentCell here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PercentCell extends RealCell
{
    private double doub = 0.0;
    public PercentCell(String input) //doesnt contain percent sign
    {
        super(input.substring(0, input.length())); //initialize input from RealCell
    }  

    public double getDoubleValue () {
        double inputPer = Double.parseDouble(getInput());
        return inputPer/100;
    }

    public String fullCellText() {
        return "" + getDoubleValue();
    }  

    public String abbreviatedCellText() {
        String cut = "";
        String dec = super.getInput().substring(0, super.getInput().length()); //of 11.25%, just 11.25
        if (dec.indexOf(".") != -1) { //if it has a decimal point, round in chart
            cut = dec.substring(0, dec.indexOf("."));//of 11.25% returns 11        
        }
        else {
            cut = dec;
        }
        if (cut.length() < 10){ //string less than 10 spaces
            String padded = cut + "%";
            for (int x = 0; x < (10 - (cut.length() + 1)); x ++) { //super.getInput().length()
                padded += " ";
            }
            return padded;
        }
        else {
            return cut.substring(0, 9) + "%"; //contains more than 10 spaces
        }        
    }    
}
