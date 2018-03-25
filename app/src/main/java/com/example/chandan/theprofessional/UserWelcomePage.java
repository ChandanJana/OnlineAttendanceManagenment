package com.example.chandan.theprofessional;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chandan.theprofessional.Key.Key;
import com.squareup.picasso.Picasso;
public class UserWelcomePage extends Fragment {
    private FloatingActionButton fab1,fab2,fab3;
    private Animation f_open,f_close,f_forward,f_backward;
    boolean ButtonIsck=false;
    TextView username,usermail,userphno;
    ImageView userpic;
    int cout=0;
    public UserWelcomePage() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_user_welcome_page, container, false);
        fab1=view.findViewById(R.id.floatingActionButton1);
        fab2=view.findViewById(R.id.floatingActionButton2);
        fab3=view.findViewById(R.id.floatingActionButton3);
        f_open= AnimationUtils.loadAnimation(getContext(),R.anim.file_open);
        f_close=AnimationUtils.loadAnimation(getContext(),R.anim.file_close);
        f_forward=AnimationUtils.loadAnimation(getContext(),R.anim.file_forward);
        f_backward=AnimationUtils.loadAnimation(getContext(),R.anim.file_backward);
        username=view.findViewById(R.id.User_name);
        usermail=view.findViewById(R.id.User_email);
        userphno=view.findViewById(R.id.User_Ph);
        userpic=view.findViewById(R.id.ImageView);
        SharedPreferences share = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = share.edit();
        String name = share.getString(Key.KEY_USER_NAME, "");
        String email = share.getString(Key.KEY_USER_EMAIL,"");
        String phno= share.getString(Key.KEY_USER_PHNO,"");
        username.setText(name);
        usermail.setText(email);
        userphno.setText(phno);
        if (share.getString(Key.KEY_USER_IMAGE,"").isEmpty())
        {
            Picasso.with(getContext())
                    .load(R.drawable.user)
                    .placeholder(R.drawable.user)
                    .error(R.drawable.user)
                    .into(userpic);
        }else {
            Picasso.with(getContext())
                    .load(share.getString(Key.KEY_USER_IMAGE,""))
                    .into(userpic);
        }
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ButtonIsck)
                {
                    fab2.startAnimation(f_close);
                    fab3.startAnimation(f_close);
                    fab1.startAnimation(f_backward);
                    fab2.setClickable(false);
                    fab3.setClickable(false);
                    ButtonIsck = false;
                }
                else {
                    fab2.startAnimation(f_open);
                    fab3.startAnimation(f_open);
                    fab1.startAnimation(f_forward);
                    fab2.setClickable(true);
                    fab2.setOnClickListener(new View.OnClickListener() {
                        Fragment fragment_fab2=null;
                        @Override
                        public void onClick(View view) {
                            fragment_fab2=new ViewStudentRecord();
                            if (fragment_fab2!=null)
                            {
                                FragmentManager manager=getFragmentManager();
                                cout=manager.getBackStackEntryCount();
                                FragmentTransaction transaction=manager.beginTransaction();
                                transaction.replace(R.id.contentPanel,fragment_fab2).setTransition(FragmentTransaction
                                        .TRANSIT_ENTER_MASK).addToBackStack(String.valueOf(cout)).commit();
                            }
                        }
                    });
                    fab3.setClickable(true);
                    fab3.setOnClickListener(new View.OnClickListener() {
                        Fragment fragment_fab3=null;
                        @Override
                        public void onClick(View view) {
                            fragment_fab3 = new AddStudentRecord();
                            if(fragment_fab3!=null)
                            {
                                FragmentManager manager=getFragmentManager();
                                cout=manager.getBackStackEntryCount();
                                FragmentTransaction transaction=manager.beginTransaction();
                                transaction.replace(R.id.contentPanel,fragment_fab3).setTransition(FragmentTransaction
                                        .TRANSIT_ENTER_MASK).addToBackStack(String.valueOf(cout)).commit();
                            }
                        }
                    });
                    ButtonIsck = true;
                }
            }
        });
        return view;
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