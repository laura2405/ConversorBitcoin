package com.example.conversorbitcoin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Cotação fixa permitida pela atividade:
    // 1 Bitcoin = R$ 600.000,00
    private static final double COTACAO_BTC = 600000.00;

    private EditText edtValor;
    private TextView txtResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtValor = findViewById(R.id.edtValor);
        txtResultado = findViewById(R.id.txtResultado);

        Button btnRealBitcoin = findViewById(R.id.btnRealBitcoin);
        Button btnBitcoinReal = findViewById(R.id.btnBitcoinReal);

        btnRealBitcoin.setOnClickListener(v -> converterRealParaBitcoin());
        btnBitcoinReal.setOnClickListener(v -> converterBitcoinParaReal());
    }

    private Double lerValor() {
        String texto = edtValor.getText().toString().trim().replace(",", ".");

        if (texto.isEmpty()) {
            Toast.makeText(this, "Digite um valor.", Toast.LENGTH_SHORT).show();
            return null;
        }

        try {
            double valor = Double.parseDouble(texto);

            if (valor < 0) {
                Toast.makeText(this, "Digite um valor positivo.", Toast.LENGTH_SHORT).show();
                return null;
            }

            return valor;
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Digite um número válido.", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private void converterRealParaBitcoin() {
        Double valor = lerValor();
        if (valor == null) return;

        double bitcoin = valor / COTACAO_BTC;

        txtResultado.setText(String.format(
                Locale.US,
                "R$ %.2f = %.8f BTC",
                valor,
                bitcoin
        ));
    }

    private void converterBitcoinParaReal() {
        Double valor = lerValor();
        if (valor == null) return;

        double reais = valor * COTACAO_BTC;

        NumberFormat moeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        txtResultado.setText(String.format(
                Locale.US,
                "%.8f BTC = %s",
                valor,
                moeda.format(reais)
        ));
    }
}
