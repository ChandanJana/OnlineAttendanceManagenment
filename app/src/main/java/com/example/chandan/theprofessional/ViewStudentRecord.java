package com.example.chandan.theprofessional;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.chandan.theprofessional.Adapter.Custom_Student_View;
import com.example.chandan.theprofessional.Key.Key;
import com.example.chandan.theprofessional.Model.Student;
import com.example.chandan.theprofessional.Parse.Parse_Student;
import com.example.chandan.theprofessional.Parse.Parse_student_batch;
import com.example.chandan.theprofessional.Parse.Parse_student_class;
import com.example.chandan.theprofessional.URL.URL;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class ViewStudentRecord extends Fragment {

    private Spinner classSpinner, batchSpinner;
    private ListView studentListView;
    private Student student;
    private ProgressDialog dialog = null;
    private boolean[] checkedStudent;
    private List<Student> studentList = new ArrayList<Student>();
    private Custom_Student_View adapter;
    private Parse_Student json_student = null;
    private ArrayAdapter<String> arrayAdapter;
    private final static String KEY_USER_EMAIL = "uEmail";
    private final static String KEY_STU_EMAIL = "studentemail";
    private final static String KEY_SPINNER_DATA = "stdclass";
    private String spinnerData;
    private String StudentEmailID = null;
    private Bundle bundle = null;
    private FragmentManager fragmentManager = null;
    private FragmentTransaction fragmentTransaction = null;
    private Parse_student_class parseStudentBatchClass = null;
    private Parse_student_batch parseStudentBatch = null;
    private int count = 0;

    public ViewStudentRecord() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //spinnerClass();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_student_rec, container, false);

        studentListView = view.findViewById(R.id.ListViewStd);
        classSpinner = view.findViewById(R.id.ClassSpinner);
        batchSpinner = view.findViewById(R.id.BatchSpinner);
        dialog = new ProgressDialog(getContext());

        studentList.clear();
        adapter = new Custom_Student_View(getContext(), studentList);

        getDataOfStudent();
        spinnerClass();
        registerForContextMenu(studentListView);
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                studentList.clear();
                adapter = new Custom_Student_View(getContext(), studentList);
                final SharedPreferences share = PreferenceManager.getDefaultSharedPreferences(getContext());
                spinnerData = String.valueOf(classSpinner.getItemAtPosition(i));
                StringRequest request = new StringRequest(Request.Method.POST, URL.DOWNLOAD_URL_FOR_BATCH_SPINNER, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseStudentBatch = new Parse_student_batch(response);
                        parseStudentBatch.getDataFromJson();
                        Log.i("Batch response:", response);
                        if (parseStudentBatch.sBatch != null) {
                            arrayAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, parseStudentBatch.sBatch);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            batchSpinner.setAdapter(arrayAdapter);
                        }
                        selectStudentForClassSpinner(spinnerData);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new Hashtable<String, String>();
                        if (!spinnerData.equals("Class")) {
                            params.put(KEY_SPINNER_DATA, spinnerData);
                        } else {
                            params.put(KEY_SPINNER_DATA, "");
                        }
                        params.put(KEY_USER_EMAIL, share.getString(Key.KEY_USER_EMAIL, ""));
                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(request);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        batchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    private void selectStudentForClassSpinner(final String ClassData) {
        studentList.clear();
        adapter = new Custom_Student_View(getContext(), studentList);
        final SharedPreferences share = PreferenceManager.getDefaultSharedPreferences(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, URL.DOWNLOAD_URL_VIEW_STUDENT_FROM_CLASSSPINNER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                json_student = new Parse_Student(response);
                json_student.getDataFromJson();
                if (json_student.sEmail != null) {
                    for (int i = 0; i < json_student.sEmail.length; i++) {
                        checkedStudent[i] = false;
                        student = new Student(json_student.sName[i], json_student.sNumber[i], json_student.sClass[i], json_student.sBatch[i], json_student.sImage[i], Integer.parseInt(json_student.sId[i]), checkedStudent[i], json_student.sEmail[i]);
                        studentList.add(student);
                    }
                    adapter.notifyDataSetChanged();
                    studentListView.setAdapter(adapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                if (!spinnerData.equals("Class")) {
                    params.put(KEY_SPINNER_DATA, ClassData);
                } else {
                    params.put(KEY_SPINNER_DATA, "");
                }
                params.put(KEY_USER_EMAIL, share.getString(Key.KEY_USER_EMAIL, ""));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }

    private void spinnerClass() {

        final SharedPreferences share = PreferenceManager.getDefaultSharedPreferences(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, URL.DOWNLOAD_URL_FOR_SPINNER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseStudentBatchClass = new Parse_student_class(response);
                parseStudentBatchClass.getDataFromJson();
                Log.i("response: ", String.valueOf(response));
                if (parseStudentBatchClass.sClass != null) {
                    Log.i("spinner class: ", String.valueOf(parseStudentBatchClass.sClass));
                    arrayAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, parseStudentBatchClass.sClass);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    classSpinner.setAdapter(arrayAdapter);

                } else
                    Toast.makeText(getContext(), "Something wrong to load class", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put(KEY_USER_EMAIL, share.getString(Key.KEY_USER_EMAIL, ""));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.ListViewStd) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle(studentList.get(info.position).getName());
            String[] menuItem = getResources().getStringArray(R.array.menu);
            for (int i = 0; i < menuItem.length; i++) {
                menu.add(Menu.NONE, i, i, menuItem[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItem = getResources().getStringArray(R.array.menu);
        String menuItemName = menuItem[menuItemIndex];
        String listItemName = studentList.get(info.position).getEmail();
        StudentEmailID = listItemName;
        //Toast.makeText(getContext(), String.valueOf(menuItemName) +" "+listItemName, Toast.LENGTH_SHORT).show();
        if (menuItemName.equals("Update")) {
            fragmentManager = getFragmentManager();
            bundle = new Bundle();
            bundle.putString("Stu_Email", listItemName);
            UpdateStudent update_student = new UpdateStudent();
            update_student.setArguments(bundle);
            count = fragmentManager.getBackStackEntryCount();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contentPanel, update_student).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                    addToBackStack(String.valueOf(count)).commit();
        } else if (menuItemName.equals("Delete")) {
            deleteDataOfStu();
            //adapter.notifyDataSetChanged();
        }
        return true;
    }

    private void deleteDataOfStu() {
        dialog.setTitle("delete student");
        final SharedPreferences share = PreferenceManager.getDefaultSharedPreferences(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, URL.UPLOAD_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Delete Successful", Toast.LENGTH_LONG).show();
                studentList.clear();
                adapter = new Custom_Student_View(getContext(), studentList);
                getDataOfStudent();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Delete Unsuccessful", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put(KEY_STU_EMAIL, StudentEmailID);
                params.put(KEY_USER_EMAIL, share.getString(Key.KEY_USER_EMAIL, ""));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }

    private void getDataOfStudent() {
        dialog.setMessage("please wait");
        dialog.setCancelable(false);
        dialog.setIndeterminate(false);
        dialog.show();
        final SharedPreferences share = PreferenceManager.getDefaultSharedPreferences(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, URL.DOWNLOAD_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                json_student = new Parse_Student(response);
                json_student.getDataFromJson();
                checkedStudent = new boolean[response.length()];
                if (json_student.sEmail != null) {

                    for (int i = 0; i < json_student.sEmail.length; i++) {
                        checkedStudent[i] = false;
                        student = new Student(json_student.sName[i], json_student.sNumber[i], json_student.sClass[i], json_student.sBatch[i], json_student.sImage[i], Integer.parseInt(json_student.sId[i]), checkedStudent[i], json_student.sEmail[i]);
                        studentList.add(student);
                    }

                    dialog.dismiss();
                    adapter.notifyDataSetChanged();
                    studentListView.setAdapter(adapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(getContext(), "No Student Details exist", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put(KEY_USER_EMAIL, share.getString(Key.KEY_USER_EMAIL, ""));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}