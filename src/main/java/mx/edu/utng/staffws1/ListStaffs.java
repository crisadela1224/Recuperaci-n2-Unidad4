package mx.edu.utng.staffws1;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by user on 03/05/2017.
 */

public class ListStaffs extends ListActivity{
    final String NAMESPACE = "http://ws.utng.edu.mx";

    final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
            SoapEnvelope.VER11);

    private ArrayList<Staff> staffs = new ArrayList<Staff>();
    private int idSelected;
    private int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TaskWSSelect select=new TaskWSSelect();
        select.execute();
        registerForContextMenu(getListView());

    }//


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_modificar:

                Staff staff = staffs.get(selectedPosition);
                Bundle bundleStaff = new Bundle();
                for (int i = 0; i < staff.getPropertyCount(); i++) {
                    bundleStaff.putString("valor" + i, staff.getProperty(i)
                            .toString());
                }
                bundleStaff.putString("accion", "modificar");
                Intent intent = new Intent(ListStaffs.this, MainActivity.class);
                intent.putExtras(bundleStaff);
                startActivity(intent);

                return true;
            case R.id.item_eliminar:
                TaskWSDelete eliminar = new TaskWSDelete();
                eliminar.execute();

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_regresar:
                startActivity(new Intent(ListStaffs.this, MainActivity.class));
                break;
            default:
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }//
    private class TaskWSSelect extends AsyncTask<String, Integer, Boolean> {

        protected Boolean doInBackground(String... params) {

            boolean result = true;

            final String METHOD_NAME = "getStaffs";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            staffs.clear();
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            envelope.setOutputSoapObject(request);

            HttpTransportSE transporte = new HttpTransportSE(MainActivity.URL);

            try {
                transporte.call(SOAP_ACTION, envelope);

                Vector<SoapObject> response = (Vector<SoapObject>) envelope.getResponse();

                if (response != null) {

                    for (SoapObject objSoap : response) {
                        Staff staff = new Staff();

                        staff.setProperty(0, Integer.parseInt(objSoap
                                .getProperty("id").toString()));

                        staff.setProperty(1, objSoap.getProperty("firstName")
                                .toString());
                        staff.setProperty(2, objSoap.getProperty("lastName")
                                .toString());
                        staff.setProperty(3, Integer.parseInt(objSoap.getProperty("addressId")
                                .toString()));
                        staff.setProperty(4, objSoap.getProperty("email")
                                .toString());
                        staff.setProperty(5, Integer.parseInt( objSoap.getProperty("storeId")
                                .toString()));
                        staff.setProperty(6, objSoap.getProperty("active")
                                .toString());
                        staff.setProperty(7, objSoap.getProperty("username")
                                .toString());
                        staff.setProperty(8, objSoap.getProperty("password")
                                .toString());
                        staff.setProperty(9, objSoap.getProperty("lastUpdate")
                                .toString());

                        staff.setProperty(10, Integer.parseInt( objSoap.getProperty("picture")
                                .toString()));


                        staffs.add(staff);
                    }
                }

            } catch (XmlPullParserException e) {
                Log.e("Error XMLPullParser", e.toString());
                result = false;
            } catch (HttpResponseException e) {
                Log.e("Error HTTP", e.toString());
                result = false;
            } catch (IOException e) {
                Log.e("Error IO", e.toString());
                result = false;
            } catch (ClassCastException e) {

                //Enviará aquí cuando exista un solo registro en la base.
                try {
                    SoapObject objSoap = (SoapObject) envelope.getResponse();
                    Staff staff = new Staff();

                    staff.setProperty(0, Integer.parseInt(objSoap
                            .getProperty("id").toString()));

                    staff.setProperty(1, objSoap.getProperty("firstName")
                            .toString());
                    staff.setProperty(2, objSoap.getProperty("lastName")
                            .toString());
                    staff.setProperty(3, Integer.parseInt(objSoap.getProperty("addressId")
                            .toString()));
                    staff.setProperty(4, objSoap.getProperty("email")
                            .toString());
                    staff.setProperty(5, Integer.parseInt( objSoap.getProperty("storeId")
                            .toString()));
                    staff.setProperty(6, objSoap.getProperty("active")
                            .toString());
                    staff.setProperty(7, objSoap.getProperty("username")
                            .toString());
                    staff.setProperty(8, objSoap.getProperty("password")
                            .toString());
                    staff.setProperty(9, objSoap.getProperty("lastUpdate")
                            .toString());

                    staff.setProperty(10, Integer.parseInt( objSoap.getProperty("picture")
                            .toString()));

                    staffs.add(staff);
                } catch (SoapFault e1) {
                    Log.e("Error SoapFault", e.toString());
                    result = false;
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                final String[] datos = new String[staffs.size()];
                for (int i = 0; i < staffs.size(); i++) {
                    datos[i] = staffs.get(i).getProperty(0) + " - "
                            +staffs.get(i).getProperty(1) + " - "
                            +staffs.get(i).getProperty(2) + " - "
                            +staffs.get(i).getProperty(3) + " - "
                            +staffs.get(i).getProperty(4) + " - "
                            +staffs.get(i).getProperty(5) + " - "
                            +staffs.get(i).getProperty(6) + " - "
                            +staffs.get(i).getProperty(7) + " - "
                            +staffs.get(i).getProperty(8) + " - "
                            +staffs.get(i).getProperty(9) + " - "
                            + staffs.get(i).getProperty(10);
                }
//////////////////////////////////este layout
                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                        ListStaffs.this,
                        android.R.layout.simple_list_item_1, datos);
                setListAdapter(adaptador);
            } else {
                Toast.makeText(getApplicationContext(), "No se encontraron datos.",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }//

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        menu.setHeaderTitle(getListView().getAdapter().getItem(info.position)
                .toString());
        idSelected = (Integer) staffs.get(info.position).getProperty(0);
        selectedPosition = info.position;

        inflater.inflate(R.menu.menu_contextual, menu);

    }//
    private class TaskWSDelete extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;

            final String METHOD_NAME = "removeStaff";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("id", idSelected);

            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(MainActivity.URL);
            try {
                transporte.call(SOAP_ACTION, envelope);
                SoapPrimitive resultado_xml = (SoapPrimitive) envelope
                        .getResponse();
                String res = resultado_xml.toString();

                if (!res.equals("0")){
                    result = true;}

            } catch (Exception e) {
                Log.e("Error", e.toString());
                result = false;
            }

            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(getApplicationContext(),
                        "Eliminado", Toast.LENGTH_SHORT).show();
                TaskWSSelect consulta = new TaskWSSelect();
                consulta.execute();
            } else {
                Toast.makeText(getApplicationContext(), "Error al eliminar",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }

}
