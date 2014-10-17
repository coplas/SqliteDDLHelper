package sk.coplas.sqliteddlhelper;

/**
 * Created by coplas on 10/2/14.
 */
public class DDLBuilderFactory {

    public static DDLCreateBuilder createTable(String tableName) {
        DDLCreateBuilder builder = new DDLCreateBuilder(tableName);
        return builder;
    }

    public static DDLAlterBuilder alterTable(String tableName) {
        DDLAlterBuilder builder = new DDLAlterBuilder(tableName);
        return builder;
    }

}
