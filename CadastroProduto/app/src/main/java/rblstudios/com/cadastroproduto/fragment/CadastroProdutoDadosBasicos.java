package rblstudios.com.cadastroproduto.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import rblstudios.com.cadastroproduto.interfaces.FragmentoCallback;
import rblstudios.com.cadastroproduto.model.Produto;
import rblstudios.com.cadastroproduto.util.PreferenciasCompartilhadasUtil;
import rblstudios.com.cadastroproduto.util.Util;
import rblstudios.com.cadastroproduto.util.ViewUtil;
import rblstudios.com.cadastroproduto.view.CadastroProduto;

/**
 * Criado por renan.lucas em 12/10/2017.
 */

public class CadastroProdutoDadosBasicos extends Fragment {

    private FragmentoCallback callback;

    private Button btnCadastrarMarca;
    private EditText etNomeProduto, etDescricaoProduto, etPrecoCompra, etPrecoVenda;
    private FloatingActionButton btnProximo;
    private ImageView imgImagemProduto;
    private Spinner spinnerMarcaProduto;
    private Switch switchProdutoAtivo;
    private TextView txtMarcaSelecionada, txtPrecoCompra, txtPrecoVenda;


    private String moeda;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cadastro_produto_dados_basicos, container, false);

        //Acha componentes da tela por ID
        encontrarViewsPorId(rootView, container);

        // Busca a moeda definida pelo usuário nas configurações
        defineMoeda();

        //Define os textos dos campos utizando a moeda encontrada da configuração
        defineTextosCamposPreco();

        // Popula spinner com base na string em Resources
        popularSpinners(rootView);

        // Verifica se algum dado veio via intent para alteração
        verificaIntentAlteracao();

        // Listeners
        definirListenerSpinner();
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
        btnCadastrarMarca = (Button) rootView.findViewById(R.id.CadastroProdutoDadosBasico_btnCadastraMarca);
        btnProximo = (FloatingActionButton) rootView.findViewById(R.id.CadastroProdutoDadosBasico_btnProximo);
        spinnerMarcaProduto = (Spinner) rootView.findViewById(R.id.CadastroProdutoDadosBasico_spinnerMarcaProduto);
        txtMarcaSelecionada = (TextView) rootView.findViewById(R.id.CadastroProdutoDadosBasico_txtMarcaSelecionada);
        txtPrecoCompra = (TextView) rootView.findViewById(R.id.CadastroProdutoDadosBasico_txtPrecoCompraProduto);
        txtPrecoVenda = (TextView) rootView.findViewById(R.id.CadastroProdutoDadosBasico_txtPrecoVendaProduto);
        etPrecoCompra = (EditText) rootView.findViewById(R.id.CadastroProdutoDadosBasico_etPrecoCompraProduto);
        etPrecoVenda = (EditText) rootView.findViewById(R.id.CadastroProdutoDadosBasico_etPrecoVendaProduto);
        etNomeProduto = (EditText) rootView.findViewById(R.id.CadastroProdutoDadosBasico_etNomeProduto);
        etDescricaoProduto = (EditText) rootView.findViewById(R.id.CadastroProdutoDadosBasico_etDescricaoProduto);

        // Fragmento de dados adicionais do produto
        imgImagemProduto = (ImageView) container.findViewById(R.id.CadastroProdutoDadosAdicionais_ImagemProduto);
        switchProdutoAtivo = (Switch) container.findViewById(R.id.CadastroProdutoDadosAdicionais_switchProdutoAtivo);
    }

    private void defineMoeda() {
        moeda = Util.retornaMoeda(PreferenciasCompartilhadasUtil.getSharedPreferenceString(getContext(), getString(R.string.preferencia_moeda), "BRL"));
    }

    private void defineTextosCamposPreco() {
        txtPrecoCompra.setText(getString(R.string.CadastroProdutoDadosBasicos_title_PrecoCompra, moeda));
        txtPrecoVenda.setText(getString(R.string.CadastroProdutoDadosBasicos_title_PrecoVenda, moeda));
        etPrecoCompra.setHint(getString(R.string.CadastroProdutoDadosBasicos_hint_PrecoCompra, moeda));
        etPrecoVenda.setHint(getString(R.string.CadastroProdutoDadosBasicos_hint_PrecoVenda, moeda));
    }

    private void verificaIntentAlteracao() {
        if (getActivity().getIntent().hasExtra("produtoAlteracao")) {
            Produto produtoAlteracao = getActivity().getIntent().getParcelableExtra("produtoAlteracao");

            if (produtoAlteracao.getNome() != null && !produtoAlteracao.getNome().isEmpty()) {
                etNomeProduto.setText(produtoAlteracao.getNome());
            }

            if (produtoAlteracao.getDescricao() != null && !produtoAlteracao.getDescricao().isEmpty()) {
                etDescricaoProduto.setText(produtoAlteracao.getDescricao());
            }

            if (produtoAlteracao.getMarca() != null && !produtoAlteracao.getMarca().isEmpty()) {
                for (int i = 0; i < spinnerMarcaProduto.getCount(); i++) {
                    if (spinnerMarcaProduto.getItemAtPosition(i).toString().toLowerCase().equals(produtoAlteracao.getMarca().toLowerCase())) {
                        spinnerMarcaProduto.setSelection(i);
                        break;
                    }
                }
            }

            if (!String.valueOf(produtoAlteracao.getPrecoCompra()).isEmpty()) {
                etPrecoCompra.setText(String.valueOf(produtoAlteracao.getPrecoCompra()));
            }

            if (!String.valueOf(produtoAlteracao.getPrecoVenda()).isEmpty()) {
                etPrecoVenda.setText(String.valueOf(produtoAlteracao.getPrecoVenda()));
            }
        }
    }

    private void popularSpinners(View rootView) {
        ArrayAdapter<CharSequence> adaptadorMarcas = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.CadastroProdutoDadosBasicos_array_marcas, android.R.layout.simple_spinner_item);
        adaptadorMarcas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerMarcaProduto.setAdapter(adaptadorMarcas);
    }

    private void definirListenerSpinner() {
        spinnerMarcaProduto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Se o item clicado foi zero, temos que mostrar que nenhuma marca foi selecionada
                if (position == 0) {
                    txtMarcaSelecionada.setText(getString(R.string.CadastroProdutoDadosBasico_title_MarcaSelecionada));
                } else {
                    txtMarcaSelecionada.setText(getString(R.string.CadastroProdutoDadosBasico_title_MarcaSelecao,
                            spinnerMarcaProduto.getSelectedItem().toString()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                txtMarcaSelecionada.setText(getString(R.string.CadastroProdutoDadosBasico_title_MarcaSelecionada));
            }
        });
    }

    private void definirListenerCampos() {
        // Se o botão "done" foi apertado, vamos para o próximo fragmento
        etPrecoVenda.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
                Toast.makeText(getContext(), getString(R.string.erro_semimplementacao), Toast.LENGTH_LONG).show();
            }
        });

        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtil.esconderTeclado(getActivity());
                callback.posicaoDeTela(1); // Vai para o fragmento 1
            }
        });
    }
}