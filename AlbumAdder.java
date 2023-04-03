package unl.cse.albums;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class AlbumAdder {

	/**
	 * Adds the necessary record(s) to the database corresponding to the album and
	 * band attributes given. Returns the primary key value of the newly inserted
	 * album record.
	 * 
	 * If any error(s) occurs or invalid data is given, this method throws a
	 * {@link RuntimeException}.
	 * 
	 * @param albumTitle
	 * @param bandName
	 * @param albumYear
	 * @param albumNumber
	 * @return
	 * @throws SQLException 
	 */
	public static int addAlbumToDatabase(String albumTitle, String bandName, String albumYear, String albumNumber) {
		Connection conn = null;
		int key2 = 0;
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
		String query = "insert into Band (name) values ('?');";
		PreparedStatement ps;
		
		try {
			ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		
			ps.setString(1, bandName);
		//prepare ps by setting any parameters
		ps.executeUpdate();

		//get the generated keys:
		ResultSet keys = ps.getGeneratedKeys();

		//if we only expect one:
		keys.next();
		int key = keys.getInt(1);
		
		

		Band b = Band.getBand(key);

		String query2 = "insert into Album (title,year,number,bandId) values ('?',?,?,?);";
		
		PreparedStatement ps2 = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

		ps2 = conn.prepareStatement(query2);
		ps2.setString(1, albumTitle);
		ps2.setString(2, albumYear);
		ps2.setString(3, albumNumber);
		ps2.setInt(4, b.getBandId());
		ps2.executeUpdate();
		
		//get the generated keys:
		ResultSet keys2 = ps2.getGeneratedKeys();

		//if we only expect one:
		keys.next();
		key2 = keys2.getInt(1);
		
		ps.setString(1, bandName);
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return key2;
	}

}
