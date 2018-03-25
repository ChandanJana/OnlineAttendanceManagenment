package com.example.chandan.theprofessional;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.chandan.theprofessional.Key.Key;
import com.example.chandan.theprofessional.Parse.Parse_Student;
import com.squareup.picasso.Picasso;

import java.util.Hashtable;
import java.util.Map;

public class UpdateStudent extends Fragment {
    EditText stu_name, stu_class, stu_batch, stu_mob, stu_fees, stu_email, stu_addr;
    ImageView edit_name, edit_class, edit_batch, edit_mob, edit_fees, edit_addr, stu_img;
    Button stu_update;
    private final static String DOWNLOAD_URL = "http://192.168.137.1/studentapi/eachstudentinformation.php";
    Bundle bundle = null;
    String stuemail = null;
    private Parse_Student json_student = null;
    String update_name, update_class, update_batch, update_mob, update_fees, update_email, update_addr;
    private final static String UPLOAD_URL = "http://192.168.137.1/studentapi/eachstudentinformationupdate.php";
    private final static String KEY_STU_NAME = "name";
    private final static String KEY_STU_CLASS = "class";
    private final static String KEY_STU_MOBILE = "mobile";
    private final static String KEY_STU_FEE = "fee";
    private final static String KEY_STU_ADD = "add";
    private final static String KEY_STU_BATCH = "batch";
    private final static String KEY_STU_EMAIL = "studentemail";
    private final static String KEY_USER_EMAIL = "Uemail1";

    public UpdateStudent() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_student, container, false);
        stu_name = view.findViewById(R.id.StuName);
        stu_class = view.findViewById(R.id.StuClass);
        stu_batch = view.findViewById(R.id.StuBatch);
        stu_mob = view.findViewById(R.id.StuMob);
        stu_fees = view.findViewById(R.id.StuFees);
        stu_email = view.findViewById(R.id.StuEmail);
        stu_addr = view.findViewById(R.id.StuAddr);
        stu_img = view.findViewById(R.id.StuImage);
        edit_name = view.findViewById(R.id.EditName);
        edit_class = view.findViewById(R.id.EditClass);
        edit_batch = view.findViewById(R.id.EditBatch);
        edit_mob = view.findViewById(R.id.EditMob);
        edit_fees = view.findViewById(R.id.EditFees);
        edit_addr = view.findViewById(R.id.EditAddr);
        stu_update = view.findViewById(R.id.EditUpdate);
        bundle = getArguments();
        stuemail = bundle.getString("Stu_Email");
        getStudentData();
        edit_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEditableMode(stu_name);
                stu_name.setCursorVisible(true);
            }
        });
        edit_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEditableMode(stu_class);
                stu_class.setCursorVisible(true);
            }
        });
        edit_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEditableMode(stu_batch);
                stu_batch.setCursorVisible(true);
            }
        });
        edit_mob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEditableMode(stu_mob);
                stu_mob.setCursorVisible(true);
            }
        });
        edit_fees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEditableMode(stu_fees);
                stu_fees.setCursorVisible(true);
            }
        });
        edit_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEditableMode(stu_addr);
                stu_addr.setCursorVisible(true);
            }
        });
        stu_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EachStudentUpdate();
            }
        });
        return view;
    }

    private void EachStudentUpdate() {
        update_name = stu_name.getText().toString().trim();
        update_class = stu_class.getText().toString().trim();
        update_batch = stu_batch.getText().toString().trim();
        update_mob = stu_mob.getText().toString().trim();
        update_fees = stu_fees.getText().toString().trim();
        update_addr = stu_addr.getText().toString().trim();
        update_email = stu_email.getText().toString().trim();
        if (update_name.equals("") || update_name.equals(" ") || update_class.equals("") || update_class.equals(" ") || update_batch.equals("") || update_batch.equals(" ")
                || update_mob.equals("") || update_mob.equals(" ") || update_fees.equals("") || update_fees.equals(" ") || update_addr.equals("") || update_addr.equals(" ")) {
            Toast.makeText(getContext(), "Enter required fields", Toast.LENGTH_LONG).show();
        } else {
            if (update_mob.length() < 10 || update_mob.length() > 10) {
                Toast.makeText(getContext(), "Invalid mobile number", Toast.LENGTH_LONG).show();
            } else {
                final ProgressDialog dialog = new ProgressDialog(getContext());
                dialog.setMessage("please wait");
                dialog.setCancelable(false);
                dialog.setIndeterminate(false);
                dialog.show();
                final SharedPreferences share = PreferenceManager.getDefaultSharedPreferences(getContext());
                StringRequest req = new StringRequest(Request.Method.POST, UPLOAD_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Toast.makeText(getContext(), "Update Successful", Toast.LENGTH_LONG).show();
                        getFragmentManager().popBackStackImmediate();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(getContext(), "Update Unsuccessful", Toast.LENGTH_LONG).show();
                    }
                }) {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new Hashtable<String, String>();
                        params.put(KEY_STU_NAME, update_name);
                        params.put(KEY_STU_EMAIL, update_email);
                        params.put(KEY_STU_ADD, update_addr);
                        params.put(KEY_STU_MOBILE, update_mob);
                        params.put(KEY_STU_CLASS, update_class);
                        params.put(KEY_STU_BATCH, update_batch);
                        params.put(KEY_STU_FEE, update_fees);
                        params.put(KEY_USER_EMAIL, share.getString(Key.KEY_USER_EMAIL, ""));
                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(req);
            }
        }
    }


    private void setEditableMode(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        stu_update.setVisibility(View.VISIBLE);
    }

    private void getStudentData() {
        final SharedPreferences share = PreferenceManager.getDefaultSharedPreferences(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, DOWNLOAD_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                json_student = new Parse_Student(response);
                json_student.getDataFromJson();
                stu_name.setText(json_student.sName[0]);
                stu_class.setText(json_student.sClass[0]);
                stu_batch.setText(json_student.sBatch[0]);
                stu_mob.setText(json_student.sNumber[0]);
                stu_addr.setText(json_student.sAdd[0]);
                stu_email.setText(json_student.sEmail[0]);
                if (json_student.sImage[0].equals(null)) {
                    Picasso.with(getContext())
                            .load(R.drawable.user)
                            .placeholder(R.drawable.user)
                            .error(R.drawable.user)
                            .into(stu_img);
                } else {
                    Picasso.with(getContext())
                            .load(json_student.sImage[0])
                            .into(stu_img);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put(KEY_STU_EMAIL, stuemail);
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
