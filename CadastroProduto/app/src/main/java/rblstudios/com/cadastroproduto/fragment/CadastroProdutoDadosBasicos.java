package rblstudios.com.cadastroproduto.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import rblstudios.com.cadastroproduto.interfaces.FragmentoCallback;
import rblstudios.com.cadastroproduto.R;
import rblstudios.com.cadastroproduto.util.ViewUtil;

/**
 * Criado por renan.lucas em 12/10/2017.
 */

public class CadastroProdutoDadosBasicos extends Fragment {

    private FragmentoCallback mCallback;

    private Button btnCadastrarMarca;
    private FloatingActionButton btnProximo;
    private Spinner spinnerMarcaProduto;

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

        definirListenerBotoes();

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Implementa o callback
        try {
            mCallback = (FragmentoCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + getString(R.string.erro_implementacaoclasse, "ComErroDeValidacao"));
        }
    }

    private void encontrarViewsPorId(View rootView) {
        btnCadastrarMarca = (Button) rootView.findViewById(R.id.CadastroProdutoDadosBasico_btnCadastraMarca);
        btnProximo = (FloatingActionButton) rootView.findViewById(R.id.CadastroProdutoDadosBasico_btnProximo);
        spinnerMarcaProduto = (Spinner) rootView.findViewById(R.id.CadastroProdutoDadosBasico_spinnerMarcaProduto);
    }

    private void popularSpinners(View rootView) {
        ArrayAdapter<CharSequence> adaptadorMarcas = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.CadastroProdutoDadosBasicos_array_marcas, android.R.layout.simple_spinner_item);
        adaptadorMarcas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerMarcaProduto.setAdapter(adaptadorMarcas);
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
                mCallback.posicaoDeTela(1); // Vai para o fragmento 1
            }
        });
    }
}