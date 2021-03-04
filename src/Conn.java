import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;


public class Conn {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn() throws ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:Test Java.s3db");

        System.out.println("База Подключена!");
    }

    // --------Создание таблицы--------
    public static void CreateDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute(
                "CREATE TABLE if not exists 'Types'(" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "'type' text)"
        );
        statmt.execute(
                "CREATE TABLE if not exists 'Cats' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "'name' text, " +
                "'type_id' INTEGER REFERENCES Types (id)," +
                "'weight' DOUBLE NOT NULL," +
                "'age' INTEGER NOT NULL" +
                ");");

        System.out.println("Таблица создана или уже существует.");
    }

    // --------Заполнение таблицы--------
    public static void WriteDB() throws SQLException
    {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        connection = DriverManager.getConnection("jdbc:sqlite:humans.db");
        Statement st = connection.createStatement();
        ResultSet set = st.executeQuery("SELECT name FROM users");
        String[] cat_names = new String[200394];
        int i = 0;
        while(set.next()){
            cat_names[i] = set.getString("name");
            i++;
        }
        connection.close();
        set.close();
        st.close();
        Random random = new Random();
        statmt.execute("INSERT INTO ");
        for(String j:cat_names){
            statmt.execute(String.format("INSERT INTO 'Cats' ('name', 'type_id', 'weight', 'age')" +
                            "VALUES (%s, %d, %s, %d)",
                    cat_names[random.nextInt(cat_names.length)],
                    1+random.nextInt(4),
                    1+random.nextInt(10)+Math.random(),
                    random.nextInt(10)));
        }
//        statmt.execute("INSERT INTO 'Types' ('type') VALUES ('Беспородный');");
//        statmt.execute("INSERT INTO 'Cats' ('name', 'type_id', 'weight', 'age') VALUES ('Пушок', '1', '5', '5'); ");
//        statmt.execute("INSERT INTO 'Cats' ('name', 'type_id', 'weight', 'age') VALUES ('Барсик', '1', '4', '8'); ");

        System.out.println("Таблица заполнена");
    }

    // -------- Вывод таблицы--------
    public static void ReadDB() throws ClassNotFoundException, SQLException
    {
        resSet = statmt.executeQuery("SELECT * FROM users");

        while(resSet.next())
        {
            int id = resSet.getInt("id");
            String  name = resSet.getString("name");
            String  phone = resSet.getString("phone");
            System.out.println( "ID = " + id );
            System.out.println( "name = " + name );
            System.out.println( "phone = " + phone );
            System.out.println();
        }

        System.out.println("Таблица выведена");
    }

    // --------Закрытие--------
    public static void CloseDB() throws ClassNotFoundException, SQLException
    {
        conn.close();
        statmt.close();
        resSet.close();

        System.out.println("Соединения закрыты");
    }

}