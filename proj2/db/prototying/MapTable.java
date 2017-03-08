package db.prototying;

import org.junit.Test;

/**
 * Created by zitongyang on 2/24/17.
 */
public class MapTable implements Table {
    private NestedMap content;
    private String name;

    /**
     * This is a constructor for table, it takes some
     * names and type parameter
     */
    public MapTable(String nameOfTable, String[] colNames) {
        List<String> colNameList = new LinkedListDeque<>(colNames);
        content = new NestedMap(colNameList);
        name = nameOfTable;
    }

    public MapTable(String nameOfTable, NestedMap c) {
        name = nameOfTable;
        content = c;
    }

    public MapTable(){
        name = null;
        content = new NestedMap();
    }

    @Override
    public String name(){
        return name;
    }

    @Override
    public int numRows() {
        return content.rowNumber();
    }

    @Override
    public int numCols() {
        return content.colNumber();
    }

    @Override
    public NestedMap content() {
        return content;
    }


    @Override
    public void addRow(String values) {
        content.addRow(values);
    }

    @Override
    /**
     * This method prints a table
     */
    public String printTable() {
        return content.printMap();
    }

    @Override
    public Table join(String nameOfTable, Table t1, Table t2) {
        NestedMap newContent = NestedMap.join(t1.content(), t2.content());
        Table newTable = new MapTable(nameOfTable, newContent);
        return newTable;
    }

    @Override
    public void addColumn(Column col, String name) {
        content.addColumn(col, name);
    }

    @Override
    public Column getColum(String name) {
        return content.get(name);
    }

    @Override
    public Column getWithoutType(String name) {
        return content.getWithoutType(name);
    }

    @Override
    public String getFullName(String colName){
        return content.getFullName(colName);
    }

    @Override
    public void changeTableName(String newName) {
        name = newName;
    }

    @Override
    public void removeRow(int index){
        content.removeRow(index);
    }

    @Override
    public Table copyTable(List<Integer> listToAdd, Table originalTable){
        this.name = originalTable.name();
        this.content = this.content.copyMap(listToAdd, originalTable.content());
        return this;

    }

}
