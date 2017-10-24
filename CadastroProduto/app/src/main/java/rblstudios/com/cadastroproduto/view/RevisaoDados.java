package rblstudios.com.cadastroproduto.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import rblstudios.com.cadastroproduto.R;
import rblstudios.com.cadastroproduto.util.Util;

public class RevisaoDados extends AppCompatActivity {

    private Button btnVoltar, btnCadastrar;
    private ImageView imgProduto;
    private TextView txtNomeProduto ,txtDescricaoProduto, txtMarcaProduto, txtPrecoCompra, txtPrecoVenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisao_dados);

        encontraViewsPorId();
        populaDados();
        defineListenerBotoes();
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
    }

    /**
     * Popula a tela com dados vindos do intent
     */
    private void populaDados() {
        if (getIntent().hasExtra("nomeProduto") && getIntent().hasExtra("descricaoProduto")
                && getIntent().hasExtra("marcaProduto") && getIntent().hasExtra("precoCompraProduto")
                && getIntent().hasExtra("precoVendaProduto") && getIntent().hasExtra("fotoProduto")) {
            txtNomeProduto.setText(getIntent().getStringExtra("nomeProduto").toUpperCase());
            txtDescricaoProduto.setText(getIntent().getStringExtra("descricaoProduto").toUpperCase());
            txtMarcaProduto.setText(getIntent().getStringExtra("marcaProduto"));
            txtPrecoCompra.setText(getIntent().getStringExtra("precoCompraProduto"));
            txtPrecoVenda.setText(getIntent().getStringExtra("precoVendaProduto"));
            imgProduto.setImageBitmap(Util.converteByteArrayParaBitmap(getIntent().getByteArrayExtra("fotoProduto")));
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
                                    Intent intent = new Intent(RevisaoDados.this,CadastroProduto.class);
                                    startActivity(intent);
                                    RevisaoDados.this.finish();
                                }
                            });

                    AlertDialog alerta = construtorAlerta.create();
                    alerta.show();
            }
        });
    }
}
