package sk.coplas.sqliteddlhelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by coplas on 10/2/14.
 */
public class DDLBuilder {


    private String tableName;
    private LinkedHashMap<String, ColumnType> columns;
    private String primaryKey;
    private String autoincrement;
    private String columnNameCache;
    private List<String> notNullcolumns;
    private boolean isAlter;

    private DDLBuilder(){
        columns = new LinkedHashMap<String, ColumnType>();
        notNullcolumns = new ArrayList<String>();
    }

    public static DDLBuilder createTable(String tableName) {
        DDLBuilder builder = new DDLBuilder();
        builder.tableName = tableName;
        builder.isAlter = false;
        return builder;
    }

    public static DDLBuilder alterTable(String tableName) {
        DDLBuilder builder = new DDLBuilder();
        builder.tableName = tableName;
        builder.isAlter = true;
        return builder;
    }


    public String build(){
        StringBuilder sb = new StringBuilder();
        if (tableName == null || tableName.length() < 0) {
            throw new IllegalArgumentException("Table name is required");
        }
        if (isAlter) {
            if (columns.size() != 1) {
                throw new IllegalArgumentException("One column is required in SQLite ALTER");
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
                createColumnParams(sb, columnName);
            }
        } else {
            if (columns.size() < 1) {
                throw new IllegalArgumentException("At least one column is required in SQLite CREATE");
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
                    createColumnParams(sb, columnName);
                    if (columnsIterator.hasNext()) {
                        sb.append(", ");
                    }
                }
                sb.append(")");
            }
        }

        return sb.toString();
    }

    private void createColumnParams(StringBuilder sb, String columnName) {
        if (columnName.equals( primaryKey)) {
            sb.append(" PRIMARY KEY");
        }
        if (columnName.equals( autoincrement)) {
            sb.append(" AUTOINCREMENT");
        }
        if (notNullcolumns.contains( columnName)) {
            sb.append(" NOT NULL");
        }
    }

    public static enum ColumnType {
        TEXT,NUMERIC,INTEGER,REAL,BOOLEAN, NONE
    }

    private class SingleColumnBuilder{


        private String name;
        private ColumnType type;
        private boolean pk;
        private boolean autoIncrement;
        private boolean notNull;

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

        public DDLBuilder notNull() {
            this.notNullcolumns.add( columnNameCache);
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


        public DDLBuilder column(String columnName, ColumnType columnType) {
            if (isAlter && columns.size() > 0) {
                throw new IllegalArgumentException("Only one column is allowed in SQLite ALTER");
                //http://stackoverflow.com/questions/6172815/sqlite-alter-createTableBuilder-add-multiple-columns-in-a-single-statement
            }

            if (!columns.containsKey(columnName)) {
                columns.put(columnName, columnType);
            }
            columnNameCache = columnName;
            return this;
        }

    }

    private class MultiColumnBuilder extends SingleColumnBuilder{
        private List<SingleColumnBuilder> columns;

        private SingleColumnBuilder column;

        private MultiColumnBuilder() {
            columns = new ArrayList<SingleColumnBuilder>();
        }

        public MultiColumnBuilder column() {
            if (column != null) {
                columns.add( column);
            }
            column = new SingleColumnBuilder();
            return this;
        }

    }


}
