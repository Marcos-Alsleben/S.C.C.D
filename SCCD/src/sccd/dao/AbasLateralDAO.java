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
import sccd.model.AbasLateral;

/**
 *
 * @author gmg
 */
public class AbasLateralDAO {
    
    private Connection con;
    
    public AbasLateralDAO(){
        this.con = new ConnectionFactory().getConnection();
    }
    
    //Metodo cadastrar
    public void cadastrar(AbasLateral obj){
        try {
            //Cria comando sql
            String sql = "insert into tb_abaslateral (tipo, cadastradopor, datahora) values (?, ?, ?)";
            
            //Conecta ao banco de dados e organiza o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, obj.getTipo());
            stmt.setString(2, obj.getCadastradopor());
            stmt.setString(3, obj.getDatahora());
            
            //Executa o comando sql
            stmt.execute();
            stmt.close();
            
            JOptionPane.showMessageDialog(null, "Cadastrado com Sucesso!");
            
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
        }
    }
    
    //Metodo Alterar
    public void alterar(AbasLateral obj){
        try {
            //Cria comando sql
            String sql = "update tb_abaslateral set "
                    +"tipo=?, cadastradopor=?, datahora=? where id=?";
            
            //Conecta ao banco de dados e organiza o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, obj.getTipo());
            stmt.setString(2, obj.getCadastradopor());
            stmt.setString(3, obj.getDatahora());
            stmt.setInt(4, obj.getId());
            
            //Executa o comando sql
            stmt.execute();
            stmt.close();
            
            JOptionPane.showMessageDialog(null, "Alterado com Sucesso!");
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
        }
    }
    
    //Metodo Excluir
    public void excluir(AbasLateral obj){
        try {
            //Cria o comando sql
            String sql = "delete from tb_abaslateral where id=?";
            
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
    }
    
    //Metodo Listar
    public List<AbasLateral> listar(){
        try {
            //Cria Lista
            List<AbasLateral> lista = new ArrayList<>();
            
            //Cria comando sql
            String sql = "select * from tb_abaslateral";
            
            //Conecta ao banco de dados e organiza o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()){
                
                AbasLateral obj = new AbasLateral();
                obj.setId(rs.getInt("id"));
                obj.setTipo(rs.getString("tipo"));
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
