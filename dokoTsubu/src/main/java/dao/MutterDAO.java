package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Mutter;

public class MutterDAO {
	//	データベース接続に使用する情報
	private final String JDBC_URL = "jdbc:h2:~/dokoTsubu";
	private final String DB_USER = "sa";
	private final String DB_PASS = "";
	
	public List<Mutter> findAll() {
		List<Mutter> mutterList = new ArrayList<>();
		
		//JDBCドライバを読み込む
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		
		//データベース接続
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			//SELECT文の準備
			String sql = "SELECT ID, NAME, TEXT FROM MUTTER ORDER BY ID DESC";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			//SELECTを実行
			ResultSet rs = pStmt.executeQuery();
			
			//SELECT文の結果をArrayListに格納
			while (rs.next()) {
				int id = rs.getInt("ID");
				String userName = rs.getString("NAME");
				String text = rs.getString("TEXT");
				Mutter mutter = new Mutter(id, userName, text);
				mutterList.add(mutter);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return mutterList;
	}
	public boolean create(Mutter mutter) {
		//JDBCドライバを読み込む
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		
		//データベース接続
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			
			//insert文の準備
			String sql = "INSERT INTO MUTTER(NAME, TEXT) VALUES(?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			//insert文中の「？」に使用する値を設定
			pStmt.setString(1, mutter.getUserName());
			pStmt.setString(2, mutter.getText());
			
			//insert文を実行
			int result = pStmt.executeUpdate();
		} catch {
			
		}
	}
}
