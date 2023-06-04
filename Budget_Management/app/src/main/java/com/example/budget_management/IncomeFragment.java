package com.example.budget_management;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.budget_management.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class IncomeFragment extends Fragment {
    //Firebase database
    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeDatabase;
    //Recyclerview
    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myview = inflater.inflate(R.layout.fragment_income, container, false);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();
        mIncomeDatabase= FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);
        recyclerView=myview.findViewById(R.id.recycle_id_income);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        return myview;
    }
    @Override
    public void onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<Data,MyViewHolder>adapter=new FirebaseRecyclerAdapter<Data, MyViewHolder>
            (
                Data.class,
                R.layout.income_recycler_data,
                MyViewHolder.class,
                mIncomeDatabase
            ) {
            @Override
            protected void populateViewHolder (MyViewHolder viewHolder, Data model, int position) {
                viewHolder.setType(model.getType());
                viewHolder.setNote(model.getNote());
                viewHolder.setDate(model.getDate());
                viewHolder.setAmmount(model.getAmount());
            }

        };
        recyclerView.setAdapter(adapter);
    }
    public  static class MyViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public  MyViewHolder(View itemView)
        {
            super(itemView);
            mView=itemView;
        }
        private void setType(String type){
            TextView mType=mView.findViewById(R.id.type_txt_income);
            mType.setText(type);
        }
        private  void setNote(String note){
            TextView mNote=mView.findViewById(R.id.note_txt_income);
            mNote.setText(note);
        }
        private  void setDate(String date){
            TextView mDate=mView.findViewById(R.id.date_txt_income);
            mDate.setText(date);
        }
        private  void setAmmount(int ammount){
            TextView mAmmount=mView.findViewById(R.id.ammount_txt_income);
            String stammount=String.valueOf(ammount);
            mAmmount.setText(stammount);
        }
    }

}