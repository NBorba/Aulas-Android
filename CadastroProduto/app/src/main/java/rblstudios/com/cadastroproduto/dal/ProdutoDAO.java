package rblstudios.com.cadastroproduto.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import rblstudios.com.cadastroproduto.datatabase.DbHelper;
import rblstudios.com.cadastroproduto.interfaces.ICrud;
import rblstudios.com.cadastroproduto.model.Produto;

/**
 * Criado por renan.lucas em 06/11/2017.
 */

public class ProdutoDAO implements ICrud<Produto> {

    private DbHelper dbHelper;

    public ProdutoDAO(Context ctx) {
        this.dbHelper = new DbHelper(ctx);
    }

    public int inserir(Produto produto) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Produto.NOME, produto.getNome());
        valores.put(Produto.DESCRICAO, produto.getDescricao());
        valores.put(Produto.MARCA, produto.getMarca());
        valores.put(Produto.IMAGEM, produto.getImagem());
        long id = db.insert(Produto.TABLE, null, valores);

        db.close();
        return (int) id;
    }

    public void atualizar(Produto produto) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Produto.NOME, produto.getNome());
        valores.put(Produto.DESCRICAO, produto.getDescricao());
        valores.put(Produto.MARCA, produto.getMarca());
        db.update(Produto.TABLE, valores, Produto.ID + " = ?", new String[]{String.valueOf(produto.getId())});

        db.close();
    }

    public void excluir(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Produto.TABLE, Produto.ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<Produto> listarTudo() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "SELECT "
                + Produto.ID + ", "
                + Produto.NOME + ", "
                + Produto.DESCRICAO + ", "
                + Produto.MARCA + ", "
                + Produto.IMAGEM
                + " FROM " + Produto.TABLE
                + " ORDER BY " + Produto.NOME;

        List<Produto> listProduto = new ArrayList<>();

        Cursor cursor = db.rawQuery(query, null);

        // Percorre todas as linhas encontradas na query
        if (cursor.moveToFirst()) {
            do {
                Produto produto = new Produto();
                produto.setId(cursor.getInt(cursor.getColumnIndex(Produto.ID)));
                produto.setNome(cursor.getString(cursor.getColumnIndex(Produto.NOME)));
                produto.setDescricao(cursor.getString(cursor.getColumnIndex(Produto.DESCRICAO)));
                produto.setMarca(cursor.getString(cursor.getColumnIndex(Produto.MARCA)));
                produto.setImagem(cursor.getBlob(cursor.getColumnIndex(Produto.IMAGEM)));

                listProduto.add(produto);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return listProduto;
    }

    public Produto listarPorId(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "SELECT "
                + Produto.ID + ", "
                + Produto.NOME + ", "
                + Produto.DESCRICAO + ", "
                + Produto.MARCA + ", "
                + Produto.IMAGEM
                + " FROM " + Produto.TABLE
                + " WHERE " + Produto.ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        Produto produto = new Produto();
        // Percorre todas as linhas encontradas na query
        if (cursor.moveToFirst()) {
            do {
                produto.setId(cursor.getInt(cursor.getColumnIndex(Produto.ID)));
                produto.setNome(cursor.getString(cursor.getColumnIndex(Produto.NOME)));
                produto.setDescricao(cursor.getString(cursor.getColumnIndex(Produto.DESCRICAO)));
                produto.setMarca(cursor.getString(cursor.getColumnIndex(Produto.MARCA)));
                produto.setImagem(cursor.getBlob(cursor.getColumnIndex(Produto.IMAGEM)));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return produto;
    }
}
