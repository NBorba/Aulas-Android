package rblstudios.com.cadastroproduto.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import rblstudios.com.cadastroproduto.R;
import rblstudios.com.cadastroproduto.adapter.AdapterProdutosList;
import rblstudios.com.cadastroproduto.adapter.RecyclerViewClickListener;
import rblstudios.com.cadastroproduto.controller.ProdutoController;
import rblstudios.com.cadastroproduto.model.Produto;
import rblstudios.com.cadastroproduto.util.Util;

public class ListagemProdutos extends AppCompatActivity {

    private RecyclerView recyclerProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_produtos);

        encontrarViewsPorId();
        criaListProdutos();
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

        AdapterProdutosList adapterProdutosList = new AdapterProdutosList(produtos, new RecyclerViewClickListener() {
            @Override
            public void onItemClick(View v, int position) {
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
