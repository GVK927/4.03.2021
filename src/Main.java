import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Conn.Conn();
        Conn.CreateDB();
        Conn.WriteDB();
        Conn.ReadDB();
        Conn.CloseDB();
    }
}
