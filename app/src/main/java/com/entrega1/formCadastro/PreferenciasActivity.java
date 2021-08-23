package com.entrega1.formCadastro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.entrega1.exception.MyException;
import com.entrega1.util.Constantes;

public class PreferenciasActivity extends AppCompatActivity {

    private RadioGroup rgOrdenacao;
    private Switch swRascunho;

    private String preferenciaOrdenacao = "Bimestre";
    private Boolean preferenciaRascunho = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        setTitle(R.string.labelActvPreferencias);

        rgOrdenacao  = findViewById(R.id.rgOrdenacao);
        swRascunho  = findViewById(R.id.swRascunho);

        carregarPreferencias();
    }

    /**
     * Obtém o valor selecionado no RadioGroup de Ordenação
     * @author LeonardoSilva
     * @since 20/08/2021
     * @param rgOrdenacao RadioGroup
     * @return String
     * @throws MyException
     */
    public String obterValorOrdenacaoSelecionado (RadioGroup rgOrdenacao) {

            String retorno = "";
            switch (rgOrdenacao.getCheckedRadioButtonId()) {
                case R.id.rbOrdBimestre:
                    retorno = getString(R.string.labelBimestre);
                    break;
                case R.id.rbOrdDisciplina:
                    retorno = getString(R.string.labelDisciplina);
                    break;
                case R.id.rbOrdAtividade:
                    retorno = getString(R.string.labelAtividade);
                    break;
                case R.id.rbOrdNota:
                    retorno = getString(R.string.labelNota);
                    break;
                default:
                    retorno = getString(R.string.labelBimestre);
            }
            return retorno;

    }

    /**
     * Carrega as preferências do usuário
     * @author LeonardoSilva
     * @since 20/08/2021
     */
    private void carregarPreferencias(){
        SharedPreferences sharedPreferences = getSharedPreferences(Constantes.ARQUIVO_PREFERENCIAS,
                Context.MODE_PRIVATE);
        preferenciaOrdenacao = sharedPreferences.getString(Constantes.ARQUIVO_PREFERENCIAS_ORDENACAO, preferenciaOrdenacao);
        preferenciaRascunho = sharedPreferences.getBoolean(Constantes.ARQUIVO_PREFERENCIAS_RASCUNHO, preferenciaRascunho);
        popularCamposTela();
    }

    private void salvarPreferencias(View view){
        SharedPreferences sharedPreferences = getSharedPreferences(Constantes.ARQUIVO_PREFERENCIAS,
                Context.MODE_PRIVATE);
       SharedPreferences.Editor editor = sharedPreferences.edit();

       editor.putBoolean(Constantes.ARQUIVO_PREFERENCIAS_RASCUNHO, swRascunho.isChecked());
       editor.putString(Constantes.ARQUIVO_PREFERENCIAS_ORDENACAO, obterValorOrdenacaoSelecionado(rgOrdenacao));

        editor.commit();

        Toast.makeText(this, R.string.msgDadosSalvos, Toast.LENGTH_LONG).show();

    }

    /**
     *
     */
    private void popularCamposTela() {
        swRascunho.setChecked(preferenciaRascunho);

        if (preferenciaOrdenacao.equals(getString(R.string.labelBimestre))) {
            RadioButton rb1 = (RadioButton) rgOrdenacao.getChildAt(0);
            rb1.setChecked(true);

        } else if (preferenciaOrdenacao.equals(getString(R.string.labelDisciplina))) {
            RadioButton rb2 = (RadioButton) rgOrdenacao.getChildAt(1);
            rb2.setChecked(true);

        } else if (preferenciaOrdenacao.equals(getString(R.string.labelAtividade))) {
            RadioButton rb3 = (RadioButton) rgOrdenacao.getChildAt(2);
            rb3.setChecked(true);

        } else if (preferenciaOrdenacao.equals(getString(R.string.labelNota))) {
            RadioButton rb4 = (RadioButton) rgOrdenacao.getChildAt(3);
            rb4.setChecked(true);
        }

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
        getMenuInflater().inflate(R.menu.menu_preferencias, menu);
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
            case R.id.mnSalvar:
                salvarPreferencias(item.getActionView());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}