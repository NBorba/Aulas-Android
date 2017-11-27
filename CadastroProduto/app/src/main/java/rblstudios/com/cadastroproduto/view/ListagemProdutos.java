package rblstudios.com.cadastroproduto.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import rblstudios.com.cadastroproduto.R;
import rblstudios.com.cadastroproduto.adapter.AdapterProdutosList;
import rblstudios.com.cadastroproduto.adapter.RecyclerViewClickListener;
import rblstudios.com.cadastroproduto.controller.ProdutoController;
import rblstudios.com.cadastroproduto.model.Produto;
import rblstudios.com.cadastroproduto.util.LocaleHelper;
import rblstudios.com.cadastroproduto.util.PreferenciasCompartilhadasUtil;
import rblstudios.com.cadastroproduto.util.ViewUtil;

public class ListagemProdutos extends AppCompatActivity {

    private RecyclerView recyclerProdutos;
    private String linguagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_produtos);

        encontrarViewsPorId();
        criaListProdutos();
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

    private void encontrarViewsPorId() {
        recyclerProdutos = (RecyclerView) findViewById(R.id.ListagemProduto_RecyclerViewProdutos);
    }

    private void criaListProdutos() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerProdutos.setHasFixedSize(true);
        recyclerProdutos.setLayoutManager(layoutManager);
        recyclerProdutos.setItemAnimator(new DefaultItemAnimator());

        final ProdutoController produtoController = new ProdutoController(this);
        final List<Produto> produtos = produtoController.listarTudo();

        final AdapterProdutosList adapterProdutosList = new AdapterProdutosList(ListagemProdutos.this, produtos, new RecyclerViewClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                // ALTERAÇÂO
                Produto produtoSelecionado = produtos.get(position);

                Produto produtoBanco = produtoController.listarPorId(produtoSelecionado.getId());

                Intent intent = new Intent(ListagemProdutos.this, CadastroProduto.class);
                intent.putExtra("produtoAlteracao", produtoBanco);
                startActivity(intent);
            }
        });

        recyclerProdutos.setAdapter(adapterProdutosList);
    }
}
