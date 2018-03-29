import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Fridge {
    private List<Product> products;

    public Fridge() {
        this.products = new ArrayList<>();
    }

    public void addObject(Product product) {
        products.add(product);
    }


    public List<Product> getExpiringObjects(int days) {
        List<Product> temp = new ArrayList<>();
        Date date = todayPlusDays(days);
        for (Product t : products) {
            if (t.getExpiryDate().before(date)) {
                temp.add(t);
            }
        }
        return temp;
    }

    public List<Product> getOpenedObjects() {
        List<Product> temp = new ArrayList<>();
        for (Product t : products) {
            if (t.getIsOpened()) {
                temp.add(t);
            }
        }
        return temp;
    }

    private Date todayPlusDays(int days) {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, days);
        dt = c.getTime();
        return dt;
    }

}
