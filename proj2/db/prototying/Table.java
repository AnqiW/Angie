package db.prototying;

/**
 * Created by zitongyang on 2/28/17.
 */
public interface Table {

    String printTable();

    void addRow(String values);

    void addColumn(Column col, String name);

    int numRows();

    int numCols();

    Table join(String nameOfTable, Table t1, Table t2);

    NestedMap content();

    Column getColum(String name);

    Column getWithoutType(String name);

    String getFullName(String colName);

    void changeTableName(String newName);

    void removeRow(int index);

    String name();
    Table copyTable(List<Integer> listToAdd, Table originalTable);
}
