package com.entrega1.formCadastro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.entrega1.adapter.ListaNotasAdapter;
import com.entrega1.dto.BaseDadosMemoria;
import com.entrega1.dto.NotaDTO;
import com.entrega1.util.Constantes;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity para listar as Notas
 * @aythor LeonardoSilva
 * @since 31/07/2021
 */
public class ListaNotasActivity extends AppCompatActivity {

    private ListView listViewNotas;

    private BaseDadosMemoria baseDadosMemoria = new BaseDadosMemoria();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        setTitle(R.string.labelActvNotas);

        listViewNotas = findViewById(R.id.listViewNotas);

        listViewNotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NotaDTO notaDTO = (NotaDTO) listViewNotas.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        notaDTO.toString(),
                        Toast.LENGTH_LONG).show();
            }
        });

        popularListView(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constantes.PEDIR_NOTA && resultCode == Activity.RESULT_OK) {
            NotaDTO notaDTO = (NotaDTO) data.getSerializableExtra(Constantes.VALOR_NOTA);
            if (notaDTO != null) {
                popularListView(notaDTO);
                Toast.makeText(this, R.string.msgDadosSalvos, Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * Popular ListView com as Notas dos ArrayString
     * @aythor LeonardoSilva
     * @since 31/07/2021
     */
    private void popularListView(NotaDTO notaDTO){

        ArrayList<NotaDTO> listaNotas = baseDadosMemoria.getListaNotas();

        if (listaNotas == null) {
            listaNotas = new ArrayList<>();
        }
        if (notaDTO != null) {
            listaNotas.add(notaDTO);
            listViewNotas.deferNotifyDataSetChanged();
        } else {
//        ArrayList<NotaDTO> listaNotas = listarNotasArrayString();
            ListaNotasAdapter adapterNotas = new ListaNotasAdapter(this, listaNotas);
            listViewNotas.setAdapter(adapterNotas);
        }


    }

    /**
     * Gerar um ArrayList com os ArrayString
     * @aythor LeonardoSilva
     * @since 31/07/2021
     * @return ArrayList<NotaDTO>
     */
    private ArrayList<NotaDTO> listarNotasArrayString(){

        ArrayList<NotaDTO> listaNotas = new ArrayList<>();

        String[] listaAnoLetivo = getResources().getStringArray(R.array.listaAnoLetivo);
        String[] listaAtividade = getResources().getStringArray(R.array.listaAtividade);
        String[] listaBimestre = getResources().getStringArray(R.array.listaBimestre);
        String[] listaDisciplina = getResources().getStringArray(R.array.listaDisciplina);
        String[] listaProfessor = getResources().getStringArray(R.array.listaProfessor);

        Boolean rascunho = false;
        for(int i = 0; i < listaAnoLetivo.length; i++){
            NotaDTO notaDTO = new NotaDTO();
            notaDTO.setAnoLetivo(listaAnoLetivo[i]);
            notaDTO.setBimestre(listaBimestre[i]);
            notaDTO.setDisciplina(listaDisciplina[i]);
            notaDTO.setProfessor(listaProfessor[i]);
            notaDTO.setAtividade(listaAtividade[i]);

            // gera nota aleatória
            Double nota = ((Math.random() * (10.0 - 0.0)) + 0.0);
            // Arredondamento
            nota = Math.round(nota * 100.0)/100.0;
            notaDTO.setNota(nota.toString());

            rascunho = !rascunho;
            notaDTO.setRascunho(rascunho);

            listaNotas.add(notaDTO);
        }
        return listaNotas;
    }


    /**
     * Action para a Activity Sobre
     * @aythor LeonardoSilva
     * @since 16/08/2021
     * @param view
     */
    public void actionSobre(View view){
        Intent intent = new Intent(this, SobreActivity.class);
        startActivity(intent);
    }

    public void actionAdicionar(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constantes.MODO, Constantes.PEDIR_NOTA);
        startActivityForResult(intent, Constantes.PEDIR_NOTA);
    }
}