package edu.ultima;

import edu.ultima.domain.Client;
import edu.ultima.repository.ClientRepository;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        System.out.println("Clientes na base de dados: ");

        ClientRepository clientRepository = new ClientRepository();

       // clientRepository.insert(
       //       new Client("Astrogildo","Souza","88877766644","astro@gmail.com",39));

        clientRepository.delete(6);

       //clientRepository.update(
       //        new Client(1,"Carlos","Coelho","33344455577","carlos@gmail.com",50));

       clientRepository.findAll().forEach(System.out::println);

        /*Client client = clientRepository.findById(10);
        if(client != null){
            System.out.println(client);
        } else {
            System.out.println("Cliente não encontrado");
        } */

    }
}
