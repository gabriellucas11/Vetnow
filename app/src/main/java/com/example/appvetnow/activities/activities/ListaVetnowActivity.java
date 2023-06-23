package com.example.appvetnow.activities.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import com.example.appvetnow.R;
import com.example.appvetnow.activities.api.VetnowService;
import com.example.appvetnow.activities.api.RestServiceGenerator;
import com.example.appvetnow.activities.entidades.Vetnow;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ListaVetnowActivity extends AppCompatActivity {

    private VetnowService service = null;
    final private ListaVetnowActivity mainActivity = this;
    private final Context context;

    public ListaVetnowActivity() {
        context = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Lista de Cursos");
        setContentView(R.layout.activity_formulario_vetnow);
        service = RestServiceGenerator.createService(VetnowService.class);
        buscaVetnows();
        criaAcaoBotaoFlutuante();
        criaAcaoCliqueLongo();
    }

    private void criaAcaoCliqueLongo() {
        ListView listView = findViewById(R.id.listViewListaVetnow);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ListaVetnowActivity","Clicou em clique longo na posicao "+position);
                final Vetnow objetoSelecionado = (Vetnow) parent.getAdapter().getItem(position);
                Log.i("ListaVetnowActivity", "Selecionou a Vetnow "+objetoSelecionado.getCodigo());
                new AlertDialog.Builder(parent.getContext()).setTitle("Removendo Vetnow")
                        .setMessage("Tem certeza que quer remover a Vetnow "+objetoSelecionado.getCodigo()+"?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                removeVetnow(objetoSelecionado);
                            }
                        }).setNegativeButton("Não", null).show();
                return true;
            }
        });
    }

    private void removeVetnow(Vetnow vetNow) {
        Log.i("ListVetnowActivity","Vai remover Vetnow "+ vetNow.getCodigo());
        Call<Boolean> call = this.service.excluiVetnow(vetNow.getCodigo());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    Log.i("ListaVetnowActivity", "Removeu a Vetnow" + vetNow.getCodigo());
                    Toast.makeText(getApplicationContext(), "Removeu a Vetnow " + vetNow.getCodigo(), Toast.LENGTH_LONG).show();
                    onResume();
                } else {
                    Log.e("ListaVetnowActivity", "Erro (" + response.code()+"): Verifique novamente os valores");
                    Toast.makeText(getApplicationContext(), "Erro (" + response.code()+"): Verifique novamente os valores", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("ListaVetnowActivity", "Erro: " + t.getMessage());
            }
        });
    }

    private void criaAcaoBotaoFlutuante() {
        FloatingActionButton botaoNovo = findViewById(R.id.floatingActionButton4);
        botaoNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity","Clicou no botão para adicionar Nova Vetnow");
                startActivity(new Intent(ListaVetnowActivity.this,
                        FormularioVetnowActivity.class));
            }
        });
    }

    public void buscaVetnows(){
        VetnowService service = RestServiceGenerator.createService(VetnowService.class);
        Call<List<Vetnow>> call = service.getVetnows();
        call.enqueue(new Callback<List<Vetnow>>() {
            @Override
            public void onResponse(Call<List<Vetnow>> call, Response<List<Vetnow>> response) {
                if (response.isSuccessful()) {
                    Log.i("ListaVetnowActivity", "Retornou " + response.body().size() + " Cursos!");
                    ListView listView = findViewById(R.id.listViewListaVetnow);
                    listView.setAdapter(new ListaVetnowAdapter(context,response.body()));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.i("ListaVetnowActivity", "Selecionou o objeto de posicao "+position);
                            Vetnow objetoSelecionado = (Vetnow) parent.getAdapter().getItem(position);
                            Log.i("ListaVetnowActivity", "Selecionou a Vetnow "+objetoSelecionado.getCodigo());
                            Intent intent = new Intent(ListaVetnowActivity.this, FormularioVetnowActivity.class);
                            intent.putExtra("objeto", objetoSelecionado);
                            startActivity(intent);
                        }
                    });
                } else {
                    Log.e("VetnowDAO", "" + response.message());
                    Toast.makeText(getApplicationContext(), "Erro: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Vetnow>> call, Throwable t) {
                Log.e("Error", "" + t.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        buscaVetnows();
    }
}