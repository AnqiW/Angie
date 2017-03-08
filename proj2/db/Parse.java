package db;

import db.prototying.*;
import db.prototying.Readers.ReaderClass;
import db.prototying.Readers.Reader;
import db.prototying.Readers.FileReader;
import db.prototying.Readers.Storer;

import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.StringJoiner;
import java.io.StringReader;


public class Parse {
    // Various common constructs, simplifies parsing.
    private static final String REST = "\\s*(.*)\\s*",
            COMMA = "\\s*,\\s*",
            AND = "\\s+and\\s+";

    // Stage 1 syntax, contains the command name.
    private static final Pattern CREATE_CMD = Pattern.compile("create table " + REST),
            LOAD_CMD = Pattern.compile("load " + REST),
            STORE_CMD = Pattern.compile("store " + REST),
            DROP_CMD = Pattern.compile("drop table " + REST),
            INSERT_CMD = Pattern.compile("insert into " + REST),
            PRINT_CMD = Pattern.compile("print " + REST),
            SELECT_CMD = Pattern.compile("select " + REST);

    // Stage 2 syntax, contains the clauses of commands.
    private static final Pattern CREATE_NEW = Pattern.compile("(\\S+)\\s+\\((\\S+\\s+\\S+\\s*" +
            "(?:,\\s*\\S+\\s+\\S+\\s*)*)\\)"),
            SELECT_CLS = Pattern.compile("([^,]+?(?:,[^,]+?)*)\\s+from\\s+" +
                    "(\\S+\\s*(?:,\\s*\\S+\\s*)*)(?:\\s+where\\s+" +
                    "([\\w\\s+\\-*/'<>=!.]+?(?:\\s+and\\s+" +
                    "[\\w\\s+\\-*/'<>=!.]+?)*))?"),
            CREATE_SEL = Pattern.compile("(\\S+)\\s+as select\\s+" +
                    SELECT_CLS.pattern()),
            INSERT_CLS = Pattern.compile("(\\S+)\\s+values\\s+(.+?" +
                    "\\s*(?:,\\s*.+?\\s*)*)\\s?");
    /**
     * This is the most important function for Parse, it evaluates the user input
     *
     * @param query this is user's input in the terminal
     * @param db    this is the database of the program, if database changes it should be
     *              db should be modified accordingly
     * @return returns the string of the input
     */
    static String eval(String query, Database db) {
        Matcher m;
        if ((m = CREATE_CMD.matcher(query)).matches()) {
            try {
                createTable(m.group(1), db.mapOfTables);
            } catch (Exception e) {
                return "ERROR: malformed create command";
            } catch (AssertionError ae) {
                return "ERROR: Wrong create";
            }
            return "";
        } else if ((m = PRINT_CMD.matcher(query)).matches()) {
            try {
                return printTable(m.group(1), db.mapOfTables);
            } catch (Exception ne) {
                return "ERROR: table doesn't exist!";
            }
        } else if ((m = SELECT_CMD.matcher(query)).matches()) {
            try {
                return select(m.group(1), db.mapOfTables);
            } catch (AssertionError e) {
                return "ERROR: Malformed selection!";
            } catch (Exception e) {
                return "ERROR: Selecting error";
            }

        } else if ((m = INSERT_CMD.matcher(query)).matches()) {
            try {
                insertRow(m.group(1), db.mapOfTables);
            } catch (AssertionError ae) {
                return "ERROR: Malformed insertion!";
            } catch (Exception e) {
                return "ERROR: Inserting values to a table that does not exist!";
            }
            return "";
        } else if ((m = LOAD_CMD.matcher(query)).matches()) {
            try {
                loadTable(m.group(1), db.mapOfTables);
            } catch (IllegalArgumentException e) {
                return "ERROR: Empty Table!";
            } catch (AssertionError ae) {
                return "ERROR: Malformed table!";
            }
            return "";
        } else if ((m = STORE_CMD.matcher(query)).matches()) {
            try {
                storeTable(m.group(1), db.mapOfTables);
            } catch (Exception e) {
                return "ERROR: Storing error";
            }
            return "";

        } else if ((m = DROP_CMD.matcher(query)).matches()) {
            try {
                dropTable(m.group(1), db.mapOfTables);
            } catch (Exception e) {
                return "ERROR: Dropping error";
            }
            return "";
        } else {
            return "ERROR: Malformed command";
        }
    }

    /**
     * This method is called when create_table command is received. The
     * table cna be created in two ways: 1) using colName Type command or
     * 2) using the select statement.
     *
     * @param expr   the original input with "create table" removed
     * @param tables the list table from the database, in case the list
     *               is mutated.
     */
    private static void createTable(String expr, Map<String, Table> tables) throws Exception {
        Matcher m;
        if ((m = CREATE_NEW.matcher(expr)).matches()) {
            createNewTable(m.group(1), m.group(2).split(COMMA), tables);
        } else if ((m = CREATE_SEL.matcher(expr)).matches()) {
            String selectexpr = m.group(2) + " from " + m.group(3);
            createSelectedTable(selectexpr, m.group(1), tables);
        } else {
            throw new Exception("Malformed create: %s\n" + expr);
        }
    }

    private static void createNewTable(String name, String[] cols, Map<String, Table> tables) {
        String[] newColName = new String[cols.length];
        for (int i = 0; i < cols.length; i++) {
            if (!(cols[i].endsWith("int") || cols[i].endsWith("string") || cols[i].endsWith("float") || cols[i].endsWith("String"))) {
                throw new AssertionError("ERROR: Wrong type!");
            }
        }
        int i = 0;
        for (String colName : cols) {
            newColName[i] = colName.replaceAll("\\s+", " ");
            i = i + 1;
        }
        Table newTable = new MapTable(name, newColName);
        tables.put(name, newTable);
    }

    private static void createSelectedTable(String selectExpr, String name, Map<String, Table> tables) {
        Table newTable = selecthelper(selectExpr, tables);
        newTable.changeTableName(name);
        tables.put(name, newTable);
    }

    private static Table selecthelper(String expr, Map<String, Table> tables) throws Error {
        Matcher m = SELECT_CLS.matcher(expr);
        if (!m.matches()) {
            throw new AssertionError("ERROR: Malformed select!");
        }
        Table temp;

        String selectedTables = m.group(2);
        temp = join(selectedTables, tables);

        String columnExpressions = m.group(1);
        temp = colExprProcessor(columnExpressions, temp);

        String conditions = m.group(3);
        temp = conditionProcessor(conditions, temp);
        return temp;
    }

    private static void insertRow(String expr, Map<String, Table> tables) throws Error {
        Matcher m = INSERT_CLS.matcher(expr);
        if (!m.matches()) {
            throw new AssertionError("Malformed Insertion!");
        }
        String tableName = m.group(1);
        String values = m.group(2);
        Table mutatedTable = tables.get(tableName);
        mutatedTable.addRow(values);
    }

    private static String printTable(String name, Map<String, Table> tables) throws Exception {
        try {
            return tables.get(name).printTable();
        } catch (Exception e) {
            throw new Exception("ERROR: table doesn't exsit!");
        }
    }

    /**
     * This function takes a select expression, ideally the expression should
     * have 3 component
     *
     * @columnExpression m.group(1) "x+y, x+3"
     * @allTheTables m.group(2)  "t1, t2, t3, t4"
     * @conditionalStatement m.group(3)  "x>2, y=1"
     */
    private static String select(String expr, Map<String, Table> tables) throws Error {
        Matcher m = SELECT_CLS.matcher(expr);
        if (!m.matches()) {
            throw new AssertionError("ERROR: Malformed select!");
        }
        Table temp;

        String selectedTables = m.group(2);
        temp = join(selectedTables, tables);


        String columnExpressions = m.group(1);
        temp = colExprProcessor(columnExpressions, temp);

        String conditions = m.group(3);
        temp = conditionProcessor(conditions, temp);
        return temp.printTable();
    }

    //*************************************************************
    private static Table join(String selectedTables, Map<String, Table> dBTables) {
        Reader rd = new ReaderClass();
        String[] selecTable = rd.readCommaSplit(selectedTables);
        if (selecTable.length == 1) {
            return dBTables.get(selecTable[0]);
        }
        Table temp = dBTables.get(selecTable[0]);
        for (String name : selecTable) {
            temp = dBTables.get(name).join(null, temp, dBTables.get(name));
        }
        return temp;
    }

    //*************************************************************
    private static Table colExprProcessor(String columnExpressions, Table temp) {
        if (columnExpressions.equals("*")) {
            return temp;
        }
        Table result = new MapTable();
        Reader rd = new ReaderClass();
        String[] commands = rd.readCommaSplit(columnExpressions);
        for (String command : commands) {
            if (rd.noOperationFlag(command)) {
                createTableHelperNoOp(result, temp, command);
            } else {
                createTableHelperOp(result, temp, command);
            }
        }
        return result;
    }

    /**
     * This case assumes that colExpress is simply column name.
     */
    private static Table createTableHelperNoOp(Table temp, Table joinedTable, String colExpress) {
        temp.addColumn(joinedTable.getWithoutType(colExpress), joinedTable.getFullName(colExpress));
        return temp;
    }

    private static Table createTableHelperOp(Table temp, Table joinedTable, String colExpress) {
        Reader rd = new ReaderClass();
        List<String> readedCommand = rd.readColumExpr(colExpress);
        Column newCol = Column.columnOperation(joinedTable.getWithoutType(readedCommand.get(0)), joinedTable.getWithoutType(readedCommand.get(1)), readedCommand.get(2));
        String op1FullName = joinedTable.getFullName(readedCommand.get(0));
        String type = rd.readType(op1FullName);
        temp.addColumn(newCol, readedCommand.get(3) + " " + type);
        return temp;
    }

    //*************************************************************
    private static Table conditionProcessor(String conditions, Table temp) {
        if (conditions == null) {
            return temp;
        }
        Reader rd = new ReaderClass();
        String[] conds = rd.readAndSplit(conditions);
        for (String cond : conds){
            String[] readedConditoin= rd.readCondition(cond);
            if (rd.uniaryFlag(readedConditoin[2])) {
                temp = uniaryConditionHelper(readedConditoin, temp);
            } else {
                temp = binaryConditionHelper(readedConditoin, temp);
            }
        }
        return temp;
    }

    private static Table uniaryConditionHelper(String[] readedConditoin, Table processed){
        Column operand0 = processed.getWithoutType(readedConditoin[0]);
        String operand1 = readedConditoin[1];
        String operator = readedConditoin[2];
        List<Integer> columnToDelete;
        return null;
    }

    private static Table binaryConditionHelper(String[] readedConditoin, Table processed){
        Column operand0 = processed.getWithoutType(readedConditoin[0]);
        Column operand1 = processed.getWithoutType(readedConditoin[1]);
        String operator = readedConditoin[2];
        List<Integer> listToDelete = Column.columnCondition(operand0, operand1, operator);
        for (int index: listToDelete) {
            processed.removeRow(index);
        }
        return processed;
    }


    private static void loadTable(String fileName, Map<String, Table> tables) {
        FileReader r = new FileReader(fileName);
        String[] cols = r.readColumnNames();
        createNewTable(fileName, cols, tables);
        Table mutatedTable = tables.get(fileName);
        while (r.hasNextLine()) {
            mutatedTable.addRow(r.readTableLine());
        }
    }

    private static void storeTable(String tableName, Map<String, Table> tables) throws Exception {
        if (!tables.containsKey(tableName)) {
            throw new Exception("ERROR: you are storing a table that does not exist!");
        }
        Storer s = new Storer(tableName);
        s.write(tables.get(tableName).printTable());
        s.flush();
        s.close();
    }

    private static void dropTable(String tableName, Map<String, Table> tables) throws Exception {
        if (!tables.containsKey(tableName)) {
            throw new Exception("ERROR: you are dropping a table that does not exist!");
        }
        tables.remove(tableName);
    }
}

