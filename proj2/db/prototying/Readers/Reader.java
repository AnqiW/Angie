package db.prototying.Readers;
import db.prototying.*;

/**
 * Created by zitongyang on 2/28/17.
 */
public interface Reader {

    String[] readCommaSplit(String values);

    List<String> readColumExpr(String columnExpression);

    String readType(String colName);

    String readName(String colName);

    boolean noOperationFlag(String colexpr);

    String[] readCondition(String condExpr);

    String[] readAndSplit(String condiExpr);

    boolean uniaryFlag(String operand1);
}
