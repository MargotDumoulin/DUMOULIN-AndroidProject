package com.example.android_project_test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder>{

     private Context context;
     private List<Meal> listMeals;
     private MealDbHelper mDatabase;
    final private OnListItemClickListener mOnListItemClickListener;



        MealAdapter(Context context, List<Meal> listMeals, OnListItemClickListener listener) {
            this.context = context;
            this.listMeals = listMeals;
            mDatabase = new MealDbHelper(context);
            mOnListItemClickListener = listener;

        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.rv_meal_activity, parent, false);
            return new ViewHolder(view);

        }

        public void onBindViewHolder(@NonNull com.example.android_project_test.MealAdapter.ViewHolder viewHolder, int position) {
            final Meal singleProduct = listMeals.get(position);
            viewHolder.dishName.setText(singleProduct.getDishName());


        }

        public int getItemCount() {
            return listMeals.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener  {

            TextView dishName;


            ViewHolder(View itemView) {
                super(itemView);
                dishName = itemView.findViewById(R.id.tv_meal);
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




