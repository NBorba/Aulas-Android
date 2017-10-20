package rblstudios.com.cadastroproduto.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import rblstudios.com.cadastroproduto.interfaces.FragmentoCallback;
import rblstudios.com.cadastroproduto.R;
import rblstudios.com.cadastroproduto.util.ViewUtil;

/**
 * Criado por renan.lucas em 12/10/2017.
 */

public class CadastroProdutoDadosBasicos extends Fragment {

    private FragmentoCallback callback;

    private Button btnCadastrarMarca;
    private FloatingActionButton btnProximo;
    private Spinner spinnerMarcaProduto;
    private TextView txtMarcaSelecionada;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cadastro_produto_dados_basicos, container, false);

        //Acha componentes da tela por ID
        encontrarViewsPorId(rootView);

        // Popula spinner com base na string em Resources
        popularSpinners(rootView);

        definirListenerSpinner();
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

    private void encontrarViewsPorId(View rootView) {
        btnCadastrarMarca = (Button) rootView.findViewById(R.id.CadastroProdutoDadosBasico_btnCadastraMarca);
        btnProximo = (FloatingActionButton) rootView.findViewById(R.id.CadastroProdutoDadosBasico_btnProximo);
        spinnerMarcaProduto = (Spinner) rootView.findViewById(R.id.CadastroProdutoDadosBasico_spinnerMarcaProduto);
        txtMarcaSelecionada = (TextView) rootView.findViewById(R.id.CadastroProdutoDadosBasico_txtMarcaSelecionada);
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