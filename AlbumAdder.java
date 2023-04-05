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
		int bandId = -1;
		int albumId = -1;

		if (albumTitle == null || bandName == null || albumYear == null || albumNumber == null) {
			throw new IllegalArgumentException("Invalid arugment");
		}

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			String query0 = "SELECT * FROM Band WHERE name = ?;";
			PreparedStatement ps0 = null;
			ps0 = conn.prepareStatement(query0);
			ps0.setString(1, bandName);
			ResultSet rs0 = ps0.executeQuery();
			if(rs0.next()) {
				bandId = rs0.getInt("bandId");
				System.out.println("bandId" + bandId);
			}
			
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		if (bandId == -1) {
			String query = "insert into Band (name) values (?);";
			PreparedStatement ps;
	
			try {
				ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, bandName);
				
				// prepare ps by setting any parameters
				ps.executeUpdate();
	
				// get the generated keys:
				ResultSet keys = ps.getGeneratedKeys();
	
				// if we only expect one:
				keys.next();
				int key = keys.getInt(1);
	
				Band b = Band.getBand(key);
				bandId = b.getBandId();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		// TODO: Check if the band name is already inserted, if so return the band id

			

				
				//Check if album exists
				
				String albumQuery = "SELECT * FROM Album WHERE title = ?;";
				PreparedStatement psA = null;
				try {
					psA = conn.prepareStatement(albumQuery);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					psA.setString(1, albumTitle);
					ResultSet rsA = psA.executeQuery();
					if(rsA.next()) {
						albumId = rsA.getInt("albumId");
						System.out.println("albumId line 85" + albumId);
					} else {
						System.out.println("failed to find album line 87");
					}
					if (albumId == -1) {
						String query2 = "insert into Album (title,year,number,bandId) values (?,?,?,?);";
	
						PreparedStatement ps2 = conn.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
	
						ps2 = conn.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
						ps2.setString(1, albumTitle);
						ps2.setString(2, albumYear);
						ps2.setString(3, albumNumber);
						ps2.setInt(4, bandId);
	
						int rows = ps2.executeUpdate();
						System.out.println("RowCnt" + rows);
						if (rows == 0) {
							return -1;
						}
						
	
						// get the generated keys:
						ResultSet keys2 = ps2.getGeneratedKeys();
	
						// if we only expect one:
						keys2.next();
						albumId = keys2.getInt(1);
						System.out.println(bandId);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return albumId;
	}

}
