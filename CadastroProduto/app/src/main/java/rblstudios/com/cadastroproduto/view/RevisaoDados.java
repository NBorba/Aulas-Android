package rblstudios.com.cadastroproduto.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import rblstudios.com.cadastroproduto.R;

public class RevisaoDados extends AppCompatActivity {

    private TextView txtNomeProduto;
    private TextView txtDescricaoProduto;
    private TextView txtMarcaProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisao_dados);

        encontraViewsPorId();
        populaDados();
    }

    private void encontraViewsPorId() {
        txtNomeProduto = (TextView) findViewById(R.id.RevisaoDados_txtNome);
        txtDescricaoProduto = (TextView) findViewById(R.id.RevisaoDados_txtDescricao);
        txtMarcaProduto = (TextView) findViewById(R.id.RevisaoDados_txtMarca);
    }

    private void populaDados() {
        if (getIntent().hasExtra("nomeProduto") && getIntent().hasExtra("descricaoProduto") && getIntent().hasExtra("marcaProduto")) {
            txtNomeProduto.setText(getIntent().getStringExtra("nomeProduto").toUpperCase());
            txtDescricaoProduto.setText(getIntent().getStringExtra("descricaoProduto").toUpperCase());
            txtMarcaProduto.setText(getIntent().getStringExtra("marcaProduto").toUpperCase());
        }
    }
}
