package rblstudios.com.cadastroproduto.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import rblstudios.com.cadastroproduto.R;
import rblstudios.com.cadastroproduto.adapter.AdapterMarcasList;
import rblstudios.com.cadastroproduto.adapter.RecyclerViewClickListener;
import rblstudios.com.cadastroproduto.controller.MarcaController;
import rblstudios.com.cadastroproduto.model.Marca;
import rblstudios.com.cadastroproduto.util.LocaleHelper;
import rblstudios.com.cadastroproduto.util.PreferenciasCompartilhadasUtil;

public class ListagemMarcas extends AppCompatActivity {

    private FloatingActionButton btnCadastrarMarcas;
    private RecyclerView recyclerMarcas;
    private String linguagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_marcas);

        encontrarViewsPorId();
        criaListMarcas();
        criaListenerBotao();
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
        btnCadastrarMarcas = (FloatingActionButton) findViewById(R.id.ListagemMarca_btnCadastrarMarca);
        recyclerMarcas = (RecyclerView) findViewById(R.id.ListagemMarcas_RecyclerViewMarcas);
    }

    private void criaListMarcas() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerMarcas.setHasFixedSize(true);
        recyclerMarcas.setLayoutManager(layoutManager);
        recyclerMarcas.setItemAnimator(new DefaultItemAnimator());

        final MarcaController marcaController = new MarcaController(this);
        final List<Marca> marcas = marcaController.listarTudo();

        final AdapterMarcasList adapterMarcasList = new AdapterMarcasList(ListagemMarcas.this, marcas, new RecyclerViewClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                // ALTERAÇÂO
                Marca marcaSelecionada = marcas.get(position);

                Marca marcaBanco = marcaController.listarPorId(marcaSelecionada.getId());

                Intent intent = new Intent(ListagemMarcas.this, CadastroMarca.class);
                intent.putExtra("idMarca", marcaBanco.getId());
                startActivity(intent);
            }
        });

        recyclerMarcas.setAdapter(adapterMarcasList);
    }

    private void criaListenerBotao() {
        btnCadastrarMarcas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListagemMarcas.this, CadastroMarca.class);
                startActivity(intent);
            }
        });
    }
}
