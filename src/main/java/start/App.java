package start;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class App {
	public void deleteItem(int id) throws ClassNotFoundException, SQLException {
		String quiry = "delete from quiz where id =?";
		PreparedStatement ps = getconnection().prepareStatement(quiry);
		ps.execute();
	}

	public void getTable() throws ClassNotFoundException, SQLException {
		String quiry = "select * from quiz";
		PreparedStatement ps = getconnection().prepareStatement(quiry);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id = rs.getInt("id");
			String question = rs.getNString("question");
			System.out.println(question);
			String opt1 = rs.getNString("option1");
			System.out.println(opt1);
			String opt2 = rs.getNString("option2");
			System.out.println(opt2);
			String opt3 = rs.getNString("option3");
			System.out.println(opt3);
			int scorepoint = rs.getInt("scorepoint");
			Scanner s = new Scanner(System.in);
			System.out.println("please select the ansewr");
			int num = s.nextInt();
			App a = new App();
			a.updateTable(scorepoint, id);


		}
		System.out.println("your scor total is : "+getTotalScore());
	}

	public void updateTable(int score, int id) throws ClassNotFoundException, SQLException {
		String quiry = "update quiz set score =? where id =?";
		PreparedStatement ps = getconnection().prepareStatement(quiry);
		ps.setInt(1, score);
		ps.setInt(2, id);
		ps.executeUpdate();
		ps.close();
	}

	public void addTable(String question, String opt1, String opt2, String opt3, String answer, int scor)
			throws ClassNotFoundException, SQLException {
		String quiry = "insert into quiz(question,option1, option2,option3,answer,scorepoint) values (?,?,?,?,?,?)";

		PreparedStatement ps = getconnection().prepareStatement(quiry);
		ps.setString(1, question);
		ps.setString(2, opt1);
		ps.setString(3, opt2);
		ps.setString(4, opt3);
		ps.setString(5, answer);
		ps.setInt(6, scor);
		ps.execute();
		System.out.println("successed updated");

		getconnection().close();

	}

	public static Connection getconnection() throws ClassNotFoundException, SQLException {
		String url = "jdbc:mysql://localhost:3306/examDB";
		String user = "root";
		String password = "root";

		Connection conn = DriverManager.getConnection(url, user, password);
		Class.forName("com.mysql.cj.jdbc.Driver");
		return conn;
	}
public static int getTotalScore() throws SQLException, ClassNotFoundException {
	String	quiry = "select score from quiz where score > 0 ";
	PreparedStatement ps = getconnection().prepareStatement(quiry);
	ResultSet rs = ps.executeQuery();
	int scorTotal = 0 ;
	while (rs.next()) {
		int score = rs.getInt("score");
		scorTotal = score + scorTotal;
	}
		return scorTotal;
}
	public static void main(String[] args) throws SQLException, ClassNotFoundException, InterruptedException {
		App app = new App();
		//app.deleteItem(1);
		Scanner s = new Scanner(System.in);

		System.out.println("to start the quiz press 1\n" + "to add question press 2 ");
		int num = s.nextInt();
		if (num == 1) {
			app.getTable();
		} else if (num == 2) {

			System.out.println("enter the question ");
			String question = s.nextLine();
			String question1 = s.nextLine();
			System.out.println("enter the option one  ");
			String opt1 = s.nextLine();
			System.out.println("enter the option two  ");
			String opt2 = s.nextLine();
			System.out.println("enter the option three  ");
			String opt3 = s.nextLine();
			System.out.println("enter the answer  ");
			String answer = s.nextLine();
			System.out.println("enter the score for the question  ");
			int score = s.nextInt();
			app.addTable(question1, opt1, opt2, opt3, answer, score);

			s.close();
		}

	}

}
