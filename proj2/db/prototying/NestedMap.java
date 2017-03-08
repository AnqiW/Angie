package db.prototying;

import db.prototying.Readers.Reader;
import db.prototying.Readers.ReaderClass;

/**
 * Created by zitongyang on 2/24/17.
 */

/**
 * Item is the temporary type parameter.
 */
public class NestedMap<Item> {
    private int numColumns;
    private int numRows;

    /**
     * The Strings in motherMap is the
     * same as the string in names
     */
    private Map<String, Column> motherMap;
    private List<String> names;


    public NestedMap() {
        numRows = 0;
        numColumns = 0;
        motherMap = new DequeMap<>();
        names = new LinkedListDeque<>();
    }

    /**
     * This method helps rint a particular row of nested map,
     * is a part of helper function for the print method.
     * This method takes the index we want to print as argument
     */
    private String printRow(int indexOfRow) {
        String resultString = "";
        for (String name : names) {
            Column tempColumn = motherMap.get(name);
            resultString = resultString + tempColumn.printEntry(indexOfRow);
            resultString = resultString + ",";
        }
        resultString = resultString.substring(0, resultString.length() - 1);
        return resultString;
    }

    /**
     * This is a private helper method for join. It filters the
     * rows in two table and returns which rows should be maintained
     * in the resulting table. The return should be a two tuple that
     * with first element being the index of row from the first table
     * and the second for the second table. For instance, the tuple
     * (2, 3) tells us that the second row from the first table and
     * the third row from the second table matches, and should be
     * combined together in the new table.
     */
    public static List<String> joinColumnFilter(NestedMap nmap1, NestedMap nmap2) {
        List<String> result = new LinkedListDeque<>();
        for (int i1 = 0; i1 < nmap1.numColumns; i1++) {
            for (int i2 = 0; i2 < nmap2.numColumns; i2++) {
                if (nmap1.names.get(i1).equals(nmap2.names.get(i2))) {
                    result.addLast(nmap1.names.get(i1).toString());
                }
            }

        }
        return result;
    }

    public static List<String> joinedColumn(NestedMap nmap1, NestedMap nmap2) {
        List<String> result = new LinkedListDeque<>();
        for (int i1 = 0; i1 < nmap1.numColumns; i1++) {
            for (int i2 = 0; i2 < nmap2.numColumns; i2++) {
                if (nmap1.names.get(i1).equals(nmap2.names.get(i2))) {
                    result.addLast(nmap1.names.get(i1).toString());
                }
            }

        }
        List<String> name1 = nmap1.names;
        List<String> name2 = nmap2.names;
        for (String n1 : name1) {
            if (!result.contains(n1)) {
                result.addLast(n1);
            }
        }
        for (String n2 : name2) {
            if (!result.contains(n2)) {
                result.addLast(n2);
            }
        }

        return result;
    }

    /**
     * This is another private helper method for join operation,
     * it takes in a two columns, and returns a list of tuple (i, j)
     * that represents that the i-th row from the first table
     * and j-th row from the second table matches each other.
     */
    public static List<Integer[]> joinRowFilter(Column col1, Column col2) {
        List<Integer[]> result = new LinkedListDeque<>();
        for (int i1 = 1; i1 < col1.size() + 1; i1++) {
            for (int i2 = 1; i2 < col2.size() + 1; i2++) {
                if (col1.get(i1).equals(col2.get(i2))) {
                    Integer[] temp = new Integer[2];
                    temp[0] = i1;
                    temp[1] = i2;
                    result.addLast(temp);
                }
            }
        }
        return result;
    }

    /**
     * This is another private helper method for join operation,
     * It takes two Lists of tuple and return the element in common
     * for instance, tuple (i1, j1) means i1-th row from table1 and
     * j1-th row from the second table match on certain column, and
     * this method helps to find row that math on both two common
     * columns.
     */
    public static List<Integer[]> joinCommonRowFilter(List<Integer[]> l1, List<Integer[]> l2) {
        List<Integer[]> result = new LinkedListDeque<>();
        for (int i1 = 0; i1 < l1.length(); i1++) {
            for (int i2 = 0; i2 < l2.length(); i2++) {
                if (l1.get(i1)[0] == (l2.get(i2))[0] && l1.get(i1)[1] == (l2.get(i2))[1]) {
                    Integer[] temp = new Integer[2];
                    temp[0] = l1.get(i1)[0];
                    temp[1] = l2.get(i1)[1];
                    result.addLast(temp);
                }
            }
        }
        return result;
    }

    /**
     * This is another helper method for join operation. It join two table
     * together in case that they have no common column.
     */
    public static NestedMap CartesianProduct(NestedMap nmap1, NestedMap nmap2) {
        List<String> name1 = nmap1.names;
        List<String> name2 = nmap2.names;
        List<String> newName = name1.concatenate(name2);

        //Now create a new table
        NestedMap result = new NestedMap(newName);
        for (int row1 = 1; row1 < nmap1.numRows + 1; row1++) {
            for (int row2 = 1; row2 < nmap2.numRows + 1; row2++) {
                for (int col1 = 0; col1 < nmap1.numColumns; col1++) {
                    String currentName = name1.get(col1);
                    result.get(currentName).addNext(nmap1.get(currentName).get(row1));
                }
                for (int col2 = 0; col2 < nmap2.numColumns; col2++) {
                    String currentName = name2.get(col2);
                    result.get(currentName).addNext(nmap2.get(currentName).get(row2));
                }
                result.numRows = result.numRows + 1;
            }
        }
        return result;
    }

    public static NestedMap join(NestedMap nmap1, NestedMap nmap2) {
        List<String> overlapingColumn = NestedMap.joinColumnFilter(nmap1, nmap2);
        if (overlapingColumn.length() == 0) {
            return NestedMap.CartesianProduct(nmap1, nmap2);
        }
        List<Integer[]> currentCombinations = new LinkedListDeque<>();
        for (String colName : overlapingColumn) {
            List<Integer[]> tempCombinations = NestedMap.joinRowFilter(nmap1.get(colName), nmap2.get(colName));
            if (currentCombinations.isEmpty()) {
                currentCombinations = tempCombinations;
            } else {
                currentCombinations = NestedMap.joinCommonRowFilter(currentCombinations, tempCombinations);
            }
        }
        List<String> joinedColumn = NestedMap.joinedColumn(nmap1, nmap2);
        List<String> name1 = nmap1.names;
        List<String> name2 = nmap2.names;
        NestedMap result = new NestedMap(joinedColumn);
        int newRowIndex = 1;
        for (Integer[] tuple : currentCombinations) {
            int row1 = tuple[0];
            int row2 = tuple[1];
            for (String n1 : name1) {
                result.get(n1).addByIndex(newRowIndex, nmap1.get(n1).get(row1));
            }
            for (String n2 : name2) {
                result.get(n2).addByIndex(newRowIndex, nmap2.get(n2).get(row2));
            }
            newRowIndex = newRowIndex + 1;
            result.numRows = result.numRows + 1;
        }
        return result;
    }

    /**
     * This is a constructor for nestedMap, it initialize the
     * number of rows and columns and the core content motherMap
     * The constructor takes a list of column names as argument,
     * and returns a empty nested map with names set
     */
    public NestedMap(List<String> colNames) {
        numRows = 0;
        names = colNames;
        numColumns = colNames.length();
        motherMap = new DequeMap<String, Column>();
        for (String name : colNames) {
            motherMap.put(name, new Column());
        }
    }

    /**
     * This method adds a row to a nestedMap.
     *
     * @param values values expressed using a string separated by
     *               commas.
     */
    public void addRow(String values) {
        numRows = numRows + 1;
        Reader readerIns = new ReaderClass();
        String[] valuesArray = readerIns.readCommaSplit(values);
        int i = 0;
        for (String colName : motherMap) {
            motherMap.get(colName).addNext(valuesArray[i]);
            i = i + 1;
        }
    }


    /**
     * This method add a row to nested map.
     * The row intended to add is contained in an item list
     */
    public void addNextRow(List<Item> newRowElem) {
        numRows = numRows + 1;
        for (String name : names) {
            Column<Item> tempColumn = motherMap.get(name);
            tempColumn.addNext(newRowElem.removeFirst());
        }
    }

    /**
     * This method helps to get  a paticular row from
     * a nested map given the name of that row
     */
    public Column get(String name) {
        return motherMap.get(name);
    }

    public Column getWithoutType(String colName) {
        try {
            return get(colName + " string");
        } catch (NullPointerException e) {
            try {
                return get(colName + " String");
            } catch (NullPointerException e1) {
                try {
                    return get(colName + " float");
                } catch (NullPointerException e2) {
                    try {
                        return get(colName + " int");
                    } catch (NullPointerException e3) {
                        System.out.println("Exception needed");
                        return null;
                    }
                }
            }
        }

    }

    public String printMap() {
        String resultString = "";
        for (String elem : names) {
            if (resultString == "") {
                resultString = resultString + elem;
            } else {
                resultString = resultString + "," + elem;
            }
        }
        resultString = resultString + System.lineSeparator();
        for (int m = 1; m < numRows + 1; m++) {
            if (m == numRows) {
                resultString = resultString + printRow(m);
            } else {
                resultString = resultString + printRow(m) + System.lineSeparator();
            }
        }
        return resultString;
    }

    public int rowNumber() {
        return numRows;
    }

    public int colNumber() {
        return numColumns;
    }

    public void addColumn(Column col, String name) {
        motherMap.put(name, col);
        numRows = col.size();
        numColumns = numColumns + 1;
        names.addLast(name);
    }

    public String getFullName(String colName) {
        Reader rd = new ReaderClass();
        for (String fullName : names) {
            if (colName.equals(rd.readName(fullName))) {
                return fullName;
            }
        }
        System.out.print("exception needed");
        return null;
    }

    public void removeRow(int index){
        numRows = numRows -1;
        for (String name : names) {
            motherMap.get(name).removeEntry(index);
        }
    }
}
