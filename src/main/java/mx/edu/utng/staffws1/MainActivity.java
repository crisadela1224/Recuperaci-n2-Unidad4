package mx.edu.utng.staffws1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etAddressId;
    private EditText etEmail;
    private EditText etStoreId;
    private EditText etActive;
    private EditText etUsername;
    private EditText etPasswoord;
    private EditText etLastUpdate;
    private EditText etPicture;

    private Button btnSave;
    private Button btnList;

    private Staff staff = null;

    final String NAMESPACE = "http://ws.utng.edu.mx";
    final SoapSerializationEnvelope envelope =
            new SoapSerializationEnvelope(SoapEnvelope.VER11);
    static String URL = "http://192.168.24.203:8080/StaffWS/services/StaffWS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        etFirstName = (EditText) findViewById(R.id.tv_first_name);
        etLastName = (EditText) findViewById(R.id.tv_last_name);
        etAddressId = (EditText) findViewById(R.id.tv_address_id);
        etEmail = (EditText) findViewById(R.id.tv_email);
        etStoreId = (EditText) findViewById(R.id.tv_store_id);
        etActive = (EditText) findViewById(R.id.tv_active);
        etUsername = (EditText) findViewById(R.id.tv_username);
        etPasswoord = (EditText) findViewById(R.id.tv_password);
        etLastUpdate = (EditText) findViewById(R.id.tv_last_update);
        etPicture = (EditText) findViewById(R.id.tv_picture);



        btnSave = (Button) findViewById(R.id.btn_save);
        btnList = (Button) findViewById(R.id.btn_list);
        btnSave.setOnClickListener(this);
        btnList.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_consume_w, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnSave.getId()) {
            try {
                if (getIntent().getExtras().getString("accion")
                        .equals("modificar")) {
                    TaskWSUpdate tarea = new TaskWSUpdate();
                    tarea.execute();
                }

            } catch (Exception e) {
                //Cuando no se haya mandado una accion por defecto es insertar.
                TaskWSInsert tarea = new TaskWSInsert();
                tarea.execute();
            }
        }
        if (btnList.getId() == v.getId()) {
            startActivity(new Intent(MainActivity.this, ListStaffs.class));
        }
    }

    private class TaskWSInsert extends
            AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;
            final String METHOD_NAME = "addStaff";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            staff = new Staff();
            staff.setProperty(0, 0);

            getData();

            PropertyInfo info = new PropertyInfo();
            info.setName("staff");
            info.setValue(staff);
            info.setType(staff.getClass());
            request.addProperty(info);
            envelope.setOutputSoapObject(request);
            envelope.addMapping(NAMESPACE, "Staff", Staff.class);

            /* Para serializar flotantes y otros tipos no cadenas o enteros*/
            MarshalFloat mf = new MarshalFloat();
            mf.register(envelope);

            HttpTransportSE transporte = new HttpTransportSE(URL);
            try {
                transporte.call(SOAP_ACTION, envelope);
                SoapPrimitive response =
                        (SoapPrimitive) envelope.getResponse();
                String res = response.toString();
                if (!res.equals("1")) {
                    result = false;
                }

            } catch (Exception e) {
                Log.e("Error ", e.getMessage());
                result = false;
            }


            return result;
        }


        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(getApplicationContext(),
                        "Registro exitoso.",
                        Toast.LENGTH_SHORT).show();
                cleanBox();

            } else {
                Toast.makeText(getApplicationContext(),
                        "Error al insertar.",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }//

    private void cleanBox() {
        etFirstName.setText("");
        etLastName.setText("");
        etAddressId.setText("");
        etEmail.setText("");
        etStoreId.setText("");
        etActive.setText("");
        etUsername.setText("");
        etPasswoord.setText("");
        etLastUpdate.setText("");
        etPicture.setText("");

    }

    private class TaskWSUpdate extends
            AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;

            final String METHOD_NAME = "updateStaff";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            staff = new Staff();
            staff.setProperty(0, getIntent().getExtras().getString("valor0"));
            getData();

            PropertyInfo info = new PropertyInfo();
            info.setName("staff");
            info.setValue(staff);
            info.setType(staff.getClass());

            request.addProperty(info);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);

            envelope.addMapping(NAMESPACE, "Staff", staff.getClass());

            MarshalFloat mf = new MarshalFloat();
            mf.register(envelope);

            HttpTransportSE transporte = new HttpTransportSE(URL);

            try {
                transporte.call(SOAP_ACTION, envelope);
                SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
                String res = resultado_xml.toString();

                if (!res.equals("1")) {
                    result = false;
                }

            } catch (HttpResponseException e) {
                Log.e("Error HTTP", e.toString());
            } catch (IOException e) {
                Log.e("Error IO", e.toString());
            } catch (XmlPullParserException e) {
                Log.e("Error XmlPullParser", e.toString());
            }


            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(getApplicationContext(), "Actualizado OK",
                        Toast.LENGTH_SHORT).show();
                cleanBox();
                startActivity(new Intent(MainActivity.this, MainActivity.class));

            } else {
                Toast.makeText(getApplicationContext(), "Error al actualizar",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }//

    private void getData() {
        staff.setProperty(1, etFirstName.getText().toString());
        staff.setProperty(2, etLastName.getText().toString());
        staff.setProperty(3, Integer.parseInt(etAddressId.getText().toString()));
        staff.setProperty(4, etEmail.getText().toString());
        staff.setProperty(5, Integer.parseInt(etStoreId.getText().toString()));
        staff.setProperty(6, etActive.getText().toString());
        staff.setProperty(7, etUsername.getText().toString());
        staff.setProperty(8, etPasswoord.getText().toString());
        staff.setProperty(9, etLastUpdate.getText().toString());
        staff.setProperty(10, Integer.parseInt(etPicture.getText().toString()));


    }//

    @Override
    protected void onResume() {
        super.onResume();
        Bundle datosRegreso = this.getIntent().getExtras();
        try {
            Log.i("Dato", datosRegreso.getString("valor10"));

            etFirstName.setText(datosRegreso.getString("valor1"));
            etLastName.setText(datosRegreso.getString("valor2"));
            etAddressId.setText(datosRegreso.getString("valor3"));
            etEmail.setText(datosRegreso.getString("valor4"));
            etStoreId.setText(datosRegreso.getString("valor5"));
            etActive.setText(datosRegreso.getString("valor6"));
            etUsername.setText(datosRegreso.getString("valor7"));
            etPasswoord.setText(datosRegreso.getString("valor8"));
            etLastUpdate.setText(datosRegreso.getString("valor9"));
            etPicture.setText(datosRegreso.getString("valor10"));


        } catch (Exception e) {
            Log.e("Error al Recargar", e.toString());
        }
    }
}
