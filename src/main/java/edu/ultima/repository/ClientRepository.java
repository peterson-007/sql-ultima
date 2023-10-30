package edu.ultima.repository;

import edu.ultima.domain.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//Classe para acessar e manipular instâncias do objeto Client no banco de dados
public class ClientRepository {

    private DataBaseConnection connection;

    public ClientRepository() {
        this.connection = DataBaseConnection.getInstance();
    }

    public List<Client> findAll() throws SQLException {
        ArrayList<Client> clients = new ArrayList<>();

        //Monta uma consulta SQL a ser realizada no banco
        PreparedStatement preparedStatement = this.connection
                .getConnection()
                .prepareStatement("SELECT * FROM client");

        //Armazena o resultada da consulta
        ResultSet resultSet = preparedStatement.executeQuery();

        //Percorrer o ResultSet e pegar os resultados
        while (resultSet.next()){
            Client client = new Client();
            client.setId(resultSet.getInt("id"));
            client.setFirstName(resultSet.getString("first_name"));
            client.setLastName(resultSet.getString("last_name"));
            client.setEmail(resultSet.getString("email"));
            client.setCpf(resultSet.getString("cpf"));
            client.setAge(resultSet.getInt("age"));
            clients.add(client);
        }

        return clients;
    }

    public Client findById(int id) throws SQLException {
        Client client = null;

        //Monta uma consulta SQL a ser realizada no banco
        PreparedStatement preparedStatement = this.connection
                .getConnection()
                .prepareStatement("SELECT * FROM client WHERE id = ?");
        preparedStatement.setInt(1,id);//quantos e qual parâmetro a ser substituído(?)
        //Armazena o resultada da consulta
        ResultSet resultSet = preparedStatement.executeQuery();

        //Verifica se existe registro com o id informado
        if(resultSet.next()){
            client = new Client();
            client.setId(resultSet.getInt("id"));
            client.setFirstName(resultSet.getString("first_name"));
            client.setLastName(resultSet.getString("last_name"));
            client.setEmail(resultSet.getString("email"));
            client.setCpf(resultSet.getString("cpf"));
            client.setAge(resultSet.getInt("age"));
        }
        //caso exista, retorna a instância
        //caso não exista, retorna null
        return client;
    }

    public boolean insert(Client client) throws SQLException {
        boolean inserted = false;

        String insertSql = "INSERT INTO client (first_name, last_name, email, cpf, age)"+
                            " VALUES (?,?,?,?,?)";

        PreparedStatement preparedStatement = this.connection
                .getConnection()
                .prepareStatement(insertSql);

        preparedStatement.setString(1, client.getFirstName());
        preparedStatement.setString(2, client.getLastName());
        preparedStatement.setString(3, client.getEmail());
        preparedStatement.setString(4, client.getCpf());
        preparedStatement.setInt(5, client.getAge());

        inserted = preparedStatement.execute();

        return  inserted;
    }

    public boolean update(Client client) throws SQLException{
        boolean updated = false;

        //client null ou id = 0 , retorna false
        if(client == null || client.getId() <= 0 ){
            return false;
        }

        //tomar cuidado com os espaços
        String updateSql = "UPDATE client "+
                "SET first_name = ?,"+
                "last_name = ?,"+
                "email = ?,"+
                "cpf = ?,"+
                "age = ? "+
                "WHERE id = ?";

        PreparedStatement preparedStatement = this.connection
                .getConnection()
                .prepareStatement(updateSql);

        preparedStatement.setString(1, client.getFirstName());
        preparedStatement.setString(2, client.getLastName());
        preparedStatement.setString(3, client.getEmail());
        preparedStatement.setString(4, client.getCpf());
        preparedStatement.setInt(5, client.getAge());
        preparedStatement.setInt(6, client.getId());

        updated = preparedStatement.execute();

        return updated;
    }

    public boolean delete(int id) throws SQLException {
        boolean isDeleted = false;

        PreparedStatement preparedStatement = this.connection
                .getConnection()
                .prepareStatement("DELETE FROM client WHERE id = ?");

        preparedStatement.setInt(1, id);

        isDeleted = preparedStatement.execute();

        return isDeleted;
    }
}
