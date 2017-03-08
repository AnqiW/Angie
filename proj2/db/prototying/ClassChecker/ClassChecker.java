package db.prototying.ClassChecker;

import java.util.Objects;

/**
 * Created by zitongyang on 3/6/17.
 */
public class ClassChecker {
    private Object o1;
    private Object o2;

    public ClassChecker() {
    }

    public ClassChecker(Object o1, Object o2) {
        this.o1 = o1;
        this.o2 = o2;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public static boolean isFloat(String s) {
        try {
            Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public static boolean isString(Object s) {
        return (!isFloat(s.toString())) && (!isInteger(s.toString()));

    }

    public boolean bothFloat() {
        return (isFloat(o1.toString())) && (isFloat(o2.toString()));
    }

    public boolean bothInteger() {
        return (isInteger(o1.toString())) && (isInteger(o2.toString()));
    }

    public boolean bothString() {
        return (isString(o1.toString())) && (isString(o2.toString()));
    }
}
