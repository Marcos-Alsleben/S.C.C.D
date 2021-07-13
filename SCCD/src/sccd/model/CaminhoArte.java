/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sccd.model;

/**
 *
 * @author gmg
 */
public class CaminhoArte {
    // Atributos    
    private int id;
    private String endereco;
    private String cadastradopor;
    private String datahora;
    
    //Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCadastradopor() {
        return cadastradopor;
    }

    public void setCadastradopor(String cadastradopor) {
        this.cadastradopor = cadastradopor;
    }

    public String getDatahora() {
        return datahora;
    }

    public void setDatahora(String datahora) {
        this.datahora = datahora;
    }
    
}
