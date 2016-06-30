package sk.coplas.sqliteddlhelper;

/**
 * Created by CoPLaS on 15.10.2014.
 */
public class DDLColumnBuilder {

    private String name;
    private ColumnType type;
    private boolean pk;
    private boolean autoIncrement;
    private boolean notNull;

    protected DDLColumnBuilder(){
    }

    protected DDLColumnBuilder autoIncrement() {
        this.autoIncrement = true;
        return this;
    }

    protected DDLColumnBuilder pk() {
        this.pk = true;
        return this;
    }

    protected DDLColumnBuilder notNull() {
        this.notNull = true;
        return this;
    }


    protected DDLColumnBuilder column(String name, ColumnType type) {
        this.name = name;
        this.type = type;
        return this;
    }


    protected DDLColumnBuilder text(String columnName) {
        return column(columnName, ColumnType.TEXT);
    }

    protected DDLColumnBuilder numeric(String columnName) {
        return column(columnName, ColumnType.NUMERIC);
    }

    protected DDLColumnBuilder integer(String columnName) {
        return column(columnName, ColumnType.INTEGER);
    }

    protected DDLColumnBuilder real(String columnName) {
        return column(columnName, ColumnType.REAL);
    }

    protected DDLColumnBuilder bool(String columnName) {
        return column(columnName, ColumnType.BOOLEAN);
    }

    protected DDLColumnBuilder none(String columnName) {
        return column(columnName, ColumnType.NONE);
    }



    protected String build(){
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(" ");
        sb.append(type.toString());
        createColumnParams(sb);

        return sb.toString();
    }

    private void createColumnParams(StringBuilder sb) {
        if (pk) {
            sb.append(" PRIMARY KEY");
        }
        if (autoIncrement) {
            sb.append(" AUTOINCREMENT");
        }
        if (notNull) {
            sb.append(" NOT NULL");
        }
    }

}
