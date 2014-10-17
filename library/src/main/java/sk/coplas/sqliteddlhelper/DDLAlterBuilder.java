package sk.coplas.sqliteddlhelper;

/**
 * Created by CoPLaS on 15.10.2014.
 */
public class DDLAlterBuilder extends DDLColumnBuilder{


    private String tableName;

    protected DDLAlterBuilder(String tableName){
        super();
        this.tableName = tableName;
    }

    public DDLAlterBuilder autoIncrement() {
        super.autoIncrement();
        return this;
    }

    public DDLAlterBuilder pk() {
        super.pk();
        return this;
    }

    public DDLAlterBuilder notNull() {
        super.notNull();
        return this;
    }


    public DDLAlterBuilder column(String name, ColumnType type) {
        super.column(name, type);
        return this;
    }


    public DDLAlterBuilder text(String columnName) {
        return column(columnName, ColumnType.TEXT);
    }

    public DDLAlterBuilder numeric(String columnName) {
        return column(columnName, ColumnType.NUMERIC);
    }

    public DDLAlterBuilder integer(String columnName) {
        return column(columnName, ColumnType.INTEGER);
    }

    public DDLAlterBuilder real(String columnName) {
        return column(columnName, ColumnType.REAL);
    }

    public DDLAlterBuilder bool(String columnName) {
        return column(columnName, ColumnType.BOOLEAN);
    }

    public DDLAlterBuilder none(String columnName) {
        return column(columnName, ColumnType.NONE);
    }

    public String build(){
        StringBuilder sb = new StringBuilder();
        sb.append("ALTER TABLE ");
        sb.append(tableName);
        sb.append(" ADD COLUMN ");
        sb.append(super.build());
        return sb.toString();
    }

}
