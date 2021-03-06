package com.entrega1.formCadastro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.entrega1.dto.NotaDTO;
import com.entrega1.exception.MyException;
import com.entrega1.persitence.NotaDatabase;
import com.entrega1.util.Constantes;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Spinner spAnos;

    private RadioGroup rgBimestres;

    private EditText editTextDisciplina;
    private EditText editTextProfessor;
    private EditText editTextAtividade;
    private EditText editTextNota;

    private CheckBox cbRascunho;

    private int modo;
    private NotaDTO notaDTO;

    private Boolean preferenciaRascunho = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.labelActvCadstrarNota);

        spAnos = findViewById(R.id.spAno);

        rgBimestres  = findViewById(R.id.rgBimestres);

        editTextDisciplina  = findViewById(R.id.editDisciplina);
        editTextProfessor   = findViewById(R.id.editProfessor);
        editTextAtividade   = findViewById(R.id.editAtividade);
        editTextNota        = findViewById(R.id.editNota);

        cbRascunho    = findViewById(R.id.cbRascunho);

        popularSpinnerAnos();

        // recupera intent
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        // carregarPreferencias de Rascunho
        carregarPreferenciasRascunho();

        if (bundle != null ){
            modo = bundle.getInt(Constantes.MODO, 0);

            if (modo == Constantes.PEDIR_NOTA){
                setTitle(getString(R.string.labelActvCadstrarNota));
                if (preferenciaRascunho != null && preferenciaRascunho) {
                    cbRascunho.setChecked(true);
                }
                notaDTO = new NotaDTO();

            } else if (modo == Constantes.ALTERAR_NOTA){
                setTitle(getString(R.string.labelActvAlterarNota));
                NotaDatabase baseDadosRoom = NotaDatabase.getDatabase(this);
                Long id = bundle.getLong(Constantes.ID_NOTA);
                notaDTO = baseDadosRoom.notaDao().findById(id);
                if (notaDTO != null) {
                    popularCamposTelaNota(notaDTO);
                }
            }

        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }

    /**
     * Popular Spinner Anos via C??digo
     * @aythor LeonardoSilva
     * @since 27/07/2021
     */
    private void popularSpinnerAnos(){
        /*
         * ****************************
         * Criar Adapter direto por StringArray
         * ****************************
         */
        ArrayAdapter<CharSequence> adapterAnosChar = ArrayAdapter.createFromResource(this,
                R.array.anosLetivos, android.R.layout.simple_list_item_1);

        spAnos.setAdapter(adapterAnosChar);

    }

    /**
     * Popular Objeto NotaDTO com os Campos da Activity
     * @aythor LeonardoSilva
     * @since 27/07/2021
     * @return NotaDTO
     * @throws MyException
     */
    private NotaDTO popularNota() throws MyException {

        try {

            // anoLetivo
            notaDTO.setAnoLetivo(spAnos.getSelectedItem().toString());

            // Bimestre
            notaDTO.setBimestre(obterValorBimestreSelecionado(rgBimestres));

            // Disciplina
            notaDTO.setDisciplina(editTextDisciplina.getText().toString());
            if(notaDTO.getDisciplina() == null || notaDTO.getDisciplina().isEmpty()){
                editTextDisciplina.requestFocus();
                throw new MyException(getString(R.string.msgNenhum)
                        + getString(R.string.labelDisciplina) + getString(R.string.msgFoiInformada));
            }

            // Professor
            notaDTO.setProfessor(editTextProfessor.getText().toString());
            if(notaDTO.getProfessor() == null || notaDTO.getProfessor().isEmpty()){
                editTextProfessor.requestFocus();
                throw new MyException(getString(R.string.msgNenhum)
                        + getString(R.string.labelProfessor) + getString(R.string.msgFoiInformada));
            }

            // Atividade
            notaDTO.setAtividade(editTextAtividade.getText().toString());
            if(notaDTO.getAtividade() == null || notaDTO.getAtividade().isEmpty()){
                editTextAtividade.requestFocus();
                throw new MyException(getString(R.string.msgNenhum)
                        + getString(R.string.labelAtividade) + getString(R.string.msgFoiInformada));
            }

            // Nota
            notaDTO.setNota(editTextNota.getText().toString());
            if(notaDTO.getNota() == null || notaDTO.getNota().isEmpty()){
                editTextNota.requestFocus();
                throw new MyException(getString(R.string.msgNenhum)
                        + getString(R.string.labelNota) + getString(R.string.msgFoiInformada));
            }

            // Rascunho
            notaDTO.setRascunho(cbRascunho.isChecked());

        } catch (MyException me) {
            throw me;
        }
        return notaDTO;
    }

    /**
     * Popular Campos Tela com a NotaDTO
     * @aythor LeonardoSilva
     * @since 20/08/2021
     * @param notaDTO : NotaDTO
     */
    private void popularCamposTelaNota(NotaDTO notaDTO) {
        spAnos.setSelection(((ArrayAdapter)spAnos.getAdapter()).getPosition(notaDTO.getAnoLetivo()));
        editTextDisciplina.setText(notaDTO.getDisciplina());
        editTextProfessor.setText(notaDTO.getProfessor());
        editTextAtividade.setText(notaDTO.getAtividade());
        editTextNota.setText(notaDTO.getNota());
        cbRascunho.setChecked(notaDTO.getRascunho());

        if (notaDTO.getBimestre().equals(getString(R.string.labelBimestre))) {
            RadioButton rb1 = (RadioButton) rgBimestres.getChildAt(0);
            rb1.setChecked(true);

        } else if (notaDTO.getBimestre().equals(getString(R.string.labelDisciplina))) {
            RadioButton rb2 = (RadioButton) rgBimestres.getChildAt(1);
            rb2.setChecked(true);

        } else if (notaDTO.getBimestre().equals(getString(R.string.labelAtividade))) {
            RadioButton rb3 = (RadioButton) rgBimestres.getChildAt(2);
            rb3.setChecked(true);

        } else if (notaDTO.getBimestre().equals(getString(R.string.labelNota))) {
            RadioButton rb4 = (RadioButton) rgBimestres.getChildAt(3);
            rb4.setChecked(true);
        }

        if (notaDTO.getBimestre().equals(getString(R.string.label1Bi))) {
            RadioButton rb1 = (RadioButton) rgBimestres.getChildAt(0);
            rb1.setChecked(true);

        } else if (notaDTO.getBimestre().equals(getString(R.string.label2Bi))) {
            RadioButton rb2 = (RadioButton) rgBimestres.getChildAt(1);
            rb2.setChecked(true);

        } else if (notaDTO.getBimestre().equals(getString(R.string.label3Bi))) {
            RadioButton rb3 = (RadioButton) rgBimestres.getChildAt(2);
            rb3.setChecked(true);

        } else if (notaDTO.getBimestre().equals(getString(R.string.label4Bi))) {
            RadioButton rb4 = (RadioButton) rgBimestres.getChildAt(3);
            rb4.setChecked(true);
        }

    }

    /**
     * Obt??m o valor selecionado no RadioGroup de Bimestres
     * @aythor LeonardoSilva
     * @since 27/07/2021
     * @param rgBimestres RadioGroup
     * @return String
     * @throws MyException
     */
    public String obterValorBimestreSelecionado (RadioGroup rgBimestres) throws MyException {

        try {
            String retorno = "";
            switch (rgBimestres.getCheckedRadioButtonId()) {
                case R.id.rb1Bimestre:
                    retorno = getString(R.string.label1Bi);
                    break;
                case R.id.rb2Bimestre:
                    retorno = getString(R.string.label2Bi);
                    break;
                case R.id.rb3Bimestre:
                    retorno = getString(R.string.label3Bi);
                    break;
                case R.id.rb4Bimestre:
                    retorno = getString(R.string.label4Bi);
                    break;
                default:
                    throw new MyException(
                            getString(R.string.msgNenhum)
                            + getString(R.string.labelBimestre)
                            + getString(R.string.msgFoiSelecionado));
            }
            return retorno;
        }catch (MyException me) {
            throw me;
        }

    }

    /**
     * A????o do bot??o Salvar
     * @author LeonardoSilva
     * @since 27/07/2021
     * @param view  : View
     */
    public void actionSalvar(View view){

        String mensagem = "";
        NotaDTO notaDTO = null;
        try {
            notaDTO = popularNota();
            NotaDatabase baseDadosRoom = NotaDatabase.getDatabase(this);
            if (modo == Constantes.PEDIR_NOTA) {
                baseDadosRoom.notaDao().insert(notaDTO);
            }else if (modo == Constantes.ALTERAR_NOTA) {
                baseDadosRoom.notaDao().update(notaDTO);
            }
            setResult(Activity.RESULT_OK);
            limparTela();
            finalizarActivity(notaDTO);
        } catch (MyException me) {
            mensagem = me.getMessage();
            Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            finalizarActivity(null);
        }

    }

    /**
     * Finaliza a Activity verificando se o modo ?? Pedir Nota
     * @aythor LeonardoSilva
     * @since 16/08/2021
     * @param notaDTO
     */
    private void finalizarActivity(NotaDTO notaDTO){
        if (notaDTO != null &&
                (modo == Constantes.PEDIR_NOTA  || modo == Constantes.ALTERAR_NOTA )) {
            setResult(Activity.RESULT_OK);
        } else {
            setResult(Activity.RESULT_CANCELED);
        }
        finish();

    }

    /**
     * SObrescreve o m??todo back do SO para chamar o finalizarActivity
     */
    @Override
    public void onBackPressed() {
        cancelar();
    }

    /**
     * A????o do bot??o Limpar
     * @aythor LeonardoSilva
     * @since 27/07/2021
     * @param view  : View
     */
    public void actionLimpar(View view){
        limparTela();
        Toast.makeText(this, R.string.msgCamposLimpos, Toast.LENGTH_LONG).show();
    }

    /**
     * Limpa os campos da Activity
     * @aythor LeonardoSilva
     * @since 27/07/2021
     */
    private void limparTela(){
        rgBimestres.clearCheck();

        editTextDisciplina.setText(null);
        editTextProfessor.setText(null);
        editTextAtividade.setText(null);
        editTextNota.setText(null);

        cbRascunho.setChecked(false);

    }

    public static void actionAlterarNota(AppCompatActivity activity, NotaDTO nota){

        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra(Constantes.MODO, Constantes.ALTERAR_NOTA);
        intent.putExtra(Constantes.ID_NOTA, nota.getId());
        activity.startActivityForResult(intent,  Constantes.ALTERAR_NOTA);
    }

    private void cancelar(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nota, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnSalvar:
                actionSalvar(item.getActionView());
                return true;

            case R.id.mnLimpar:
                actionLimpar(item.getActionView());
                return true;

            case android.R.id.home:
                cancelar();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void carregarPreferenciasRascunho(){
        SharedPreferences sharedPreferences = getSharedPreferences(Constantes.ARQUIVO_PREFERENCIAS,
                Context.MODE_PRIVATE);
        preferenciaRascunho = sharedPreferences.getBoolean(Constantes.ARQUIVO_PREFERENCIAS_RASCUNHO, preferenciaRascunho);
    }
}