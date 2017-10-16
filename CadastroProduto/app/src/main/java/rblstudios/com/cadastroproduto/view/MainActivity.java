package rblstudios.com.cadastroproduto.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import rblstudios.com.cadastroproduto.R;

public class MainActivity extends AppCompatActivity {

    private Button btnCadastrarProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        encontrarViewsPorId();
        definirListenerBotoes();
    }

    private void encontrarViewsPorId() {
        btnCadastrarProduto = (Button) findViewById(R.id.MainActivity_btnCadastrarProduto);
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
    }
}
