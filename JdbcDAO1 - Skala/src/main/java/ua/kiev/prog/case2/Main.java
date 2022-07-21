package ua.kiev.prog.case2;

import ua.kiev.prog.shared.Client;
import ua.kiev.prog.shared.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection())
        {
            // remove this
            try {
                try (Statement st = conn.createStatement()) {
                    st.execute("DROP TABLE IF EXISTS Clients");
                    //st.execute("CREATE TABLE Clients (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20) NOT NULL, age INT)");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            ClientDAOImpl2 dao = new ClientDAOImpl2(conn, "Clients");
            dao.createTable(Client.class);

            Client c = new Client("test", 1);
            dao.add(c);
            Client c1 = new Client("test1", 1);
            dao.add(c1);
            Client c2 = new Client(2);
            dao.add(c2);
            Client c3 = new Client("test3");
            dao.add(c3);
            Client c4 = new Client("test4", 4);
            dao.add(c4);
            Client c5 = new Client("test5", 5);
            dao.add(c5);
            System.out.println(c5.getId() + " - ID client you add with fields" + c5.toString());

            System.out.println("All clients you add to table:");
            List<Client> list = dao.getAll(Client.class);
            for (Client cli : list)
                System.out.println(cli);

            System.out.println("------------------------");

            System.out.println("All clients you add to table with initialized fields name and age:");
            list.get(0).setAge(55);
            dao.update(list.get(0));

            List<Client> list1 = dao.getAll(Client.class, "name", "age");
//            List<Client> list2 = dao.getAll(Client.class, "age");
            for (Client cli : list1)
                System.out.println(cli);



            dao.delete(list.get(0));
        }
    }
}
