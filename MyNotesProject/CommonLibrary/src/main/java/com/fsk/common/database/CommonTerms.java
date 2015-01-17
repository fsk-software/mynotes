package com.fsk.common.database;


/**
 * A constants class that defines common terms used for the database schemas.
 */
public interface CommonTerms {
    //Developer Note=>Code format exception:
    //    The individual items are documented because they are self-explanatory.


    public static final String AND = " AND ";


    public static final String OR = " OR ";


    public static final String EQUALS = " = ";


    public static final String FROM = " FROM ";


    public static final String WHERE = " WHERE ";


    public static final String AS_SELECT = " AS SELECT ";


    public static final String SEMI_COLON = ";";


    public static final String PERIOD = ".";


    public static final String COMMA = ",";


    public static final String INTEGER = " INTEGER";


    public static final String TEXT = " TEXT";


    public static final String INTEGER_PRIMARY_KEY = " INTEGER PRIMARY KEY";


    public static final String INTEGER_PRIMARY_KEY_AUTO_INCREMENT =
            INTEGER_PRIMARY_KEY + " AUTOINCREMENT NOT NULL ";


    public static final String TEXT_PRIMARY_KEY = " TEXT PRIMARY KEY";


    public static final String CREATE_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS ";


    public static final String CREATE_VIEW_IF_NOT_EXISTS = "CREATE VIEW IF NOT EXISTS ";


    public static final String DROP_VIEW_IF_EXISTS = "DROP VIEW IF EXISTS %s";


    public static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS %s";
}
