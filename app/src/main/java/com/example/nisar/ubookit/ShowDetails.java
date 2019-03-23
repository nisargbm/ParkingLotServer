//package com.example.nisar.ubookit;
//
///**
// * Created by nisar on 24-09-2017.
// */
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ProgressBar;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
///**
// * Created by nisar on 01-09-2017.
// */
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.design.widget.CoordinatorLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.app.AppCompatActivity;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//
//public class AddVehicle extends Fragment {
//    private static final String FIREBASE_URL="https://taxi-booking-69de8.firebaseio.com/";
//    private EditText inputVehicleNumber, inputPassword,inputVehicleName,inputAddress,inputMobile;
//    private Button btnSignIn, btnSignUp, btnResetPassword;
//    private FirebaseAuth auth;
//    private DatabaseReference mDatabase;
//    private TextView detailsTextView;
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.activity_show_details,container,false);
//    }
//
//    @Override
//    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        getActivity().setTitle("AddVehicle activity");
//        auth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(FIREBASE_URL);
//        //btnSignIn = (Button) view.findViewById(R.id.sign_in_button);
//        detailsTextView = (TextView) view.findViewById(R.id.details_textview);
//        btnSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int selectedId=radioGroup.getCheckedRadioButtonId();
//                radioButton=(RadioButton)view.findViewById(selectedId);
//                Toast.makeText(getActivity(),radioButton.getText(),Toast.LENGTH_SHORT).show();
//                final String vehicleType = radioButton.getText().toString();
//                final String number = inputVehicleNumber.getText().toString().trim();
//                //final String password = inputPassword.getText().toString().trim();
//                final String name =inputVehicleName.getText().toString().trim();
//                //final String mobile =inputMobile.getText().toString().trim();
//                //final String address=inputAddress.getText().toString().trim();
//
//                if (TextUtils.isEmpty(number) || !number.matches("[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}")) {
//                    Toast.makeText(getContext(), "Enter proper vehicle number!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (TextUtils.isEmpty(name) ) {
//                    Toast.makeText(getContext(), "Enter Vehicle Name !", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                progressBar.setVisibility(View.VISIBLE);
//                if(auth.getCurrentUser()!=null) {
//                    String uid = auth.getCurrentUser().getUid();
//                    //String key = mDatabase.child("users").child(uid).getKey();
//                    mDatabase.child("users").child(uid).child(number).child("Vehicle Number").setValue(number);
//                    mDatabase.child("users").child(uid).child(number).child("Vehicle Name").setValue(name);
//                    mDatabase.child("users").child(uid).child(number).child("Vehicle Type").setValue(vehicleType);
//                    FragmentTransaction ft =  getFragmentManager().beginTransaction();
//                    ft.replace(R.id.addVehicle,new QRCodeActivity());
//                    ft.commit();
//                }
//                else{
//                    startActivity(new Intent(getContext(),Login.class));
//                }
//
//            }
//        });
//    }
//}
