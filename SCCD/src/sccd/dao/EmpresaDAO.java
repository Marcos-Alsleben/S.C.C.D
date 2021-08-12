/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sccd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import sccd.jdbc.ConnectionFactory;
import sccd.model.Empresa;

/**
 *
 * @author mrs_a
 */
public class EmpresaDAO {
    
    private Connection con;
    
    public EmpresaDAO(){
        this.con = new ConnectionFactory().getConnection();
    }
    
    //Metodo cadastrar
    /*public void cadastrarEmpresa(Empresa obj){
        try {
            //Cria comando sql
            String sql = "insert into tb_empresa (nome, endereco, telefone, observacao)"
                    + "values (?, ?, ?, ?)";
            
            //Conecta ao banco de dados e organiza o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getEndereco());
            stmt.setString(2, obj.getTelefone());
            stmt.setString(4, obj.getObservacao());
            
            //Executa o comando sql
            stmt.execute();
            stmt.close();
            
            JOptionPane.showMessageDialog(null, "Cadastrado com Sucesso!");
            
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
        }
    }*/
    
    //Metodo Alterar
    public void alterar(Empresa obj){
        try {
            //Cria comando sql
            String sql = "update tb_empresa set nome=?, telefone=?, endereco=?, observacao=?, cadastradopor=?, datahora=? where id=?";
            
            //Conecta ao banco de dados e organiza o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getTelefone());
            stmt.setString(3, obj.getEndereco());
            stmt.setString(4, obj.getObservacao());
            stmt.setString(5, obj.getCadastradopor());
            stmt.setString(6, obj.getDatahora());
            stmt.setInt(7, obj.getId());
            
            //Executa o comando sql
            stmt.execute();
            stmt.close();
            
            JOptionPane.showMessageDialog(null, "Alterado com Sucesso!");
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
        }
    }
    
    //Metodo Excluir
    /*public void excluirEmpresa(Empresa obj){
        try {
            //Cria o comando sql
            String sql = "delete from tb_empresa where id=?";
            
            //Conecta ao banco de dados e organiza o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, obj.getId());
            
            //Executa o comando sql
            stmt.execute();
            stmt.close();
            
            JOptionPane.showMessageDialog(null, "Excluido com Sucesso!");
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
        }
    }*/
    
    //Metodo Listar
    public List<Empresa> listar(){
        try {
            //Cria Lista
            List<Empresa> lista = new ArrayList<>();
            
            //Cria comando sql
            String sql = "select * from tb_empresa";
            
            //Conecta ao banco de dados e organiza o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()){
                
                Empresa obj = new Empresa();
                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setTelefone(rs.getString("telefone"));
                obj.setEndereco(rs.getString("endereco"));
                obj.setObservacao(rs.getString("observacao"));
                obj.setCadastradopor(rs.getString("cadastradopor"));
                obj.setDatahora(rs.getString("datahora"));
                
                //Executa
                lista.add(obj);
            }
            return lista;
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro:" + erro);
            return null;
        }
    }
    
}
