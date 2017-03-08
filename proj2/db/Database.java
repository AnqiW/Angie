package db;

import db.prototying.DequeMap;
import db.prototying.Map;
import db.prototying.Table;

public class Database {
    /**
     * This member holds all the tables created
     * in this database.
     */
    Map<String, Table> mapOfTables;

    public Database() {
        mapOfTables = new DequeMap<>();
    }

    public String transact(String query) {
        return Parse.eval(query, this);
    }
}
