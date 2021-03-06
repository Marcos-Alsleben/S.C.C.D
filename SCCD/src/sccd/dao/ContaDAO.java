/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sccd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import sccd.jdbc.ConnectionFactory;
import sccd.model.Conta;

/**
 *
 * @author mrs_a
 */
public class ContaDAO {

    private Connection con;

    public ContaDAO() {
        this.con = new ConnectionFactory().getConnection();
    }

    //Metodo cadastrar
    public void cadastrar(Conta obj) {
        try {
            //Cria comando sql
            String sql = "insert into tb_conta (nome, senha, privilegio)"
                    + "values (?, ?, ?)";

            //Conecta ao banco de dados e organiza o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getSenha());
            stmt.setString(3, obj.getPrivilegio());

            //Executa o comando sql
            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Cadastrado com Sucesso!");

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
        }
    }

    //Metodo Alterar
    public void alterar(Conta obj) {
        try {
            //Cria comando sql
            String sql = "update tb_conta set nome=?, senha=?, privilegio=? where id=?";

            //Conecta ao banco de dados e organiza o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getSenha());
            stmt.setString(3, obj.getPrivilegio());
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
    public void excluir(Conta obj) {
        try {
            //Cria o comando sql
            String sql = "delete from tb_conta where id=?";

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
    public List<Conta> listar() {
        try {
            //Cria Lista
            List<Conta> lista = new ArrayList<>();

            //Cria comando sql
            String sql = "select * from tb_conta";

            //Conecta ao banco de dados e organiza o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Conta obj = new Conta();
                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setSenha(rs.getString("senha"));
                obj.setPrivilegio(rs.getString("privilegio"));

                //Executa
                lista.add(obj);
            }
            return lista;
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro:" + erro);
            return null;
        }
    }
    
    //Metodo efetuaLogin
    public int efetuaLogin(String nome, String senha ) {
        try {

            //1 passo - SQL
            String sql = "select * from tb_conta where nome = ? and senha = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                //Usuario logou

                //Caso o usuario seja do tipo admin
                if (rs.getString("privilegio").equals("Admin")) {

                    return (1);
                } 

                //Caso o usuario seja do tipo limitado 
                else if (rs.getString("privilegio").equals("Usu??rio")) {
                    
                    return (2);

                }

            } else {
                //Dados incorretos
                JOptionPane.showMessageDialog(null, "Dados incorretos!");

            }

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro : " + erro);
        }
        
        return(0);

    }

}
