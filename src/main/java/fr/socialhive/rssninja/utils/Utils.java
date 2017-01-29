package fr.socialhive.rssninja.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Munsch on 29/01/2017.
 */
public class Utils {
    public static <T, U> List<T> transform(List<U> income, String property) {
        List<T> list = new ArrayList<T>();

        for (U obj : income) {
            try {
                Field f = obj.getClass().getDeclaredField(property);
                f.setAccessible(true);
                T adding = (T)(f.get(obj));
                list.add(adding);
            }
            catch (Throwable ex) {
                System.out.println("Error in Utils");
            }
        }
        return list;
    }
}
