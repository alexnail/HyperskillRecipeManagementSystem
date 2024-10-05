package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@SpringBootApplication
public class RecipesApplication implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(RecipesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        printH2VersionUsingDataSource();
    }

    private void printH2VersionUsingDataSource() throws Exception {
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT H2VERSION() AS H2_VERSION")) {
            if (rs.next()) {
                System.out.println("H2 Database Version: " + rs.getString("H2_VERSION"));
            }
        }
    }
}
