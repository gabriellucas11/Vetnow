package com.example.appvetnow.activities.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.Date;


import com.example.appvetnow.R;

import com.example.appvetnow.activities.api.VetnowService;
import com.example.appvetnow.activities.api.RestServiceGenerator;
import com.example.appvetnow.activities.entidades.Vetnow;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FormularioVetnowActivity  extends AppCompatActivity {
    private VetnowService service = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_vetnow);
        setTitle("Edição de Vetnow");
        service = RestServiceGenerator.createService(VetnowService.class);
        configuraBotaoSalvar();
        inicializaObjeto();
    }

    private void inicializaObjeto() {
        Intent intent = getIntent();
        if (intent.getSerializableExtra("objeto") != null) {
            Vetnow objeto = (Vetnow) intent.getSerializableExtra("objeto");
            EditText codigo = findViewById(R.id.editTextText);
            EditText nome = findViewById(R.id.editTextText);
            EditText descricao = findViewById(R.id.editTextText);
            codigo.setText(objeto.getCodigo());
            nome.setText(objeto.getNome());
            descricao.setText(objeto.getDescricao());
            codigo.setEnabled(false);
            Button botaoSalvar = findViewById(R.id.button);
            botaoSalvar.setText("Atualizar");
        }
    }

    private void configuraBotaoSalvar() {
        Button botaoSalvar = findViewById(R.id.button);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Formulariovetnow","Clicou em Salvar");
                Vetnow vetNow = recuperaInformacoesFormulario();
                Intent intent = getIntent();
                if (intent.getSerializableExtra("objeto") != null) {
                    Vetnow objeto = (Vetnow) intent.getSerializableExtra("objeto");
                    vetNow.setCodigo(objeto.getCodigo());
                    vetNow.setDataCriacao(objeto.getDataCriacao());
                    if (validaFormulario(vetNow)) {
                        atualizaVetnow(vetNow);
                    }
                } else {
                    vetNow.setDataCriacao(new Date());
                    if (validaFormulario(vetNow)) {
                        salvaVetnow(vetNow);
                    }
                }
            }
        });
    }

    private boolean validaFormulario(Vetnow vetNow){
        boolean valido = true;
        EditText codigo = findViewById(R.id.editTextText);
        EditText nome = findViewById(R.id.editTextText);
        EditText descricao = findViewById(R.id.editTextText);
        if (vetNow.getCodigo() == null || vetNow.getCodigo().trim().length() == 0){
            codigo.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            valido = false;
        } else {
            codigo.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_blue_dark), PorterDuff.Mode.SRC_ATOP);
        }
        if (vetNow.getNome() == null || vetNow.getNome().trim().length() == 0){
            nome.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            valido = false;
        } else {
            nome.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_blue_dark), PorterDuff.Mode.SRC_ATOP);
        }
        if (vetNow.getDescricao() == null || vetNow.getDescricao().trim().length() == 0){
            descricao.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            valido = false;
        } else {
            descricao.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_blue_dark), PorterDuff.Mode.SRC_ATOP);
        }
        if (!valido){
            Log.e("FormularioVetnow", "Favor verificar os campos destacados");
            Toast.makeText(getApplicationContext(), "Favor verificar os campos destacados", Toast.LENGTH_LONG).show();
        }
        return valido;
    }

    private void salvaVetnow(Vetnow vetNow) {
        Call<Vetnow> call = service.criavetnow(vetNow);
        call.enqueue(new Callback<Vetnow>() {
            @Override
            public void onResponse(Call<Vetnow> call, Response<Vetnow> response) {
                if (response.isSuccessful()) {
                    Log.i("FormularioVetnow", "Salvou a Vetnow "+ vetNow.getCodigo());
                    Toast.makeText(getApplicationContext(), "Salvou a Vetnow "+ vetNow.getCodigo(), Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Log.e("FormularioVetnow", "Erro (" + response.code()+"): Verifique novamente os valores");
                    Toast.makeText(getApplicationContext(), "Erro (" + response.code()+"): Verifique novamente os valores", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Vetnow> call, Throwable t) {
                Log.e("FormularioVetnow", "Erro: " + t.getMessage());
            }
        });
    }

    private void atualizaVetnow(Vetnow vetNow) {
        Log.i("FormulariomVetnow","Vai atualizar Vetnow "+ vetNow.getCodigo());
        Call<Vetnow> call = service.atualizaVetnow(vetNow.getCodigo(), vetNow);
        call.enqueue(new Callback<Vetnow>() {
            @Override
            public void onResponse(Call<Vetnow> call, Response<Vetnow> response) {
                if (response.isSuccessful()) {
                    Log.i("FormularioVetnow", "Atualizou a Vetnow " + vetNow.getCodigo());
                    Toast.makeText(getApplicationContext(), "Atualizou a Vetnow " + vetNow.getCodigo(), Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Log.e("FormularioVetnow", "Erro (" + response.code()+"): Verifique novamente os valores");
                    Toast.makeText(getApplicationContext(), "Erro (" + response.code()+"): Verifique novamente os valores", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Vetnow> call, Throwable t) {
                Log.e("FormularioVetnow", "Erro: " + t.getMessage());
            }
        });
    }

    @NotNull
    private Vetnow recuperaInformacoesFormulario() {
        EditText codigo = findViewById(R.id.editTextText);
        EditText nome = findViewById(R.id.editTextText);
        EditText descricao = findViewById(R.id.editTextText);
        Vetnow vetNow = new Vetnow();
        vetNow.setCodigo(codigo.getText().toString());
        vetNow.setNome(nome.getText().toString());
        vetNow.setDescricao(descricao.getText().toString());
        vetNow.setDataCriacao(new Date());
        return vetNow;
    }
}
