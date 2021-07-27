package com.entrega1.formCadastro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.entrega1.dto.BaseDadosMemoria;
import com.entrega1.dto.NotaDTO;
import com.entrega1.exception.MyException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Spinner spAnos;

    private RadioGroup rgBimestres;

    private EditText editTextDisciplina;
    private EditText editTextProfessor;
    private EditText editTextAtividade;
    private EditText editTextNota;

    private CheckBox cbRascunho;

    private BaseDadosMemoria baseDadosMemoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spAnos = findViewById(R.id.spAno);

        rgBimestres  = findViewById(R.id.rgBimestres);

        editTextDisciplina  = findViewById(R.id.editDisciplina);
        editTextProfessor   = findViewById(R.id.editProfessor);
        editTextAtividade   = findViewById(R.id.editAtividade);
        editTextNota        = findViewById(R.id.editNota);

        cbRascunho    = findViewById(R.id.cbRascunho);

        popularSpinnerAnos();
    }

    /**
     * Popular Spinner Anos via Código
     * @aythor LeonardoSilva
     * @since 27/07/2014
     */
    private void popularSpinnerAnos(){
        /*
         * ****************************
         * Popular Manual
         * ****************************
         */
//        ArrayList<String> listaAnos = new ArrayList<>();
//        listaAnos.add("AEEE");
//        listaAnos.add("23");
//        listaAnos.add("345");

//        ArrayAdapter<String> adapterAnos = new ArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1,
//                listaAnos);

//        spAnos.setAdapter(adapterAnos);

        /*
         * ****************************
         * obter ArrayString
         * ****************************
         */

//        String[] arrayString = getResources().getStringArray(R.array.anosLetivos);

        getString(R.string.label1Bi);
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
     * @since 27/07/2014
     * @return NotaDTO
     * @throws MyException
     */
    private NotaDTO popularNota() throws MyException {

        NotaDTO notaDTO = new NotaDTO();

        try {

            // anoLetivo
            notaDTO.setAnoLetivo(spAnos.getSelectedItem().toString());

            // Bimestre
            notaDTO.setBimestre(obterValorBimestreSelecionado(rgBimestres));

            // Disciplina
            notaDTO.setDisciplina(editTextDisciplina.getText().toString());
            if(notaDTO.getDisciplina() == null || notaDTO.getDisciplina().isEmpty()){
                throw new MyException(getString(R.string.msgNenhum)
                        + getString(R.string.labelDisciplina) + getString(R.string.msgFoiInformada));
            }

            // Professor
            notaDTO.setProfessor(editTextProfessor.getText().toString());
            if(notaDTO.getProfessor() == null || notaDTO.getProfessor().isEmpty()){
                throw new MyException(getString(R.string.msgNenhum)
                        + getString(R.string.labelProfessor) + getString(R.string.msgFoiInformada));
            }

            // Atividade
            notaDTO.setAtividade(editTextAtividade.getText().toString());
            if(notaDTO.getAtividade() == null || notaDTO.getAtividade().isEmpty()){
                throw new MyException(getString(R.string.msgNenhum)
                        + getString(R.string.labelAtividade) + getString(R.string.msgFoiInformada));
            }

            // Nota
            notaDTO.setNota(editTextNota.getText().toString());
            if(notaDTO.getNota() == null || notaDTO.getNota().isEmpty()){
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
     * Salva o objeto NotaDTO em um Array de Notas do Objeto do tipo BaseDadosMemoria
     * @aythor LeonardoSilva
     * @since 27/07/2014
     * @param base  : BaseDadosMemoria
     * @param nota  : NotaDTO
     * @return
     */
    public static BaseDadosMemoria salvarNotasBaseDados(BaseDadosMemoria base, NotaDTO nota){

        if (base == null) {
            base = new BaseDadosMemoria();
            base.setListaNotas(new ArrayList<>());
        }

        if (base.getListaNotas() == null) {
            base.setListaNotas(new ArrayList<>());
        }

        base.getListaNotas().add(nota);

        return base;
    }

    /**
     * Obtém o valor selecionado no RadioGroup de Bimestres
     * @aythor LeonardoSilva
     * @since 27/07/2014
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
     * Ação do botão Salvar
     * @aythor LeonardoSilva
     * @since 27/07/2014
     * @param view  : View
     */
    public void actionSalvar(View view){

        String mensagem = "";
        try {
            NotaDTO notaDTO = popularNota();
            baseDadosMemoria = salvarNotasBaseDados(baseDadosMemoria, notaDTO);
            mensagem = "Nota Salva com Sucesso! \n "  + notaDTO.toString();
        } catch (MyException me) {
            System.out.println("MENSAGEM: " + me.getMessage());
            mensagem = me.getMessage();
        }

        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();

        limparTela();

    }

    /**
     * Ação do botão Limpar
     * @aythor LeonardoSilva
     * @since 27/07/2014
     * @param view  : View
     */
    public void actionLimpar(View view){
        limparTela();
    }

    /**
     * Limpa os campos da Activity
     * @aythor LeonardoSilva
     * @since 27/07/2014
     */
    private void limparTela(){
        rgBimestres.clearCheck();

        editTextDisciplina.setText(null);
        editTextProfessor.setText(null);
        editTextAtividade.setText(null);
        editTextNota.setText(null);

        cbRascunho.setChecked(false);

    }

    /**
     * Ação do botão Exibir Notas
     * @aythor LeonardoSilva
     * @since 27/07/2014
     * @param view  : View
     */
    public void actionExibirNotas(View view){
        if (baseDadosMemoria != null) {
            Toast.makeText(this, baseDadosMemoria.toString(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Sem dados Salvos!", Toast.LENGTH_LONG).show();
        }
    }

}