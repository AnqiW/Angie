package db.prototying.Operator;

import db.prototying.ClassChecker.ClassChecker;

import java.util.Objects;

/**
 * Created by zitongyang on 3/7/17.
 */
public class Operator {
    private String operator;

    public Operator(String operator) {
        this.operator = operator;
    }

    public String operate(String op0, String op1) {
        ClassChecker checker = new ClassChecker(op0, op1);
        if (checker.bothInteger()) {
            Integer result = operate(Integer.parseInt(op0), Integer.parseInt(op1));
            return result.toString();
        } else if (checker.bothFloat()) {
            Float result = operate(Float.parseFloat(op0), Float.parseFloat(op1));
            return result.toString();
        } else if (checker.isFloat(op0) && checker.isInteger(op1)) {
            Float result = operate(Float.parseFloat(op0), Integer.parseInt(op1));
            return result.toString();
        } else if (checker.isInteger(op0) && checker.isFloat(op1)) {
            Float result = operate(Integer.parseInt(op0), Float.parseFloat(op1));
            return result.toString();
        } else if (checker.bothString()) {
            String newStr1 = "'" + op0.replaceAll("'", "");
            String newStr2 = op1.replaceAll("'", "") + "'";
            String addedString = new StringBuilder().append(newStr1).append(newStr2).toString();
            return addedString;
        } else {
            System.out.println("String operate exception needed");
        }
        return null;
    }

    public Integer operate(Integer op0, Integer op1) {
        if (operator.equals("+")) {
            return op0 + op1;
        } else if (operator.equals("-")) {
            return op0 - op1;
        } else if (operator.equals("*")) {
            return op0 * op1;
        } else if (operator.equals("/")) {
            return op0 / op1;
        } else {
            System.out.println("Integer operator exception needed");
        }
        return null;
    }

    public Float operate(Float op0, Float op1) {
        if (operator.equals("+")) {
            return op0 + op1;
        } else if (operator.equals("-")) {
            return op0 - op1;
        } else if (operator.equals("*")) {
            return op0 * op1;
        } else if (operator.equals("/")) {
            return op0 / op1;
        } else {
            System.out.println("Float operator exception needed");
        }
        return null;
    }

    public Float operate(Float op0, Integer op1) {
        if (operator.equals("+")) {
            return op0 + op1;
        } else if (operator.equals("-")) {
            return op0 - op1;
        } else if (operator.equals("*")) {
            return op0 * op1;
        } else if (operator.equals("/")) {
            return op0 / op1;
        } else {
            System.out.println("Float Integer operator exception needed");
        }
        return null;
    }

    public Float operate(Integer op0, Float op1) {
        if (operator.equals("+")) {
            return op0 + op1;
        } else if (operator.equals("-")) {
            return op0 - op1;
        } else if (operator.equals("*")) {
            return op0 * op1;
        } else if (operator.equals("/")) {
            return op0 / op1;
        } else {
            System.out.println("Integer Float operator exception needed");
        }
        return null;
    }
}
