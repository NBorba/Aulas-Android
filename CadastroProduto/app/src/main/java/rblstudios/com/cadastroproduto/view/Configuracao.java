package rblstudios.com.cadastroproduto.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import rblstudios.com.cadastroproduto.R;
import rblstudios.com.cadastroproduto.util.LocaleHelper;
import rblstudios.com.cadastroproduto.util.PreferenciasCompartilhadasUtil;
import rblstudios.com.cadastroproduto.util.ViewUtil;

public class Configuracao extends AppCompatActivity {

    private Button btnVoltar;
    private RadioGroup rgLinguagem;
    private RadioButton rbPortugues, rbIngles;
    private String linguagem;
    private Spinner spinnerMoeda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        encontraViewsPorId();

        // Popula spinner com moedas disponíveis
        popularSpinnerMoeda();

        // Busca configurações anteriores e remonta a tela
        remontaConfiguracoesAnteriores();

        // Listeners
        defineListenerRadioGroupLinguagem();
        defineListenerSpinnerMoeda();
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
        // Monta a tela com a linguagem definida pelo usuário
        SharedPreferences sharedPref = base.getSharedPreferences(base.getString(R.string.arquivo_preferencias), Context.MODE_PRIVATE);
        linguagem = sharedPref.getString(base.getString(R.string.preferencia_linguagem), "pt");
        super.attachBaseContext(LocaleHelper.onAttach(base, linguagem));
    }

    private void encontraViewsPorId() {
        btnVoltar = (Button) findViewById(R.id.Configuracao_btnVoltar);
        rgLinguagem = (RadioGroup) findViewById(R.id.Configuracao_RadioButtonGroupLinguagem);
        rbPortugues = (RadioButton) findViewById(R.id.Configuracao_RadioButtonPortugues);
        rbIngles = (RadioButton) findViewById(R.id.Configuracao_RadioButtonIngles);
        spinnerMoeda = (Spinner) findViewById(R.id.Configuracao_spinnerMoeda);
    }

    private void remontaConfiguracoesAnteriores() {
        // Define seleção do RadioButton de linguagem
        switch (linguagem) {
            case "pt":
                rbPortugues.setChecked(true);
                break;
            case "en":
                rbIngles.setChecked(true);
                break;
        }

        // Define seleção do Spinner de moeda
        String moeda = PreferenciasCompartilhadasUtil.getSharedPreferenceString(this, getString(R.string.preferencia_moeda), "BRL");
        for (int i = 0; i < spinnerMoeda.getCount(); i++) {
            if (spinnerMoeda.getItemAtPosition(i).toString().equals(moeda)) {
                spinnerMoeda.setSelection(i);
                break;
            }
        }

    }

    private void defineListenerRadioGroupLinguagem() {
        // Ao marcar um RadioButton, mudamos a linguagem
        rgLinguagem.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int id) {
                switch (id) {
                    case R.id.Configuracao_RadioButtonPortugues:
                        // Se a linguagem selecionada é diferente da atual
                        if (!linguagem.equals("pt")) {
                            mudarLingua("pt");
                            recreate();
                        }
                        break;
                    case R.id.Configuracao_RadioButtonIngles:
                        // Se a linguagem selecionada é diferente da atual
                        if (!linguagem.equals("en")) {
                            mudarLingua("en");
                            recreate();
                        }
                        break;
                }
            }
        });
    }

    private void mudarLingua(String codigoLinguagem) {
        // Define linguagem para linguagem que passamos
        LocaleHelper.setLocale(this, codigoLinguagem);
        // Salva nas preferências do aparelho
        PreferenciasCompartilhadasUtil.setSharedPreferenceString(this, getString(R.string.preferencia_linguagem), codigoLinguagem);
    }

    private void popularSpinnerMoeda() {
        ArrayAdapter<CharSequence> adaptadorMoedas = ArrayAdapter.createFromResource(this,
                R.array.Configuracao_array_moedas, android.R.layout.simple_spinner_item);
        adaptadorMoedas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerMoeda.setAdapter(adaptadorMoedas);
    }

    private void defineListenerSpinnerMoeda() {
        spinnerMoeda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Salva a moeda selecionada pelo usuário
                salvarMoeda(spinnerMoeda.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ViewUtil.criaEMostraAlert(Configuracao.this, getString(R.string.title_erro),
                        getString(R.string.erro_campovazio), getString(R.string.botaoOK), true);
            }
        });
    }

    private void salvarMoeda(String moeda) {
        PreferenciasCompartilhadasUtil.setSharedPreferenceString(this, getString(R.string.preferencia_moeda), moeda);
    }

    private void defineListenerBotoes() {
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Configuracao.super.onBackPressed();
            }
        });
    }
}
