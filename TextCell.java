
/**
 * Write a description of class TextCell here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TextCell implements Cell, Comparable
{
    String s;
    public TextCell (String s) {
        this.s = s; //HOW
    }

    public int compareTo(Object cell) {
        TextCell c = (TextCell)cell; //cast inputted cell to a textcell
        return fullCellText().compareTo(c.fullCellText()); //compare two cells alphabetically, returns int val
        //if neg, that means first is lexic. less, if pos first is lexic more, if 0 they are the same
    }
    public String abbreviatedCellText(){
        String padded = s;

        if (s.length() < 10){ //string less than 10 spaces
            for (int x = 0; x < (10 - s.length()); x ++) {
                padded += " ";
            }
            return padded;
        }
        else {
            return s.substring(0, 10); //contains more than 10 spaces
        }
    }

    public String fullCellText(){ //return the input
        return "\"" + s + "\"";
    }
}
