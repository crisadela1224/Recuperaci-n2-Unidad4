package mx.edu.utng.staffws1;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by user on 03/05/2017.
 */

public class Staff implements KvmSerializable{
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

    public Staff(int id, String firstName, String lastName,
                 int addressId, String email, int storeId, String active, String username, String password, String lastUpdate, int picture) {
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
    public Staff(){
        this(0,"","",0,"",0,"","","","",0);
    }
    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return id;
            case 1:
                return firstName;
            case 2:
                return lastName;
            case 3:
                return addressId;
            case 4:
                return email;
            case 5:
                return storeId;
            case 6:
                return active;
            case 7:
                return username;
            case 8:
                return password;
            case 9:
                return lastUpdate;
            case 10:
                return picture;

        }

        return null;
    }

    @Override
    public int getPropertyCount() {
        return 11;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i) {
            case 0:
                id = Integer.parseInt(o.toString());
                break;
            case 1:
                firstName = o.toString();
                break;
            case 2:
                lastName = o.toString();
                break;
            case 3:
                addressId =Integer.parseInt( o.toString());
                break;
            case 4:
                email = o.toString();
                break;
            case 5:
                storeId =Integer.parseInt( o.toString());
                break;
            case 6:
                active = o.toString();
                break;
            case 7:
                username = o.toString();
                break;
            case 8:
               password = o.toString();
                break;
            case 9:
                lastUpdate = o.toString();
                break;
            case 10:
                picture = Integer.parseInt(o.toString());
                break;

            default:
                break;
        }

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        switch (i) {
            case 0:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "id";
                break;
            case 1:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "firstName";
                break;
            case 2:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "lastName";
                break;
            case 3:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "addressId";
                break;
            case 4:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "email";
                break;
            case 5:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "storeId";
                break;
            case 6:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "active";
                break;
            case 7:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "username";
                break;
            case 8:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "password";
                break;
            case 9:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "lastUpdate";
                break;
            case 10:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "picture";
                break;
            default:
                break;
        }
    }
}
