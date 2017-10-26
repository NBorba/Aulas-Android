package rblstudios.com.cadastroproduto.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import rblstudios.com.cadastroproduto.R;
import rblstudios.com.cadastroproduto.util.LocaleHelper;
import rblstudios.com.cadastroproduto.util.PreferenciasCompartilhadasUtil;

public class MainActivity extends AppCompatActivity {

    private Button btnCadastrarProduto;
    private ImageButton btnConfiguracao;
    private String linguagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        encontrarViewsPorId();
        definirListenerBotoes();
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

    private void encontrarViewsPorId() {
        btnCadastrarProduto = (Button) findViewById(R.id.MainActivity_btnCadastrarProduto);
        btnConfiguracao = (ImageButton) findViewById(R.id.MainActivity_btnConfiguracao);
    }

    private void definirListenerBotoes() {
        btnCadastrarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ao clicar no botão de cadastro de produto, chamamos a activity de cadastro de produto
                Intent intent = new Intent(MainActivity.this, CadastroProduto.class);
                startActivity(intent);
            }
        });

        btnConfiguracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ao clicar no botão de cadastro de produto, chamamos a activity de configurações
                Intent intent = new Intent(MainActivity.this, Configuracao.class);
                startActivity(intent);
            }
        });
    }
}
