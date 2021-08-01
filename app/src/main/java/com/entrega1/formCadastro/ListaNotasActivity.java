package com.entrega1.formCadastro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.entrega1.adapter.ListaNotasAdapter;
import com.entrega1.dto.NotaDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity para listar as Notas
 * @aythor LeonardoSilva
 * @since 31/07/2021
 */
public class ListaNotasActivity extends AppCompatActivity {

    private ListView listViewNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

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

        popularListView();
    }

    /**
     * Popular ListView com as Notas dos ArrayString
     * @aythor LeonardoSilva
     * @since 31/07/2021
     */
    private void popularListView(){

        ArrayList<NotaDTO> listaNotas = listarNotas();

        ListaNotasAdapter adapterNotas = new ListaNotasAdapter(this, listaNotas);
        listViewNotas.setAdapter(adapterNotas);

//        ArrayAdapter<NotaDTO> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1, listaNotas);
//
//        listViewNotas.setAdapter(adapter);

    }

    /**
     * Gerar um ArrayList com os ArrayString
     * @aythor LeonardoSilva
     * @since 31/07/2021
     * @return ArrayList<NotaDTO>
     */
    private ArrayList<NotaDTO> listarNotas(){

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
}