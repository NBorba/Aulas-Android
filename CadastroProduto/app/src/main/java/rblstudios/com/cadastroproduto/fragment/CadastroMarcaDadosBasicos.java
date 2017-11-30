package rblstudios.com.cadastroproduto.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import rblstudios.com.cadastroproduto.R;
import rblstudios.com.cadastroproduto.controller.MarcaController;
import rblstudios.com.cadastroproduto.controller.ProdutoController;
import rblstudios.com.cadastroproduto.interfaces.FragmentoCallback;
import rblstudios.com.cadastroproduto.model.Marca;
import rblstudios.com.cadastroproduto.model.Produto;
import rblstudios.com.cadastroproduto.util.NumberUtil;
import rblstudios.com.cadastroproduto.util.PreferenciasCompartilhadasUtil;
import rblstudios.com.cadastroproduto.util.Util;
import rblstudios.com.cadastroproduto.util.ViewUtil;
import rblstudios.com.cadastroproduto.view.MainActivity;
import rblstudios.com.cadastroproduto.view.RevisaoDados;

/**
 * Criado por renan.lucas em 12/10/2017.
 */

public class CadastroMarcaDadosBasicos extends Fragment {

    private String crud = "inclusao";
    private FragmentoCallback callback;

    private Button btnCadastrarMarca;
    private EditText etNomeMarca;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cadastro_marca_dados_basicos, container, false);

        //Acha componentes da tela por ID
        encontrarViewsPorId(rootView, container);

        // Verifica se algum dado veio via intent para alteração
        verificaIntentAlteracao();

        definirListenerCampos();
        definirListenerBotoes();

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Implementa o callback
        try {
            callback = (FragmentoCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + getString(R.string.erro_implementacaoclasse, "ComErroDeValidacao"));
        }
    }

    private void encontrarViewsPorId(View rootView, View container) {
        etNomeMarca = (EditText) rootView.findViewById(R.id.CadastroMarcaDadosBasico_etNomeMarca);
        btnCadastrarMarca = (Button) rootView.findViewById(R.id.CadastroMarcaDadosBasico_btnCadastrarMarca);
    }

    private void verificaIntentAlteracao() {
        if (getActivity().getIntent().hasExtra("idMarca")) {
            int idMarcaAlteracao = getActivity().getIntent().getIntExtra("idMarca", 0);
            MarcaController marcaController = new MarcaController(getContext());
            Marca marcaAlteracao = marcaController.listarPorId(idMarcaAlteracao);

            if (marcaAlteracao.getNome() != null && !marcaAlteracao.getNome().isEmpty()) {
                etNomeMarca.setText(marcaAlteracao.getNome());
            }

            crud = "alteracao";
            btnCadastrarMarca.setText(getString(R.string.botaoAlterar));
        }
    }

    private void definirListenerCampos() {
        // Se o botão "done" foi apertado, vamos para o próximo fragmento
        etNomeMarca.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int idAcao, KeyEvent keyEvent) {
                if (idAcao == EditorInfo.IME_ACTION_DONE) {
                    ViewUtil.esconderTeclado(getActivity());
                    callback.posicaoDeTela(1); // Vai para o fragmento 1

                    return true;
                }
                return false;
            }
        });
    }

    private void definirListenerBotoes() {
        btnCadastrarMarca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (crud.equals("inclusao")) {
                    cadastrarMarca(retornaObjetoMarca());
                    mostraAlerta(crud);
                } else {
                    atualizarMarca(retornaObjetoMarca());
                    mostraAlerta(crud);
                }
            }
        });
    }

    private Marca retornaObjetoMarca() {
        Marca marca = new Marca();
        if (getActivity().getIntent().hasExtra("idMarca")) {
            marca.setId(getActivity().getIntent().getIntExtra("idMarca", 0));
        }
        marca.setNome(etNomeMarca.getText().toString());
        return marca;
    }

    private void cadastrarMarca(Marca marca) {
        try {
            MarcaController marcaController = new MarcaController(getContext());
            marcaController.inserir(marca);
        } catch (Exception ex) {
            ViewUtil.criaEMostraAlert(getContext(), getString(R.string.title_erro), ex.getMessage().trim(),
                    getString(R.string.botaoOK), true);
        }
    }

    private void atualizarMarca(Marca marca) {
        try {
            MarcaController marcaController = new MarcaController(getContext());
            marcaController.atualizar(marca);
        } catch (Exception ex) {
            ViewUtil.criaEMostraAlert(getContext(), getString(R.string.title_erro), ex.getMessage().trim(),
                    getString(R.string.botaoOK), true);
        }
    }

    private void mostraAlerta(String tipoRegistro) {
        // Constroi o alerta
        AlertDialog.Builder construtorAlerta = new AlertDialog.Builder(getContext());
        if (tipoRegistro.equals("inclusao")) {
            construtorAlerta.setMessage(getString(R.string.mensagem_marcacadastradasucesso));
        } else {
            construtorAlerta.setMessage(getString(R.string.mensagem_marcaalteradasucesso));
        }
        construtorAlerta.setCancelable(true);

        construtorAlerta.setPositiveButton(
                getString(R.string.botaoOK),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Volta para a tela de cadastro
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finishAffinity();
                    }
                });

        AlertDialog alerta = construtorAlerta.create();
        alerta.show();
    }
}