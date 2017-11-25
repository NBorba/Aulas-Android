package rblstudios.com.cadastroproduto.view;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import rblstudios.com.cadastroproduto.R;
import rblstudios.com.cadastroproduto.controller.ProdutoController;
import rblstudios.com.cadastroproduto.model.Produto;
import rblstudios.com.cadastroproduto.util.LocaleHelper;
import rblstudios.com.cadastroproduto.util.NumberUtil;
import rblstudios.com.cadastroproduto.util.PreferenciasCompartilhadasUtil;
import rblstudios.com.cadastroproduto.util.Util;
import rblstudios.com.cadastroproduto.util.ViewUtil;

public class RevisaoDados extends AppCompatActivity {

    private Button btnVoltar, btnCadastrar;
    private ImageView imgProduto;
    private TextView txtDescricaoActivity, txtNomeProduto, txtDescricaoProduto, txtMarcaProduto, txtPrecoCompra, txtPrecoVenda, txtProdutoAtivo;
    private String linguagem;
    private String crud;

    private static final int NOTIFICATION_ID = 1;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisao_dados);

        encontraViewsPorId();

        defineInclusaoAlteracao();

        // Popula os dados do produto que vieram da tela de cadastro
        populaDados();

        // Listeners
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
        // Buscamos a linguagem definida pelo usuário e montamos a tela
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
        txtDescricaoActivity = (TextView) findViewById(R.id.RevisaoDados_txtSubtitulo);
        txtMarcaProduto = (TextView) findViewById(R.id.RevisaoDados_txtMarca);
        txtNomeProduto = (TextView) findViewById(R.id.RevisaoDados_txtNome);
        txtDescricaoProduto = (TextView) findViewById(R.id.RevisaoDados_txtDescricao);
        txtPrecoCompra = (TextView) findViewById(R.id.RevisaoDados_txtPrecoCompra);
        txtPrecoVenda = (TextView) findViewById(R.id.RevisaoDados_txtPrecoVenda);
        txtProdutoAtivo = (TextView) findViewById(R.id.RevisaoDados_txtProdutoAtivo);
    }

    private void defineInclusaoAlteracao() {
        if (getIntent().hasExtra("crud")) {
            crud = getIntent().getStringExtra("crud");
        } else {
            crud = "inclusao";
        }

        if (!crud.equals("inclusao")) {
            btnCadastrar.setText(getString(R.string.botaoAlterar));
            txtDescricaoActivity.setText(getString(R.string.RevisaoDados_title_subtitulo_alteracao));
        }
    }

    /**
     * Popula a tela com dados vindos do intent
     */
    private void populaDados() {
        // Verifica se o intent trouxe tudo
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

            // Baseado no status do produto, muda a mensagem e a cor
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
                if (crud == "inclusao") {
                    cadastrarProduto();
                    exibirNotificacao();

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

                    // Botão de compartilhamento
                    construtorAlerta.setNegativeButton(
                            getString(R.string.botaoCompartilhar),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intentCompartilhamento = new Intent(android.content.Intent.ACTION_SEND);
                                    intentCompartilhamento.setType("text/plain");

                                    String textoCompartilhamento;

                                    // Se o produto está ativo mostramos mensagem que está disponível no link
                                    // Se não, mandamos mensagem que acabamos de cadastrar
                                    if (getIntent().getBooleanExtra("produtoAtivo", true)) {
                                        textoCompartilhamento = getString(R.string.RevisaoDados_title_compartilhamentoprodutoativo,
                                                txtNomeProduto.getText(), "www.meusite.com.br/meuproduto");
                                    } else {
                                        textoCompartilhamento = getString(R.string.RevisaoDados_title_compartilhamentoprodutoinativo, txtNomeProduto.getText());
                                    }

                                    String assuntoCompartilhamento = "MeuSite";
                                    intentCompartilhamento.putExtra(android.content.Intent.EXTRA_SUBJECT, assuntoCompartilhamento);
                                    intentCompartilhamento.putExtra(android.content.Intent.EXTRA_TEXT, textoCompartilhamento);
                                    // Compartilhamos o cadastro
                                    startActivity(Intent.createChooser(intentCompartilhamento, getString(R.string.title_compartilharvia)));

                                    RevisaoDados.this.finishAffinity();
                                }
                            });

                    AlertDialog alerta = construtorAlerta.create();
                    alerta.show();
                } else {

                }
            }
        });

    }

    private void cadastrarProduto() {
        ProdutoController produtoController = new ProdutoController(this);
        try {
            Produto produto = new Produto();
            produto.setNome(txtNomeProduto.getText().toString());
            produto.setDescricao(txtDescricaoProduto.getText().toString());
            produto.setMarca(txtMarcaProduto.getText().toString());
            produto.setPrecoCompra(NumberUtil.parseParaDouble(txtPrecoCompra.getText().toString().replace("R$", "")));
            produto.setPrecoVenda(NumberUtil.parseParaDouble(txtPrecoVenda.getText().toString().replace("R$", "")));
            produto.setImagem(getIntent().getByteArrayExtra("fotoProduto"));
            produto.setAtivo(getIntent().getBooleanExtra("produtoAtivo", true) ? 1 : 0);
            produtoController.inserir(produto);
        } catch (Exception ex) {
            ViewUtil.criaEMostraAlert(this, getString(R.string.title_erro), ex.getMessage().trim(),
                    getString(R.string.botaoOK), true);
        }
    }

    private void atualizarProduto() {
        ProdutoController produtoController = new ProdutoController(this);
        try {
            Produto produto = new Produto();
            produto.setNome(txtNomeProduto.getText().toString());
            produto.setDescricao(txtDescricaoProduto.getText().toString());
            produto.setMarca(txtMarcaProduto.getText().toString());
            produto.setPrecoCompra(NumberUtil.parseParaDouble(txtPrecoCompra.getText().toString().replace("R$", "")));
            produto.setPrecoVenda(NumberUtil.parseParaDouble(txtPrecoVenda.getText().toString().replace("R$", "")));
            produto.setImagem(getIntent().getByteArrayExtra("fotoProduto"));
            produto.setAtivo(getIntent().getBooleanExtra("produtoAtivo", true) ? 1 : 0);
            produtoController.inserir(produto);
        } catch (Exception ex) {
            ViewUtil.criaEMostraAlert(this, getString(R.string.title_erro), ex.getMessage().trim(),
                    getString(R.string.botaoOK), true);
        }
    }


    private void exibirNotificacao() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_settings)
                .setContentTitle(txtNomeProduto.getText().toString())
                .setContentText(getString(R.string.mensagem_produtocadastradosucesso))
                .setPriority(2);

        Intent retornoNotificacao = new Intent(this, ListagemProdutos.class);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(ListagemProdutos.class);

        taskStackBuilder.addNextIntent(retornoNotificacao);
        PendingIntent intentPendenteNotificacao = taskStackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(intentPendenteNotificacao);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void downloadNotificacao() {
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
    }
}
