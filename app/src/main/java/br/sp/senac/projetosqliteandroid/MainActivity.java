package br.sp.senac.projetosqliteandroid;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String NOME_BANCO_DE_DADOS = "bdEmpregados";

    TextView lblEmpregados;
    EditText txtNomeEmpregado, txtSalarioEmpregado;
    Spinner spnDepartamentos;

    SQLiteDatabase meuBancoDeDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblEmpregados = (TextView) findViewById(R.id.lblVisualizaFuncionario);
        txtNomeEmpregado = (EditText) findViewById(R.id.txtNomeNovoFuncionario);
        txtSalarioEmpregado = (EditText) findViewById(R.id.txtNovoSalarioFuncionario);
        spnDepartamentos = (Spinner) findViewById(R.id.spnDepartamentos);

        findViewById(R.id.btnAdicionarfuncionario).setOnClickListener(this);
        lblEmpregados.setOnClickListener(this);

        //Criando banco de dados

        meuBancoDeDados = openOrCreateDatabase(NOME_BANCO_DE_DADOS, MODE_PRIVATE, null);

        criarTabelaEmpregado();
    }

    //Este método irá validar o nome e o salário
    //departamento não precisa de validação, pois é um spinner e não pode estar vazio

    private boolean verificarEntrada(String nome, String salario) {
        if (nome.isEmpty()) {
            txtNomeEmpregado.setError("Por favor entre com o nome");
            txtNomeEmpregado.requestFocus();
            return false;
        }

        if (salario.isEmpty() || Integer.parseInt(salario) <= 0) {
            txtSalarioEmpregado.setError("Por favor entre com o salário");
            txtSalarioEmpregado.requestFocus();
            return false;
        }
        return true;
    }

    //Neste método vamos fazer a operação para adicionar os funcionario
    private void adicionarEmpregado() {

        String nomeEmpr = txtNomeEmpregado.getText().toString().trim();
        String salarioEmpr = txtSalarioEmpregado.getText().toString().trim();
        String deptoEmpr = spnDepartamentos.getSelectedItem().toString();

        // obtendo o horário atual para data de inclusão

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String dataEntrada = simpleDateFormat.format(calendar.getTime());

        //validando entrada
        if (verificarEntrada(nomeEmpr, salarioEmpr)) {

            String insertSQL = "INSERT INTO funcionarios (" +
                    "nome, " +
                    "departamento, " +
                    "dataEntrada," +
                    "salario)" +
                    "VALUES(?, ?, ?, ?);";

            // usando o mesmo método execsql para inserir valores
            // desta vez tem dois parâmetros
            // primeiro é a string sql e segundo são os parâmetros que devem ser vinculados à consulta

            meuBancoDeDados.execSQL(insertSQL, new String[]{nomeEmpr, deptoEmpr, dataEntrada, salarioEmpr});

            Toast.makeText(getApplicationContext(), "Funcionário adicionado com sucesso!!!", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdicionarfuncionario:

                adicionarEmpregado();
                break;
            case R.id.lblVisualizaFuncionario:
                startActivity(new Intent(getApplicationContext(), Funcionarios_Activity.class));
        }

    }

    // este método irá criar a tabela
    // como vamos chamar esse método toda vez que lançarmos o aplicativo
    // Eu adicionei IF NOT EXISTS ao SQL
    // então, só criará a tabela quando a tabela ainda não estiver criada

    private void criarTabelaEmpregado() {
        meuBancoDeDados.execSQL(
                "CREATE TABLE IF NOT EXISTS funcionarios (" +
                        "id integer PRIMARY KEY AUTOINCREMENT," +
                        "nome varchar(200) NOT NULL," +
                        "departamento varchar(200) NOT NULL," +
                        "dataEntrada datetime NOT NULL," +
                        "salario double NOT NULL );"
        );
    }

}
