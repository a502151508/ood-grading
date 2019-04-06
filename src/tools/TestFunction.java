package tools;

public class TestFunction {

	public static void main(String[] args) {
		String sql = "CREATE TABLE COMPANY " +
                "(ID INT PRIMARY KEY     NOT NULL," +
                " NAME           TEXT    NOT NULL, " + 
                " AGE            INT     NOT NULL, " + 
                " ADDRESS        CHAR(50), " + 
                " SALARY         REAL)"; 
		DBUtil.dosql(sql);
	}

}
