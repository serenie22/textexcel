 

//Update this file with your own code.

public class SpreadsheetLocation implements Location
{
    private int colNumber;
    private int rowNumber;

    public SpreadsheetLocation(String cellName)
    {
        colNumber = Character.toUpperCase(cellName.charAt(0)) - 'A';
        rowNumber = Integer.parseInt(cellName.substring(1)) - 1; //A20 means index 19, which is what u need 
    }

    @Override
    public int getRow()
    {
        return rowNumber;
    }

    @Override
    public int getCol()
    {
        return colNumber;
    }
    

}
