package sk.coplas.sqliteddlhelper.sample;

import android.database.sqlite.SQLiteDatabase;

import se.emilsjolander.sprinkles.Migration;
import sk.coplas.sqliteddlhelper.DDLBuilderFactory;

/**
 * Created by coplas on 10/2/14.
 */
public class MigrationsFactory {

    public static Migration itemCreate() {
        return new Migration() {
            @Override
            protected void doMigration(SQLiteDatabase db) {
                try {
                    String sql = DDLBuilderFactory.createTable("Item")
                            .integer("id").pk().autoIncrement()
                            .text("name")
                            .integer("value")
                            .build();
                    db.execSQL(sql);
                } catch (android.database.SQLException e){
//                    Utils.e("Error running sql statement: " + sql);
//                    Utils.e(e);
                }
            }
        };
    }

    public static Migration itemAlter() {
        return new Migration() {
            @Override
            protected void doMigration(SQLiteDatabase db) {
                try {
                    String sql = DDLBuilderFactory.alterTable("Item")
                            .text("value2")
                            .build();
                    db.execSQL(sql);
                } catch (android.database.SQLException e){
//                    Utils.e("Error running sql statement: " + sql);
//                    Utils.e(e);
                }
            }
        };
    }

}
