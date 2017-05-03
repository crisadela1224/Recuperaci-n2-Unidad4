package mx.edu.utng.ws;

public class Staff {
	
	private int id;
	private String firstName;
	private String lastName;
	private int addressId;
	private String email;
	private int storeId;
	private String active;
	private String username;
	private String password;
	private String lastUpdate;
	private int picture;
	
	public Staff(int id, String firstName, String lastName, int addressId, String email, int storeId,
			String active, String username, String password, String lastUpdate, int picture) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.addressId = addressId;
		this.email = email;
		this.storeId = storeId;
		this.active = active;
		this.username = username;
		this.password = password;
		this.lastUpdate = lastUpdate;
		this.picture = picture;
	}

	public Staff() {
		this(0,"","",0,"",0,"","","","",0);
	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public int getPicture() {
		return picture;
	}

	public void setPicture(int picture) {
		this.picture = picture;
	}

	@Override
	public String toString() {
		return "Staff [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", addressId=" + addressId
				+ ", email=" + email + ", storeId=" + storeId + ", active=" + active + ", username=" + username
				+ ", password=" + password + ", lastUpdate=" + lastUpdate + ", picture=" + picture + "]";
	}

	

}
