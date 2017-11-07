package rblstudios.com.cadastroproduto.model;

/**
 * Criado por renan.lucas em 06/11/2017.
 */

public class Produto {

    public static final String TABLE = "produto";

    public static final String ID = "id";
    public static final String NOME = "nome";
    public static final String DESCRICAO = "descricao";
    public static final String MARCA = "marca";
    public static final String IMAGEM = "imagem";

    private int id;
    private String nome;
    private String descricao;
    private String marca;
    private byte[] imagem;

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

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
}
