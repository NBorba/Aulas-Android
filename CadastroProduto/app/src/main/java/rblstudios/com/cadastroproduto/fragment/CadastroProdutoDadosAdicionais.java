package rblstudios.com.cadastroproduto.fragment;

/**
 * Criado por Renan em 12/10/17.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import rblstudios.com.cadastroproduto.interfaces.FragmentoCallback;
import rblstudios.com.cadastroproduto.R;
import rblstudios.com.cadastroproduto.util.ViewUtil;

/**
 * Criado por renan.lucas em 12/10/2017.
 */

public class CadastroProdutoDadosAdicionais extends Fragment {
    private FragmentoCallback mCallback;

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imgImagemProduto;
    private Button btnTirarFoto, btnCadastrar;

    // Fragmento de dados de basicos
    private EditText etNomeProduto, etDescricaoProduto, etPrecoCompra, etPrecoVenda;
    private Spinner spinnerMarcaProduto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cadastro_produto_dados_adicionais, container, false);

        //Acha componentes da tela por Id
        encontrarViewsPorId(rootView, container);

        // Define listener dos botões da tela
        defineListenerBotoes();

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

    private void encontrarViewsPorId(View rootView, ViewGroup container) {
        // Fragmento de dados adicionais do produto
        imgImagemProduto = (ImageView) rootView.findViewById(R.id.CadastroProdutoDadosAdicionais_ImagemProduto);
        btnTirarFoto = (Button) rootView.findViewById(R.id.CadastroProdutoDadosAdicionais_btnTirarFoto);
        btnCadastrar = (Button) rootView.findViewById(R.id.CadastroClienteVendas_btnCadastrarProduto);

        // Fragmento de dados básicos do produto
        etNomeProduto = (EditText) container.findViewById(R.id.CadastroProdutoDadosBasico_etNomeProduto);
        etDescricaoProduto = (EditText) container.findViewById(R.id.CadastroProdutoDadosBasico_etDescricaoProduto);
        etPrecoCompra = (EditText) container.findViewById(R.id.CadastroProdutoDadosBasico_etPrecoCompraProduto);
        etPrecoVenda = (EditText) container.findViewById(R.id.CadastroProdutoDadosBasico_etPrecoVendaProduto);
        spinnerMarcaProduto = (Spinner) container.findViewById(R.id.CadastroProdutoDadosBasico_spinnerMarcaProduto);
    }

    private void defineListenerBotoes() {
        btnTirarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validaCampos()) {
                    AlertDialog.Builder construtorAlerta = new AlertDialog.Builder(getContext());
                    construtorAlerta.setMessage(getString(R.string.mensagem_produtocadastradosucesso));
                    construtorAlerta.setCancelable(true);

                    construtorAlerta.setPositiveButton(
                            getString(R.string.btnOK),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    getActivity().finish();
                                }
                            });

                    AlertDialog alerta = construtorAlerta.create();
                    alerta.show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imgImagemProduto.setImageBitmap(photo);
        }
    }

    private boolean validaCampos() {
        boolean erroFragmentoUm = false;
        boolean erroFragmentoDois = false;

        // Nome produto
        if (TextUtils.isEmpty(etNomeProduto.getText().toString().trim())) {
            etNomeProduto.setError(getString(R.string.erro_campovazio));
            erroFragmentoUm = true;
        }

        // Descrição produto
        if (TextUtils.isEmpty(etDescricaoProduto.getText().toString().trim())) {
            etDescricaoProduto.setError(getString(R.string.erro_campovazio));
            erroFragmentoUm = true;
        }

        // Preço de compra
        if (TextUtils.isEmpty(etPrecoCompra.getText().toString().trim())) {
            etPrecoCompra.setError(getString(R.string.erro_campovazio));
            erroFragmentoUm = true;
        } else if (Double.valueOf(etPrecoCompra.getText().toString().trim()) <= 0) {
            etPrecoCompra.setError(getString(R.string.erro_valorzerado));
            erroFragmentoUm = true;
        }

        // Preço de venda
        if (TextUtils.isEmpty(etPrecoVenda.getText().toString().trim())) {
            etPrecoVenda.setError(getString(R.string.erro_campovazio));
            erroFragmentoUm = true;
        } else if (Double.valueOf(etPrecoVenda.getText().toString().trim()) <= 0) {
            etPrecoVenda.setError(getString(R.string.erro_valorzerado));
            erroFragmentoUm = true;
        }

        if (spinnerMarcaProduto.getSelectedItemId() == 0) {
            ViewUtil.criaEMostraAlert(this.getContext(), getString(R.string.title_erro), getString(R.string.erro_semmarca), getString(R.string.btnOK), true);
            erroFragmentoUm = true;
        }

        // Quando tem erro em algum dos fragmentos muda a posição da tela de acordo com a tela que o erro está
        if (erroFragmentoDois && erroFragmentoUm) {
            mCallback.posicaoDeTela(1);
        } else {
            mCallback.posicaoDeTela(0);
        }

        return !erroFragmentoUm && !erroFragmentoDois;
    }
}