package rblstudios.com.cadastroproduto.datatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import rblstudios.com.cadastroproduto.model.Produto;

/**
 * Criado por renan.lucas on 06/11/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "app.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        criaTabelaProduto(db);
    }

    private void criaTabelaProduto(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + Produto.TABLE + "("
                + Produto.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Produto.NOME + " VARCHAR(150), "
                + Produto.DESCRICAO + " VARCHAR(200), "
                + Produto.MARCA + " VARCHAR(100), "
                + Produto.PRECO_COMPRA + " DOUBLE, "
                + Produto.PRECO_VENDA + " DOUBLE, "
                + Produto.IMAGEM + " BLOB, "
                + Produto.ATIVO + " INT)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + Produto.TABLE;
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }

    // FUNÇÃO UTILIZADA POR "ANDROID DATABASE MANAGER" PARA VISUALIZAR BANCOS DE DADOS SQLITE, APAGAR EM PRODUÇÃO
    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"message"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (SQLException sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        } catch (Exception ex) {
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }
}
