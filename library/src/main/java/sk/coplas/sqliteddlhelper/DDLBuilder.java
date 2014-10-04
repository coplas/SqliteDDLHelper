package sk.coplas.sqliteddlhelper;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by coplas on 10/2/14.
 */
public class DDLBuilder {

    public static enum ColumnType {
        TEXT,NUMERIC,INTEGER,REAL,BOOLEAN, NONE
    }

    private String tableName;
    private LinkedHashMap<String, ColumnType> columns;
    private String primaryKey;
    private String autoincrement;
    private String columnNameCache;
    private boolean isAlter;

    public DDLBuilder(){
        isAlter = false;
        columns = new LinkedHashMap<String, ColumnType>();
    }

    public DDLBuilder table(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public DDLBuilder pk(String columnName) {
        this.primaryKey = columnName;
        return this;
    }

    public DDLBuilder autoIncrement(String columnName) {
        this.primaryKey = columnName;
        return this;
    }

    public DDLBuilder pk() {
        this.primaryKey = columnNameCache;
        return this;
    }

    public DDLBuilder autoIncrement() {
        this.autoincrement = columnNameCache;
        return this;
    }


    public DDLBuilder text(String columnName) {
        return column(columnName, ColumnType.TEXT);
    }

    public DDLBuilder numeric(String columnName) {
        return column(columnName, ColumnType.NUMERIC);
    }

    public DDLBuilder integer(String columnName) {
        return column(columnName, ColumnType.INTEGER);
    }

    public DDLBuilder real(String columnName) {
        return column(columnName, ColumnType.REAL);
    }

    public DDLBuilder bool(String columnName) {
        return column(columnName, ColumnType.BOOLEAN);
    }

    public DDLBuilder none(String columnName) {
        return column(columnName, ColumnType.NONE);
    }

    public DDLBuilder addColumn(String columnName, ColumnType columnType){
        if (isAlter) {
            throw new UnsupportedOperationException("Only one column is allowed in SQLite ALTER");
            //http://stackoverflow.com/questions/6172815/sqlite-alter-table-add-multiple-columns-in-a-single-statement
        }
        this.isAlter = true;
        return column(columnName, columnType);
    }

    public DDLBuilder create(){
        this.isAlter = false;
        return this;
    }

    public DDLBuilder column(String columnName, ColumnType columnType) {
        if (!columns.containsKey(columnName)) {
            columns.put(columnName, columnType);
        }
        columnNameCache = columnName;
        return this;
    }

    public String build(){
        StringBuilder sb = new StringBuilder();
        if (tableName == null || tableName.length() < 0) {
            throw new UnsupportedOperationException("Table name is required");
        }
        if (isAlter) {
            if (columns.size() != 1) {
                throw new UnsupportedOperationException("One column is required in SQLite ALTER");
            } else {
                //get first column
                String columnName = columns.keySet().iterator().next();
                ColumnType columnType = columns.get( columnName);
                sb.append("ALTER TABLE ");
                sb.append(tableName);
                sb.append(" ADD COLUMN ");
                sb.append(columnName);
                sb.append(" ");
                sb.append(columnType.toString());
                if (columnName.equals( primaryKey)) {
                    sb.append(" PRIMARY KEY");
                }
                if (columnName.equals( autoincrement)) {
                    sb.append(" AUTOINCREMENT");
                }
//                sb.append(";");
            }
        } else {
            if (columns.size() < 1) {
                throw new UnsupportedOperationException("At least one column is required in SQLite CREATE");
            } else {
                sb.append("CREATE TABLE ");
                sb.append(tableName);
                sb.append(" ( ");
                final Iterator<String> columnsIterator = columns.keySet().iterator();
                while (columnsIterator.hasNext()) {
                    String columnName = columnsIterator.next();
                    ColumnType columnType = columns.get( columnName);
                    sb.append(columnName);
                    sb.append(" ");
                    sb.append(columnType.toString());
                    if (columnName.equals( primaryKey)) {
                        sb.append(" PRIMARY KEY");
                    }
                    if (columnName.equals( autoincrement)) {
                        sb.append(" AUTOINCREMENT");
                    }
                    if (columnsIterator.hasNext()) {
                        sb.append(", ");
                    }
                }
                sb.append(")");
//              sb.append(";");
            }
        }

        return sb.toString();
    }



}
