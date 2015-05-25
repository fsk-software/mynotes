package com.fsk.common.database;


/**
 * A constants class that defines common terms used for the database schemas.
 */
public interface CommonTerms {
    //Developer Note=>Code format exception:
    //    The individual items are documented because they are self-explanatory.


    String AND = " AND ";


    String OR = " OR ";


    String EQUALS = " = ";


    String FROM = " FROM ";


    String WHERE = " WHERE ";


    String AS_SELECT = " AS SELECT ";


    String SEMI_COLON = ";";


    String PERIOD = ".";


    String COMMA = ",";


    String INTEGER = " INTEGER";


    String TEXT = " TEXT";


    String INTEGER_PRIMARY_KEY = " INTEGER PRIMARY KEY";


    String INTEGER_PRIMARY_KEY_AUTO_INCREMENT =
            INTEGER_PRIMARY_KEY + " AUTOINCREMENT NOT NULL ";


    String TEXT_PRIMARY_KEY = " TEXT PRIMARY KEY";


    String CREATE_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS ";


    String CREATE_VIEW_IF_NOT_EXISTS = "CREATE VIEW IF NOT EXISTS ";


    String DROP_VIEW_IF_EXISTS = "DROP VIEW IF EXISTS %s";


    String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS %s";
}
