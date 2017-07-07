package com.mereexams.mereexamscourses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mereexams.mereexamscourses.Models.DisciplineGroup;
import com.mereexams.mereexamscourses.Models.DisciplineGroupCategory;

import java.util.List;

public class IndividualCourseActivity extends AppCompatActivity {

    TextView textViewCategoryName, textViewCategoryId;
    RecyclerView recyclerView;

    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_course);

        textViewCategoryId = (TextView) findViewById(R.id.textview_category_id);
        textViewCategoryName = (TextView) findViewById(R.id.textview_category_name);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_individual_course_activity);

        textViewCategoryId.setText(getIntent().getExtras().getString(CoursesActivity.CATEGORY_ID));
        textViewCategoryName.setText(getIntent().getExtras().getString(CoursesActivity.CATEGORY_NAME));

        adapter = new MyRecyclerViewAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    // Recycler View Adapter
    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
        List<DisciplineGroup> groups;

        public MyRecyclerViewAdapter() {
            this.groups = CoursesActivity.getClickedDisciplineGroup();
            Log.d("Groups", this.groups.size() + "");
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_layout_activity_individual_course, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.textViewCourseName.setText(groups.get(position).getDisciplineGroup());
            holder.textViewCourseId.setText(groups.get(position).getId() + "");
        }

        @Override
        public int getItemCount() {
            if(groups == null) return 0;
            else return groups.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView textViewCourseName, textViewCourseId;

            public MyViewHolder(View view) {
                super(view);
                textViewCourseId = (TextView) view.findViewById(R.id.recyclerview_textview_course_id);
                textViewCourseName = (TextView) view.findViewById(R.id.recyclerview_textview_course_name);
            }
        }
    }
}
