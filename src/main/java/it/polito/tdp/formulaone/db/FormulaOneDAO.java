package it.polito.tdp.formulaone.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import it.polito.tdp.formulaone.model.Adiacenza;
import it.polito.tdp.formulaone.model.Circuit;
import it.polito.tdp.formulaone.model.Constructor;
import it.polito.tdp.formulaone.model.Driver;
import it.polito.tdp.formulaone.model.Season;


public class FormulaOneDAO {

	public List<Integer> getAllYearsOfRace() {

		String sql = "SELECT year FROM races ORDER BY year" ;

		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;

			ResultSet rs = st.executeQuery() ;

			List<Integer> list = new ArrayList<>() ;
			while(rs.next()) {
				list.add(rs.getInt("year"));
			}

			conn.close();
			return list ;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}

	public List<Season> getAllSeasons() {

		String sql = "SELECT year, url FROM seasons ORDER BY year" ;

		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;

			ResultSet rs = st.executeQuery() ;

			List<Season> list = new ArrayList<>() ;
			while(rs.next()) {
				list.add(new Season(rs.getInt("year"), rs.getString("url"))) ;
			}

			conn.close();
			return list ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<Circuit> getAllCircuits() {

		String sql = "SELECT circuitId, name FROM circuits ORDER BY name";

		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			List<Circuit> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Circuit(rs.getInt("circuitId"), rs.getString("name")));
			}

			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}

	public List<Constructor> getAllConstructors() {

		String sql = "SELECT constructorId, name FROM constructors ORDER BY name";

		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			List<Constructor> constructors = new ArrayList<>();
			while (rs.next()) {
				constructors.add(new Constructor(rs.getInt("constructorId"), rs.getString("name")));
			}

			conn.close();
			return constructors;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}


	public List<Driver> getAllDrivers(Season stagione, Map<Integer, Driver> driverIdMap) {

		String sql = "SELECT DISTINCT d.driverId, d.driverRef, d.number, d.code, d.forename, d.surname, d.dob, d.nationality, d.url " + 
				"FROM drivers AS d, results AS r, races AS ra " + 
				"WHERE r.driverId = d.driverId AND r.positionText <> 'R' AND ra.year = ? " + 
				"AND ra.raceId = r.raceId";

		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, stagione.getYear());

			ResultSet res = st.executeQuery();

			List<Driver> list = new ArrayList<>();
			while (res.next()) {
				Driver d = new Driver(res.getInt("driverId"), res.getString("driverRef"), res.getInt("number"), res.getString("code"), 
						res.getString("forename"), res.getString("surname"), res.getDate("dob").toLocalDate(), res.getString("nationality"), res.getString("url"));
				list.add(d);
				driverIdMap.put(res.getInt("driverId"), d);
			}

			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}

	public List<Adiacenza> getAdiacenza(Map<Integer, Driver> driverIdMap) {

		String sql = "SELECT r1.driverId AS id1, r2.driverId AS id2, COUNT(DISTINCT r1.raceId) AS peso " + 
				"FROM results AS r1, results AS r2 " + 
				"WHERE r1.driverId <> r2.driverId AND r1.raceId = r2.raceId " + 
				"AND r1.position < r2.position " + 
				"GROUP BY id1, id2";

		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet res = st.executeQuery();

			List<Adiacenza> list = new ArrayList<>();

			while (res.next()) {
				int id1 = res.getInt("id1");
				int id2 = res.getInt("id2");



				Driver d1 = driverIdMap.get(id1);
				Driver d2 = driverIdMap.get(id2);

				if(d1 == null || d2 == null) {
					System.err.format("Skipping %d %d\n", id1, id2);
				}
				else {
					list.add(new Adiacenza (d1, d2, res.getInt("peso")));
				}
			}



			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
}

