package rblstudios.com.cadastroproduto.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import rblstudios.com.cadastroproduto.R;
import rblstudios.com.cadastroproduto.util.LocaleHelper;
import rblstudios.com.cadastroproduto.util.PreferenciasCompartilhadasUtil;
import rblstudios.com.cadastroproduto.util.Util;

public class RevisaoDados extends AppCompatActivity {

    private Button btnVoltar, btnCadastrar;
    private ImageView imgProduto;
    private TextView txtNomeProduto, txtDescricaoProduto, txtMarcaProduto, txtPrecoCompra, txtPrecoVenda, txtProdutoAtivo;
    private String linguagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisao_dados);

        encontraViewsPorId();
        populaDados();
        defineListenerBotoes();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Se a linguagem mudou recriamos a tela
        if (!linguagem.equals(PreferenciasCompartilhadasUtil.getSharedPreferenceString(this, getString(R.string.preferencia_linguagem), "pt"))) {
            recreate();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        SharedPreferences sharedPref = base.getSharedPreferences(base.getString(R.string.arquivo_preferencias), Context.MODE_PRIVATE);
        linguagem = sharedPref.getString(base.getString(R.string.preferencia_linguagem), "pt");
        super.attachBaseContext(LocaleHelper.onAttach(base, linguagem));
    }

    /**
     * Encontra views via Id no layout XML
     */
    private void encontraViewsPorId() {
        btnVoltar = (Button) findViewById(R.id.RevisaoDados_btnVoltar);
        btnCadastrar = (Button) findViewById(R.id.RevisaoDados_btnCadastrar);
        imgProduto = (ImageView) findViewById(R.id.RevisaoDados_imgProduto);
        txtMarcaProduto = (TextView) findViewById(R.id.RevisaoDados_txtMarca);
        txtNomeProduto = (TextView) findViewById(R.id.RevisaoDados_txtNome);
        txtDescricaoProduto = (TextView) findViewById(R.id.RevisaoDados_txtDescricao);
        txtPrecoCompra = (TextView) findViewById(R.id.RevisaoDados_txtPrecoCompra);
        txtPrecoVenda = (TextView) findViewById(R.id.RevisaoDados_txtPrecoVenda);
        txtProdutoAtivo = (TextView) findViewById(R.id.RevisaoDados_txtProdutoAtivo);
    }

    /**
     * Popula a tela com dados vindos do intent
     */
    private void populaDados() {
        if (getIntent().hasExtra("nomeProduto") && getIntent().hasExtra("descricaoProduto")
                && getIntent().hasExtra("marcaProduto") && getIntent().hasExtra("precoCompraProduto")
                && getIntent().hasExtra("precoVendaProduto") && getIntent().hasExtra("fotoProduto")
                && getIntent().hasExtra("produtoAtivo")) {
            txtNomeProduto.setText(getIntent().getStringExtra("nomeProduto").toUpperCase());
            txtDescricaoProduto.setText(getIntent().getStringExtra("descricaoProduto").toUpperCase());
            txtMarcaProduto.setText(getIntent().getStringExtra("marcaProduto"));
            txtPrecoCompra.setText(getIntent().getStringExtra("precoCompraProduto"));
            txtPrecoVenda.setText(getIntent().getStringExtra("precoVendaProduto"));
            imgProduto.setImageBitmap(Util.converteByteArrayParaBitmap(getIntent().getByteArrayExtra("fotoProduto")));

            if (getIntent().getBooleanExtra("produtoAtivo", true)) {
                txtProdutoAtivo.setText(getString(R.string.RevisaoDados_title_produtoAtivo));
                txtProdutoAtivo.setTextColor(ContextCompat.getColor(this, R.color.verde));
            } else {
                txtProdutoAtivo.setText(getString(R.string.RevisaoDados_title_produtoInativo));
                txtProdutoAtivo.setTextColor(ContextCompat.getColor(this, R.color.vermelho));
            }
        }
    }

    private void defineListenerBotoes() {
        // Realiza o clique de voltar
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RevisaoDados.super.onBackPressed();
            }
        });

        // Cadastra o produto e volta para a tela de cadastro
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Constroi o alerta
                AlertDialog.Builder construtorAlerta = new AlertDialog.Builder(RevisaoDados.this);
                    construtorAlerta.setMessage(getString(R.string.mensagem_produtocadastradosucesso));
                    construtorAlerta.setCancelable(true);

                    construtorAlerta.setPositiveButton(
                            getString(R.string.botaoOK),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Volta para a tela de cadastro
                                    Intent intent = new Intent(RevisaoDados.this, MainActivity.class);
                                    startActivity(intent);
                                    RevisaoDados.this.finishAffinity();
                                }
                            });

                    construtorAlerta.setNegativeButton(
                        getString(R.string.botaoCompartilhar),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Compartilhamos o cadastro
                                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                                sharingIntent.setType("text/plain");

                                String textoCompartilhamento;
                                if (getIntent().getBooleanExtra("produtoAtivo", true)) {
                                    textoCompartilhamento = getString(R.string.RevisaoDados_title_compartilhamentoprodutoativo,
                                            txtNomeProduto.getText(), "www.meusite.com.br/meuproduto");
                                } else {
                                    textoCompartilhamento = getString(R.string.RevisaoDados_title_compartilhamentoprodutoinativo, txtNomeProduto.getText());
                                }

                                String assuntoCompartilhamento = "MeuSite";
                                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, assuntoCompartilhamento);
                                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, textoCompartilhamento);
                                startActivity(Intent.createChooser(sharingIntent, getString(R.string.title_compartilharvia)));

                                RevisaoDados.this.finishAffinity();
                            }
                        });

                    AlertDialog alerta = construtorAlerta.create();
                    alerta.show();
            }
        });
    }
}
