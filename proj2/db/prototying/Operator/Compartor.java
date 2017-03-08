package db.prototying.Operator;

import db.prototying.ClassChecker.ClassChecker;

/**
 * Created by zitongyang on 3/7/17.
 */
public class Compartor {
    private String comparator;

    public Compartor(String com) {
        this.comparator = com;
    }

    public boolean compare(String op0, String op1) {
        ClassChecker checker = new ClassChecker(op0, op1);
        if (checker.bothInteger()) {
            boolean result = compare(Integer.parseInt(op0), Integer.parseInt(op1));
            return result;
        } else if (checker.bothFloat()) {
            boolean result = compare(Float.parseFloat(op0), Float.parseFloat(op1));
            return result;
        } else if (checker.isFloat(op0) && checker.isInteger(op1)) {
            boolean result = compare(Float.parseFloat(op0), Integer.parseInt(op1));
            return result;
        } else if (checker.isInteger(op0) && checker.isFloat(op1)) {
            boolean result = compare(Integer.parseInt(op0), Float.parseFloat(op1));
            return result;
        } else if (checker.bothString()) {
            boolean result = trueStringComporator(op0, op1);
            return result;
        } else {
            System.out.println("String comparator exception needed");
        }
        return false;
    }

    public boolean trueStringComporator(String op0, String op1) {
        if (comparator.equals("==")) {
            return op0 == op1;
        } else if (comparator.equals("!=")) {
            return op0 != op1;
        } else {
            System.out.println("Integer operator exception needed");
        }
        return false;
    }

    public boolean compare(Integer op0, Integer op1) {
        if (comparator.equals(">")) {
            return op0 > op1;
        } else if (comparator.equals("<")) {
            return op0 < op1;
        } else if (comparator.equals(">=")) {
            return op0 >= op1;
        } else if (comparator.equals("<=")) {
            return op0 <= op1;
        } else if (comparator.equals("==")) {
            return op0 == op1;
        } else if (comparator.equals("!=")) {
            return op0 != op1;
        } else {
            System.out.println("Integer operator exception needed");
        }
        return false;
    }

    public boolean compare(Float op0, Float op1) {
        if (comparator.equals(">")) {
            return op0 > op1;
        } else if (comparator.equals("<")) {
            return op0 < op1;
        } else if (comparator.equals(">=")) {
            return op0 >= op1;
        } else if (comparator.equals("<=")) {
            return op0 <= op1;
        } else if (comparator.equals("==")) {
            return op0 == op1;
        } else if (comparator.equals("!=")) {
            return op0 != op1;
        } else {
            System.out.println("ff operator exception needed");
        }
        return false;
    }

    public boolean compare(Float op0, Integer op1) {
        if (comparator.equals(">")) {
            return op0 > op1;
        } else if (comparator.equals("<")) {
            return op0 < op1;
        } else if (comparator.equals(">=")) {
            return op0 >= op1;
        } else if (comparator.equals("<=")) {
            return op0 <= op1;
        } else {
            System.out.println("fi  exception needed");
        }
        return false;
    }

    public boolean compare(Integer op0, Float op1) {
        if (comparator.equals(">")) {
            return op0 > op1;
        } else if (comparator.equals("<")) {
            return op0 < op1;
        } else if (comparator.equals(">=")) {
            return op0 >= op1;
        } else if (comparator.equals("<=")) {
            return op0 <= op1;
        } else {
            System.out.println("if exception needed");
        }
        return false;

    }
}
