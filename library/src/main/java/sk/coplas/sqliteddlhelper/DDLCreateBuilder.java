package sk.coplas.sqliteddlhelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by CoPLaS on 15.10.2014.
 */
public class DDLCreateBuilder {


    private String tableName;
    private List<DDLColumnBuilder> columns;
    private DDLColumnBuilder currentColumn;

    protected DDLCreateBuilder(String tableName){
        this.tableName = tableName;
        columns = new ArrayList<DDLColumnBuilder>();
    }

    public DDLCreateBuilder autoIncrement() {
        currentColumn.autoIncrement();
        return this;
    }

    public DDLCreateBuilder pk() {
        currentColumn.pk();
        return this;
    }

    public DDLCreateBuilder notNull() {
        currentColumn.notNull();
        return this;
    }


    public DDLCreateBuilder column(String name, ColumnType type) {
        currentColumn = new DDLColumnBuilder();
        currentColumn.column(name, type);
        columns.add( currentColumn);
        return this;
    }


    public DDLCreateBuilder text(String columnName) {
        return column(columnName, ColumnType.TEXT);
    }

    public DDLCreateBuilder numeric(String columnName) {
        return column(columnName, ColumnType.NUMERIC);
    }

    public DDLCreateBuilder integer(String columnName) {
        return column(columnName, ColumnType.INTEGER);
    }

    public DDLCreateBuilder real(String columnName) {
        return column(columnName, ColumnType.REAL);
    }

    public DDLCreateBuilder bool(String columnName) {
        return column(columnName, ColumnType.BOOLEAN);
    }

    public DDLCreateBuilder none(String columnName) {
        return column(columnName, ColumnType.NONE);
    }

    public String build(){
        StringBuilder sb = new StringBuilder();
        if (tableName == null || tableName.length() < 0) {
            throw new IllegalArgumentException("Table name is required");
        }
        if (columns.size() < 1) {
            throw new IllegalArgumentException("At least one column is required in SQLite CREATE");
        }

        sb.append("CREATE TABLE ");
        sb.append(tableName);
        sb.append(" ( ");
        Iterator<DDLColumnBuilder> iterator = columns.iterator();
        while (iterator.hasNext()) {
            DDLColumnBuilder column = iterator.next();
            sb.append(column.build());
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(")");


        return sb.toString();
    }

}
