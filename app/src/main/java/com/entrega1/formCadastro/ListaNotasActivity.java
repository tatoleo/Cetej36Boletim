package com.entrega1.formCadastro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
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
    private ListaNotasAdapter adapterNotas;
    private BaseDadosMemoria baseDadosMemoria = new BaseDadosMemoria();
    private ActionMode actionMode;
    private View viewSelecionada;
    private int positionListaSelecionada = -1;

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_listagem_contextual, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.mnEditar:
                    alterarNota();
                    mode.finish();
                    return true;

                case R.id.mnExcluir:
                    excluirNota();
                    mode.finish();
                    return true;

                default:
                    return true;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

            if (viewSelecionada != null) {
                viewSelecionada.setBackgroundColor(Color.TRANSPARENT);
            }

            actionMode      = null;
            viewSelecionada = null;

            listViewNotas.setEnabled(true);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        setTitle(R.string.labelActvNotas);

        listViewNotas = findViewById(R.id.listViewNotas);

        listViewNotas.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listViewNotas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (actionMode != null){
                    return false;
                }
                positionListaSelecionada = position;
                view.setBackgroundColor(Color.LTGRAY);
                viewSelecionada = view;
                listViewNotas.setEnabled(false);
                actionMode = startSupportActionMode(mActionModeCallback);
                return true;
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
        } else if (requestCode == Constantes.ALTERAR_NOTA && resultCode == Activity.RESULT_OK) {
            NotaDTO notaDTO = (NotaDTO) data.getSerializableExtra(Constantes.VALOR_NOTA);
            if (notaDTO != null) {
                popularListView(notaDTO);
                Toast.makeText(this, R.string.msgDadosAlterados, Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void excluirNota(){

        baseDadosMemoria.getListaNotas().remove(positionListaSelecionada);
        adapterNotas.notifyDataSetChanged();
    }

    private void alterarNota(){
        NotaDTO nota = baseDadosMemoria.getListaNotas().get(positionListaSelecionada);
        MainActivity.actionAlterarNota(this, nota);
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

        if (adapterNotas == null) {
            adapterNotas = new ListaNotasAdapter(this, listaNotas);
        }

        if (notaDTO != null) {
            listaNotas.add(notaDTO);
            listViewNotas.deferNotifyDataSetChanged();
        } else {
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

    /**
     * AdicionarNota
     * @aythor LeonardoSilva
     * @since 16/08/2021
     * @param view
     */
    public void actionAdicionar(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constantes.MODO, Constantes.PEDIR_NOTA);
        startActivityForResult(intent, Constantes.PEDIR_NOTA);
    }

    /**
     * Infla o menu da Activity
     * @author LeonardoSilva
     * @since 20/08/2021
     * @param menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listagem, menu);
        return true;
    }

    /**
     * Controla a seleção do Menu da Activity
     * @author LeonardoSilva
     * @since 20/08/2021
     * @param item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mnAdicionar:
                actionAdicionar(item.getActionView());
                return true;

            case R.id.mnSobre:
                actionSobre(item.getActionView());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}