package rblstudios.com.cadastroproduto.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

/**
 * Criado por renan.lucas em 06/11/2017.
 */

public class Produto implements Parcelable {

    public static final String TABLE = "produto";

    public static final String ID = "id";
    public static final String NOME = "nome";
    public static final String DESCRICAO = "descricao";
    public static final String MARCA = "marca";
    public static final String PRECO_COMPRA = "preco_compra";
    public static final String PRECO_VENDA = "preco_venda";
    public static final String IMAGEM = "imagem";
    public static final String ATIVO = "ativo";

    private int id;
    private String nome;
    private String descricao;
    private String marca;
    private double precoCompra;
    private double precoVenda;
    private byte[] imagem;
    private int ativo;

    public Produto() {}

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(double precoCompra) {
        this.precoCompra = precoCompra;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }

    public Produto(Parcel in) {
        String[] data = new String[8];

        in.readStringArray(data);
        this.id = Integer.parseInt(data[0]);
        this.nome = data[1];
        this.descricao = data[2];
        this.marca = data[3];
        this.precoCompra = Double.parseDouble(data[4]);
        this.precoVenda = Double.parseDouble(data[5]);
        this.imagem = Base64.decode(data[6], Base64.DEFAULT);
        this.ativo = Integer.parseInt(data[7]);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                String.valueOf(this.id),
                this.nome,
                this.descricao,
                this.marca,
                String.valueOf(this.precoCompra),
                String.valueOf(this.precoVenda),
                Base64.encodeToString(this.imagem, Base64.DEFAULT),
                String.valueOf(this.ativo)
      });
    }

    public static final Parcelable.Creator<Produto> CREATOR = new Parcelable.Creator<Produto>() {

        @Override
        public Produto createFromParcel(Parcel source) {
            return new Produto(source);
        }

        @Override
        public Produto[] newArray(int size) {
            return new Produto[size];
        }
    };
}
