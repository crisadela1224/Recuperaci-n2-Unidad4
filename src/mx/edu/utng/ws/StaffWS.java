package mx.edu.utng.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StaffWS {
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private PreparedStatement ps;
	
	public StaffWS(){
		conectar();
	}
	
	private void conectar() {
		try{
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/wsstaff","postgres","arevalo");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	
	public int addStaff(Staff staff){
		int result = 0;
		if(connection!=null){
			try{
				ps=connection.prepareStatement(
		"INSERT INTO staff(first_name, last_name, address_id, email, store_id, active, username, password, last_update, picture)"
		+ "	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
				ps.setString(1, staff.getFirstName());
				ps.setString(2, staff.getLastName());
				ps.setInt(3, staff.getAddressId());
				ps.setString(4, staff.getEmail());
				ps.setInt(5, staff.getStoreId());
				ps.setString(6, staff.getActive());
				ps.setString(7, staff.getUsername());
				ps.setString(8, staff.getPassword());
				ps.setString(9, staff.getLastUpdate());
				ps.setInt(10, staff.getPicture());
				
				result = ps.executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public int updateStaff(Staff staff){
		int result=0;
		if(connection!=null){
			try{
				ps = connection.prepareStatement("UPDATE staff SET first_name=?, last_name=?, address_id=?, email=?, "
						+ "store_id=?, active=?, username=?, password=?, last_update=?, picture=? WHERE id=?;");
				
				ps.setString(1, staff.getFirstName());
				ps.setString(2, staff.getLastName());
				ps.setInt(3, staff.getAddressId());
				ps.setString(4, staff.getEmail());
				ps.setInt(5, staff.getStoreId());
				ps.setString(6, staff.getActive());
				ps.setString(7, staff.getUsername());
				ps.setString(8, staff.getPassword());
				ps.setString(9, staff.getLastUpdate());
				ps.setInt(10, staff.getPicture());
				
				ps.setInt(11, staff.getId());
				
				result = ps.executeUpdate();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public int removeStaff(int id){
		int result=0;
		if(connection!=null){
			try{
				ps = connection.prepareStatement(
						"DELETE FROM staff WHERE id=?;");
				ps.setInt(1, id);
				result = ps.executeUpdate();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public Staff[] getStaffs(){
		Staff[] result=null;
		List<Staff> staffs = new ArrayList<Staff>();
		if(connection!=null){
			try{
				statement = connection.createStatement();
				resultSet = statement.executeQuery("SELECT * FROM staff;");
				while(resultSet.next()){
					Staff staff = new Staff(
							resultSet.getInt("id"),
							resultSet.getString("first_name"),
							resultSet.getString("last_name"),
							resultSet.getInt("address_id"),
							resultSet.getString("email"),
							resultSet.getInt("store_id"),
							resultSet.getString("active"),
							resultSet.getString("username"),
							resultSet.getString("password"),
							resultSet.getString("last_update"),
							resultSet.getInt("picture"));
				staffs.add(staff);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		result=staffs.toArray(new Staff[staffs.size()]);
		return result;
	}
	
	public Staff getStaffById(int id){
		Staff staff = null;
		if(connection!=null){
			try{
				ps = connection.prepareStatement("SELECT * FROM staff WHERE id=?;");
				ps.setInt(1, id);
				resultSet = ps.executeQuery();
				while(resultSet.next()){
					 staff = new Staff(
							 resultSet.getInt("id"),
								resultSet.getString("first_name"),
								resultSet.getString("last_name"),
								resultSet.getInt("address_id"),
								resultSet.getString("email"),
								resultSet.getInt("store_id"),
								resultSet.getString("active"),
								resultSet.getString("username"),
								resultSet.getString("password"),
								resultSet.getString("last_update"),
								resultSet.getInt("picture"));

				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return staff;
	}

}
