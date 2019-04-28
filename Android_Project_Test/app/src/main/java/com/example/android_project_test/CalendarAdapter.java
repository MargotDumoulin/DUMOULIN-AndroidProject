package com.example.android_project_test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder>{

    private Context context;
    private List<Day> listDays;
    private CalendarDbHelper mDatabase;
    final private OnListItemClickListener mOnListItemClickListener;
    private DatabaseReference db;


    CalendarAdapter(Context context, List<Day> listDays, OnListItemClickListener listener){
        this.context = context;
        this.listDays = listDays;
        db = FirebaseDatabase.getInstance().getReference();
        mDatabase = new CalendarDbHelper(db);
        mOnListItemClickListener = listener;

    }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.rv_calendar_activity, parent, false);
            return new ViewHolder(view);
        }

        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            final Day singleDay = listDays.get(position);
            viewHolder.dayName.setText(singleDay.getDayName() + " " + singleDay.getDayDate());
            viewHolder.dish1.setText(singleDay.getMeals().get(0).getDishName());
            viewHolder.dish2.setText(singleDay.getMeals().get(1).getDishName());
            viewHolder.dish3.setText(singleDay.getMeals().get(2).getDishName());




        }
        public int getItemCount() {
            return listDays.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            TextView dayName;
            TextView dish1;
            TextView dish2;
            TextView dish3;


            ViewHolder(View itemView) {
                super(itemView);
                dayName = itemView.findViewById(R.id.tv_day);
                dish1 = itemView.findViewById(R.id.tv_morning);
                dish2  = itemView.findViewById(R.id.tv_twelve);
                dish3 = itemView.findViewById(R.id.tv_evening);
                itemView.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {
                mOnListItemClickListener.onListItemClick(getAdapterPosition());
            }

        }

    public interface OnListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
}



