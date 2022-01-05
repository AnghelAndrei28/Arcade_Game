import java.util.Comparator;

public class MyComparator implements Comparator<String> {
    public int compare(String obj1,String obj2)
    {
        if(obj1 == obj2) {
            return 0;
        }
        if(obj1 == null) {
            return -1;
        }
        if(obj2 == null) {
            return 1;
        }
        return obj2.compareTo(obj1);
    }
}