package rblstudios.com.cadastroproduto.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import rblstudios.com.cadastroproduto.datatabase.DbHelper;
import rblstudios.com.cadastroproduto.interfaces.ICrud;
import rblstudios.com.cadastroproduto.model.Marca;

/**
 * Criado por renan.lucas em 29/11/2017.
 */

public class MarcaDAO implements ICrud<Marca> {

    private DbHelper dbHelper;

    public MarcaDAO(Context ctx) {
        this.dbHelper = new DbHelper(ctx);
    }

    public int inserir(Marca marca) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Marca.NOME, marca.getNome());
        long id = db.insert(Marca.TABLE, null, valores);

        db.close();
        return (int) id;
    }

    public void atualizar(Marca marca) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Marca.NOME, marca.getNome());
        db.update(Marca.TABLE, valores, Marca.ID + " = ?", new String[]{String.valueOf(marca.getId())});

        db.close();
    }

    public void excluir(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Marca.TABLE, Marca.ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<Marca> listarTudo() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "SELECT "
                + Marca.ID + ", "
                + Marca.NOME
                + " FROM " + Marca.TABLE
                + " ORDER BY " + Marca.NOME;

        List<Marca> listMarca = new ArrayList<>();

        Cursor cursor = db.rawQuery(query, null);

        // Percorre todas as linhas encontradas na query
        if (cursor.moveToFirst()) {
            do {
                Marca marca = new Marca();
                marca.setId(cursor.getInt(cursor.getColumnIndex(Marca.ID)));
                marca.setNome(cursor.getString(cursor.getColumnIndex(Marca.NOME)));

                listMarca.add(marca);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return listMarca;
    }

    public Marca listarPorId(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "SELECT "
                + Marca.ID + ", "
                + Marca.NOME
                + " FROM " + Marca.TABLE
                + " WHERE " + Marca.ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        Marca marca = new Marca();
        // Percorre todas as linhas encontradas na query
        if (cursor.moveToFirst()) {
            do {
                marca.setId(cursor.getInt(cursor.getColumnIndex(Marca.ID)));
                marca.setNome(cursor.getString(cursor.getColumnIndex(Marca.NOME)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return marca;
    }
}
