package com.example.chandan.theprofessional.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chandan.theprofessional.Model.Student;
import com.example.chandan.theprofessional.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Chandan on 20-09-2017.
 */
public class Custom_Student_View extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Student> studentList;
    private CheckBox checkBox;
    private ImageView studentImage;
    private TextView name1, mob1, class1, batch1;
    private Student studentObb;

    public Custom_Student_View(Context context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
    }

    @Override
    public int getCount() {
        return studentList.size();
    }

    @Override
    public Object getItem(int i) {
        return studentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
            view = inflater.inflate(R.layout.custom_student_listview, null);
        studentObb = getStudent(i);
        studentObb = new Student(studentObb.getName(), studentObb.getMob(), studentObb.getClass1(),
                studentObb.getBatch(), studentObb.getPicpath(), studentObb.getId(),
                studentObb.getChkItem(), studentObb.getEmail());
        name1 = view.findViewById(R.id.Name);
        mob1 = view.findViewById(R.id.MobNo);
        class1 = view.findViewById(R.id.TextClass);
        batch1 = view.findViewById(R.id.TextBatch);
        studentImage = view.findViewById(R.id.Dp);
        checkBox = view.findViewById(R.id.Attendance);
        checkBox.setTag(i);
        checkBox.setChecked(studentList.get(i).getChkItem());
        name1.setText(studentList.get(i).getName());
        mob1.setText(studentList.get(i).getMob());
        class1.setText(studentList.get(i).getClass1());
        batch1.setText(studentList.get(i).getBatch());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });
        if (studentList.get(i).getPicpath().isEmpty()) {
            Picasso.with(context)
                    .load(R.drawable.user)
                    .placeholder(R.drawable.user)
                    .error(R.drawable.user)
                    .into(studentImage);
        } else {
            Picasso.with(context)
                    .load(studentList.get(i).getPicpath())
                    .into(studentImage);
        }
        return view;
    }

    Student getStudent(int i) {
        return ((Student) getItem(i));
    }
}