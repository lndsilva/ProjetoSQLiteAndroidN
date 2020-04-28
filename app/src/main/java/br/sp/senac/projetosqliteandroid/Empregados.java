package br.sp.senac.projetosqliteandroid;

public class Empregados {
    int id;
    String nome, depto, dataEntrada;
    double salario;

    public Empregados() {
    }

    public Empregados(int id, String nome, String depto, String dataEntrada, double salario) {
        this.id = id;
        this.nome = nome;
        this.depto = depto;
        this.dataEntrada = dataEntrada;
        this.salario = salario;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDepto() {
        return depto;
    }

    public String getDataEntrada() {
        return dataEntrada;
    }

    public double getSalario() {
        return salario;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDepto(String depto) {
        this.depto = depto;
    }

    public void setDataEntrada(String dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
}
