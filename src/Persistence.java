import java.sql.*;
public class Persistence {
	public static Connection createConnection() throws SQLException
	{
		String url = "35.247.193.20:3306/TCC"; //Nome da base de dados
        String user = "root"; //nome do usuário do MySQL
        String password = "666tntb"; //senha do MySQL
         
        Connection con = null;
        con = DriverManager.getConnection(url, user, password);
         
        return con;
	}
}
