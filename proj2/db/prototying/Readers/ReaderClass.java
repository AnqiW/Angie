package db.prototying.Readers;

import db.prototying.LinkedListDeque;
import db.prototying.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zitongyang on 2/28/17.
 */
public class ReaderClass implements Reader {
    private static final String COMMA = "\\s*,\\s*";
    private static final Pattern COLUMN_EXPR_4 = Pattern.compile("\\s*(\\S+)\\s*(\\S+)\\s*(\\S+)\\s*as\\s*(\\S+)\\s*");
    private static final Pattern COLUMN_EXPR_1 = Pattern.compile("\\s*(\\S+)\\s*");
    private static final Pattern COLUMN_NAME = Pattern.compile("(\\S+)\\s(\\S+)");
    private static final Pattern CONDITION_EXPR = Pattern.compile("(\\S+)\\s*(>|<|==|>=|<=|!=)\\s*(\\S+)$");
    private static final String AND ="\\s*and\\s*";
    private static final Pattern UNIARY_FLAG = Pattern.compile("[0-9]+\\.[0-9]+|[0-9]+|'\\S+'");



    @Override
    /** This method reads a comma separated values of string
     * to an array of String.
     */
    public String[] readCommaSplit(String values) {
        return values.split(COMMA);
    }

    @Override
    public String[] readAndSplit(String condiExpr){
        return condiExpr.split(AND);
    }
    /**
     * This method reads a column expression, and returns a String array of
     * desired elements
     *
     * @param expr <operand0> <arithmetic operator> <operand1> as <column alias>
     * @return String[]:: {<operand0>, <operand1>, <arithmetic operator>, <column alias>}
     */
    @Override
    public List<String> readColumExpr(String expr) {
        List<String> result = new LinkedListDeque<>();
        Matcher m4 = COLUMN_EXPR_4.matcher(expr);
        Matcher m1 = COLUMN_EXPR_1.matcher(expr);
        if (m4.matches()) {
            result.addLast(m4.group(1));
            result.addLast(m4.group(3));
            result.addLast(m4.group(2));
            result.addLast(m4.group(4));
        } else if (m1.matches()) {
            result.addLast(m1.group(1));
        } else {
            System.out.println("exception needed");
        }
        return result;
    }

    @Override
    public String readType(String colName) {
        Matcher m = COLUMN_NAME.matcher(colName);
        if (m.matches()) {
            return m.group(2);
        }
        return "read ty[e exception needed";
    }

    @Override
    public String readName(String colName) {
        Matcher m = COLUMN_NAME.matcher(colName);
        if (m.matches()) {
            return m.group(1);
        }
        return "readname exception needed";
    }

    @Override
    public boolean noOperationFlag(String colexpr) {
        Matcher m = COLUMN_EXPR_1.matcher(colexpr);
        if (m.matches()) {
            return true;
        }
        return false;
    }

    @Override
    public String[] readCondition(String condExpr) {
        String[] result = new  String[3];
        Matcher m = CONDITION_EXPR.matcher(condExpr);
        if (m.matches()) {
           result[0] = m.group(1);
           result[1] = m.group(3);
           result[2] = m.group(2);
        } else {
            System.out.println("condition exception needed");
        }
        return result;
    }

    @Override
    public boolean uniaryFlag(String operand1){
        Matcher m = UNIARY_FLAG.matcher(operand1);
        if (m.matches()){
            return true;
        }
        return false;
    }
}
