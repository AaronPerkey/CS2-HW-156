package unl.cse.albums;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Album {

	private Integer albumId;
	private String title;
	private Integer year;
	private Band band;
	private List<String> songTitles = new ArrayList<String>();
	
	public Album(Integer albumId, String title, Integer year, Band band) {
		super();
		this.albumId = albumId;
		this.title = title;
		this.year = year;
		this.band = band;
	}

	public Album(String title, Integer year, String bandName) {
		this(null, title, year, new Band(bandName));
	}
	
	public Album(Integer albumId, String title) {
		super();
		this.albumId = albumId;
		this.title = title;
	}

	public Integer getAlbumId() {
		return albumId;
	}

	public String getTitle() {
		return title;
	}

	public Integer getYear() {
		return year;
	}

	public Band getBand() {
		return band;
	}

	public List<String> getSongTitles() {
		return songTitles;
	}

	public void addSong(String songTitle) {
		this.songTitles.add(songTitle);
	}
	
	/**
	 * This method returns an {@link #Album} instance loaded from the 
	 * database corresponding to the given <code>albumId</code>.  
	 * Returns <code>null</code> if the <code>albumId</code> is
	 * invalid.
	 * 
	 * All fields are loaded with this method.
	 * @param albumId
	 * @return
	 */
	public static Album getDetailedAlbum(int albumId) {
	Album a = null;
		
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		

		String query = "SELECT * FROM Album WHERE albumId = ?";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, albumId);
			rs = ps.executeQuery();
			if(rs.next()) {
				String title = rs.getString("title");
				int year = rs.getInt("year");
				int bandId = rs.getInt("bandId");
				Band band = Band.getBand(bandId);
				a = new Album(albumId, title, year, band);
			} else {
				throw new IllegalStateException("no such band with bandId = " + albumId);
			}
			
			
			String query2 = "SELECT Song.title FROM Song "
					+ "left join AlbumSong on Song.songId = AlbumSong.songId "
					+ "left join Album on Album.albumId = AlbumSong.albumId "
					+ "Where Album.albumId = ? order by AlbumSong.trackNumber";
			
			PreparedStatement ps2 = null;
			ResultSet rs2 = null;
			
			ps2 = conn.prepareStatement(query2);
			ps2.setInt(1, albumId);
			rs2 = ps2.executeQuery();
			while(rs2.next()) {
				String songTitle = rs2.getString("title");
				a.addSong(songTitle);
			}
			
			rs2.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return a;
	}
	
	/**
	 * Returns a list of all albums in the database.  However, this
	 * is only a summary so only the following items need to be loaded
	 * from the database:
	 * <ul>
	 *   <li>Album ID</li>
	 *   <li>Album Title</li>
	 *   <li>Album Year</li>
	 *   <li>Band ID</li>
	 *   <li>Band Name</li>
	 * </ul>
	 *   
	 * @return
	 */
	public static List<Album> getAlbumSummaries() {
		
		List<Album> allAlbums = new ArrayList<Album>();
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			
			
			
			String query = "SELECT * FROM Album";

			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				ps = conn.prepareStatement(query);
				
				rs = ps.executeQuery();
				while(rs.next()) {
					int albumId = rs.getInt("albumId");
					String title = rs.getString("title");
					int year = rs.getInt("year");
					int bandId = rs.getInt("bandId");
					Band band = Band.getBand(bandId);
					Album a = new Album(albumId, title, year, band);
					allAlbums.add(a);
					
				}
				rs.close();
			} catch (SQLException e) {
				System.out.println("SQLException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return allAlbums;
	}

	@Override
	public String toString() {
		return title + " (id = " + albumId + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((albumId == null) ? 0 : albumId.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Album other = (Album) obj;
		if (albumId == null) {
			if (other.albumId != null)
				return false;
		} else if (!albumId.equals(other.albumId))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	
	
	
	
}
