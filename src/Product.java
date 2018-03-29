import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date; //https://www.tutorialspoint.com/java/java_date_time.htm

enum productType {VEGETABLES, FRUITS, DAIRY, MEAT, DRINKS}

public class Product {
    private String name;
    private productType type;
    private Date expiryDate;
    private Date purchaseDate;
    private	boolean isOpened;//0-closed, 1- opened

    public Product(String name, productType type, Date expiryDate, Date purchaseDate, boolean isOpened){
        this.name= name;
        this.type=type;
        this.expiryDate= expiryDate;
        this.purchaseDate=purchaseDate;
        this.isOpened= isOpened;
    }

    private String getNameOfType(){
        String temp="";
        switch(type)
        {
            case VEGETABLES:
                temp="vegetables";
                break;
            case FRUITS:
                temp="fruits";
                break;
            case DAIRY:
                temp="dairy";
                break;
            case MEAT:
                temp="meat";
                break;
            case DRINKS:
                temp="drinks";
                break;
        }

        return temp;
    }

    public Date getExpiryDate(){
        return expiryDate;
    }

    public boolean getIsOpened(){
        return isOpened;
    }


    public String toString(){
        DateFormat formatter=new SimpleDateFormat("dd-M-yyyy");
        String tempOpened;
        if (isOpened){tempOpened="opened";}
        else {tempOpened="not opened";}
        return name+"- type: "+getNameOfType()
                +", expiry date: "+formatter.format(expiryDate)
                +", purchase date: "+formatter.format(purchaseDate)
                +", "+tempOpened;
    }

}
