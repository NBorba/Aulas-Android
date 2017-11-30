package rblstudios.com.cadastroproduto.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

/**
 * Criado por renan.lucas em 06/11/2017.
 */

public class Marca {

    public static final String TABLE = "marca";

    public static final String ID = "id";
    public static final String NOME = "nome";

    private int id;
    private String nome;

    public Marca() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
