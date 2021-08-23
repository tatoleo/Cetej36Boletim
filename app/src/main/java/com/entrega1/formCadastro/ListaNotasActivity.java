package com.entrega1.formCadastro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.entrega1.adapter.ListaNotasAdapter;
import com.entrega1.dto.BaseDadosMemoria;
import com.entrega1.dto.NotaDTO;
import com.entrega1.util.Constantes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Activity para listar as Notas
 * @aythor LeonardoSilva
 * @since 31/07/2021
 */
public class ListaNotasActivity extends AppCompatActivity {

    private ListView listViewNotas;
    private ListaNotasAdapter adapterNotas;
    private BaseDadosMemoria baseDadosMemoria = BaseDadosMemoria.getInstance();
    private ActionMode actionMode;
    private View viewSelecionada;
    private int positionListaSelecionada = -1;

    private String preferenciaOrdenacao = null;

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

        // listener para click longo
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

        // listener para click curto
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

        carregarPreferenciasOrdenacao();

        ordenarListaPreferencia();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constantes.PEDIR_NOTA && resultCode == Activity.RESULT_OK) {
            NotaDTO notaDTORetorno = (NotaDTO) data.getSerializableExtra(Constantes.VALOR_NOTA);
            if (notaDTORetorno != null) {
                popularListView(notaDTORetorno);
                Toast.makeText(this, R.string.msgDadosSalvos, Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == Constantes.ALTERAR_NOTA && resultCode == Activity.RESULT_OK) {
            NotaDTO notaDTORetorno = (NotaDTO) data.getSerializableExtra(Constantes.VALOR_NOTA);
            if (notaDTORetorno != null) {
                NotaDTO notaDTOSelecionada = baseDadosMemoria.getListaNotas().get(positionListaSelecionada);
                atualizarDadosNotaSelecionada(notaDTOSelecionada, notaDTORetorno);
                positionListaSelecionada = -1;
                listViewNotas.deferNotifyDataSetChanged();
                Toast.makeText(this, R.string.msgDadosAlterados, Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void atualizarDadosNotaSelecionada(NotaDTO notaDTOSelecionada, NotaDTO notaDTORetorno ){
        notaDTOSelecionada.setAnoLetivo(notaDTORetorno.getAnoLetivo());
        notaDTOSelecionada.setBimestre(notaDTORetorno.getBimestre());
        notaDTOSelecionada.setProfessor(notaDTORetorno.getProfessor());
        notaDTOSelecionada.setDisciplina(notaDTORetorno.getDisciplina());
        notaDTOSelecionada.setAtividade(notaDTORetorno.getAtividade());
        notaDTOSelecionada.setNota(notaDTORetorno.getNota());
        notaDTOSelecionada.setRascunho(notaDTORetorno.getRascunho());
    }

    /**
     * Remove da lista de notas a posição selecionada
     * @author LeonardoSilva
     * @since 20/08/2021
     */
    private void excluirNota(){
        baseDadosMemoria.getListaNotas().remove(positionListaSelecionada);
        adapterNotas.notifyDataSetChanged();
        Toast.makeText(this, R.string.msgNotaExcluida, Toast.LENGTH_LONG).show();
    }

    /**
     * Chama a MainActivity para no modo alterar Nota
     * @author LeonardoSilva
     * @since 20/08/2021
     */
    private void alterarNota(){
        NotaDTO nota = baseDadosMemoria.getListaNotas().get(positionListaSelecionada);
        MainActivity.actionAlterarNota(this, nota);
    }

    /**
     * Popular ListView com as Notas dos ArrayString
     * @author LeonardoSilva
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
     * Ordena a listagem
     * @aithor LeonardoSilva
     * @since 16/08/2021
     */
    private void ordenarListaPreferencia(){
        if (preferenciaOrdenacao != null) {


            ArrayList<NotaDTO> listaNotas = baseDadosMemoria.getListaNotas();

            if (preferenciaOrdenacao.equals(getString(R.string.labelBimestre))) {
                // ordenar por bimestre
                Collections.sort(listaNotas, new BaseDadosMemoria.ComparadorNotas(BaseDadosMemoria.ComparadorNotas.POR_BIMESTRE));

            } else if (preferenciaOrdenacao.equals(getString(R.string.labelDisciplina))) {
                // ordenar por Disciplina
                Collections.sort(listaNotas, new BaseDadosMemoria.ComparadorNotas(BaseDadosMemoria.ComparadorNotas.POR_DISCIPLINA));

            } else if (preferenciaOrdenacao.equals(getString(R.string.labelAtividade))) {
                // ordenar por Atividade
                Collections.sort(listaNotas, new BaseDadosMemoria.ComparadorNotas(BaseDadosMemoria.ComparadorNotas.POR_ATIVIDADE));

            } else if (preferenciaOrdenacao.equals(getString(R.string.labelNota))) {
                // ordenar por Nota
                Collections.sort(listaNotas, new BaseDadosMemoria.ComparadorNotas(BaseDadosMemoria.ComparadorNotas.POR_NOTA));

            }

            listViewNotas.deferNotifyDataSetChanged();

        }

    }

    /**
     * Action para a Activity Sobre
     * @aithor LeonardoSilva
     * @since 16/08/2021
     * @param view
     */
    public void actionSobre(View view){
        Intent intent = new Intent(this, SobreActivity.class);
        startActivity(intent);
    }

    /**
     * AdicionarNota
     * @author LeonardoSilva
     * @since 16/08/2021
     * @param view
     */
    public void actionAdicionar(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constantes.MODO, Constantes.PEDIR_NOTA);
        startActivityForResult(intent, Constantes.PEDIR_NOTA);
    }

    /**
     * Chama a Activity Preferencias
     * @author LeonardoSilva
     * @since 16/08/2021
     * @param view
     */
    public void actionPreferencias(View view){
        Intent intent = new Intent(this, PreferenciasActivity.class);
        startActivity(intent);
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

            case R.id.mnPreferencias:
                actionPreferencias(item.getActionView());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void carregarPreferenciasOrdenacao(){
        SharedPreferences sharedPreferences = getSharedPreferences(Constantes.ARQUIVO_PREFERENCIAS,
                Context.MODE_PRIVATE);
        preferenciaOrdenacao = sharedPreferences.getString(Constantes.ARQUIVO_PREFERENCIAS_ORDENACAO, preferenciaOrdenacao);
    }
}