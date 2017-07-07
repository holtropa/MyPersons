package nl.rabobank.personenbeheer;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class PersoonDaoImpl implements PersoonDao {


    static List<Persoon> personen = new ArrayList();
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    InputStream input = classLoader.getResourceAsStream("database.properties");

//    public InputStream getInput() {
//        return input;
//        System.out.println("De input is: " + input.toString());
//    }



    public PersoonDaoImpl() {
    }

    public Persoon addPersoon(Persoon persoon) {
        String insert_sql = "INSERT INTO XZA_MYPERSOON (ID, ACHTERNAAM, BSNNUMMER, EMAIL, GESLACHT, TELEFOONNUMMER, TUSSENVOEGSELS, VOORLETTERS, HUISNUMMER, HUISNUMMERTOEVOEGING, LAND, POSTCODE, STRAAT, WOONPLAATS, GEBOORTEPLAATS, GEMEENTE) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(insert_sql);
            if (statement != null) {
                persoon.setId(getMaxPersoonID(connection));
                statement.setInt(1, persoon.getId());
                statement.setInt(1, getMaxPersoonID(connection));
                statement.setString(2, persoon.getAchterNaam());
                statement.setString(3, "leeg");
                statement.setString(4, "leeg");
                statement.setString(5, "leeg");
                statement.setString(6, "leeg");
                statement.setString(7, "leeg");
                statement.setString(8, "leeg");
                statement.setInt(9, 0);
                statement.setString(10, "leeg");
                statement.setString(11, "leeg");
                statement.setString(12, "leeg");
                statement.setString(13, "leeg");
                statement.setString(14, "leeg");
                statement.setString(15, "leeg");
                statement.setString(16, "leeg");
                try {
                    int count = statement.executeUpdate();
                    boolean ok = count == 1;
                    //         if(!ok) id=0;
                } catch (Exception statmentExcption) {
                    statmentExcption.printStackTrace();
                    statmentExcption.printStackTrace(); //return 0 ;
                }
                statement.close();
            }
        } catch (Exception generalException) {
            generalException.printStackTrace();
            generalException.printStackTrace(); //return 0;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) { /* ignored */}
            }
        }
        return persoon;
    }

    @Override
    public void deletePersoon(Persoon persoon) {
        String delete_sql = "DELETE FROM XZA_MYPERSOON WHERE ID = ?";
        PreparedStatement insertStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(delete_sql);
            if (statement != null) {
                statement.setInt(1, persoon.getId());
                try {
                    int count = statement.executeUpdate();
                    boolean ok = count == 1;
                    //         if(!ok) id=0;
                } catch (Exception statmentExcption) {
                    statmentExcption.printStackTrace();
                    statmentExcption.printStackTrace(); //return 0 ;
                }
                statement.close();
            }
        } catch (Exception generalException) {
            generalException.printStackTrace();
            generalException.printStackTrace(); //return 0;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) { /* ignored */}
            }
        }
    }


    @Override
    public Persoon updatePersoon(Persoon persoon) {

        String update_sql = "UPDATE XZA_MYPERSOON SET WOONPLAATS = ?  WHERE ACHTERNAAM = ?";
        PreparedStatement insertStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(update_sql);
            if (statement != null) {
                statement.setString(1, "Utrecht");
                persoon.setPlaatsNaam("Utrecht");
                statement.setString(2, persoon.getAchterNaam());
                try {
                    int count = statement.executeUpdate();
                    boolean ok = count == 1;
                    //         if(!ok) id=0;
                } catch (Exception statmentExcption) {
                    statmentExcption.printStackTrace();
                    statmentExcption.printStackTrace(); //return 0 ;
                }
                statement.close();
            }
        } catch (Exception generalException) {
            generalException.printStackTrace();
            generalException.printStackTrace(); //return 0;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) { /* ignored */}
            }
        }
        return persoon;
    }

    //retrive list of Persoons from the database
    @Override
    public List<Persoon>  getAllPersonen() {
            String select_sql = "SELECT * FROM XZA_MYPERSOON";
            ArrayList<Persoon> mypersonen = new ArrayList<Persoon>();
            Connection connection = null;
            try {
                connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(select_sql);
                if (statement != null) {
                    try {
                        ResultSet result = statement.executeQuery();
                        while (result.next()) {
                            Persoon persoon = new Persoon();
                            persoon.setId(result.getInt(1));
                            persoon.setAchterNaam(result.getString(2));
                            persoon.setPlaatsNaam(result.getString(14));
                            mypersonen.add(persoon);
                        }
                    } catch (Exception statmentExcption) {
                        statmentExcption.printStackTrace();
                        statmentExcption.printStackTrace(); //return 0 ;
                    }
                    statement.close();
                }
            } catch (Exception generalException) {
                generalException.printStackTrace();
                generalException.printStackTrace(); //return 0;
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) { /* ignored */}
                }
            }
            return mypersonen;
        }

    @Override
    public Persoon getPersoon(int rollNo) {
        String select_sql = "SELECT * FROM XZA_MYPERSOON" + " WHERE ID = ?";
        Persoon persoon = new Persoon();
        PreparedStatement insertStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(select_sql);
            if (statement != null) {
                statement.setInt(1, rollNo);
                try {
                    ResultSet result = statement.executeQuery();
                    if (result.next()) {
                    persoon.setId(rollNo);
                    persoon.setAchterNaam(result.getString(2));
                    persoon.setPlaatsNaam(result.getString(14));
                    }
                } catch (Exception statmentExcption) {
                    statmentExcption.printStackTrace();
                    statmentExcption.printStackTrace(); //return 0 ;
                }
                statement.close();
            }
        } catch (Exception generalException) {
            generalException.printStackTrace();
            generalException.printStackTrace(); //return 0;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) { /* ignored */}
            }
        }
        return persoon;
    }

    public Persoon getPersoonOpNaam(String naam) {
        String select_sql = "SELECT * FROM XZA_MYPERSOON" + " WHERE ACHTERNAAM = ?";
        Persoon persoon = new Persoon();
        PreparedStatement insertStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(select_sql);
            if (statement != null) {
                statement.setString(1, naam);
                try {
                    ResultSet result = statement.executeQuery();
                    result.next();
                    persoon.setId(result.getInt(1));
                    persoon.setAchterNaam(result.getString(2));
                    persoon.setPlaatsNaam(result.getString(14));
                } catch (Exception statmentExcption) {
                    statmentExcption.printStackTrace();
                    statmentExcption.printStackTrace(); //return 0 ;
                }
                statement.close();
            }
        } catch (Exception generalException) {
            generalException.printStackTrace();
            generalException.printStackTrace(); //return 0;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) { /* ignored */}
            }
        }
        return persoon;
    }

    private Connection getConnection() {

        Connection connection = null;
        try {
            File file = new File(getClass().getClassLoader().getResource("database.properties").getFile());
            input = new FileInputStream(file);
            Properties prop = new Properties();
            prop.load(input);
            String url = prop.getProperty("database.connection.url");
            String user = prop.getProperty("database.connection.user");
            String password = prop.getProperty("database.connection.password");

      //      connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "holtropa", "holtropia");
      //      connection = DriverManager.getConnection(@Value("${database.connection.url}") String url, @Value("${database.connection.user}") String user, @Value("${database.connection.password}") String password);
            connection = DriverManager.getConnection(url, user, password);
              //  public QueryRelationArrangementViewHeaderFactory(@Value("${rhd.requestorDomain}") String requestorDomain, @Value("${rhd.requestorId}") String requestorId) {
        } catch (Exception e) {
            return connection;
        }
        return connection;
    }


    private Connection getConnectionViaSpring() {

        Connection connection = null;
        try {
            File file = new File(getClass().getClassLoader().getResource("database.properties").getFile());
            input = new FileInputStream(file);
            Properties prop = new Properties();
            prop.load(input);
            String url = prop.getProperty("database.connection.url");
            String user = prop.getProperty("database.connection.user");
            String password = prop.getProperty("database.connection.password");

            //      connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "holtropa", "holtropia");
            //      connection = DriverManager.getConnection(@Value("${database.connection.url}") String url, @Value("${database.connection.user}") String user, @Value("${database.connection.password}") String password);
            connection = DriverManager.getConnection(url, user, password);
            //  public QueryRelationArrangementViewHeaderFactory(@Value("${rhd.requestorDomain}") String requestorDomain, @Value("${rhd.requestorId}") String requestorId) {
        } catch (Exception e) {
            return connection;
        }
        return connection;
    }


    public static int getMaxPersoonID(Connection connection) {
        int id = 0;
        String sql = "SELECT  NVL(MAX(ID),0)+1  FROM XZA_MYPERSOON ";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            if (statement != null) {
                try {
                    ResultSet results = statement.executeQuery();
                    if (results != null) {
                        try {
                            if (results.next()) {
                                id = results.getInt(1);
                            }
                        } catch (Exception resultSetException) {
                            resultSetException.printStackTrace();
                        }
                        results.close();
                    }
                } catch (Exception statmentExcption) {
                    statmentExcption.printStackTrace();
                }
                statement.close();
            }
        } catch (Exception generalException) {
            generalException.printStackTrace();
        }
        return id;
    }
}


