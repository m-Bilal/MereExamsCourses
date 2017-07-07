package com.mereexams.mereexamscourses;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mereexams.mereexamscourses.Models.DisciplineGroup;
import com.mereexams.mereexamscourses.Models.DisciplineGroupCategory;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class CoursesActivity extends AppCompatActivity {

    public static final String TAG = "CoursesActivity";
    List<DisciplineGroup> disciplineGroups;
    List<DisciplineGroupCategory> disciplineGroupCategories;
    private Button buttonSync, buttonUpdate;
    private RecyclerView recyclerView;
    private TextView textViewCourses, textViewCategories, textViewRepeated;
    private MyRecyclerViewAdapter adapter;

    private int repeated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        buttonSync = (Button) findViewById(R.id.button_sync);
        buttonUpdate = (Button) findViewById(R.id.button_update);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_courses_activity);
        textViewCategories = (TextView) findViewById(R.id.textview_categories);
        textViewCourses = (TextView) findViewById(R.id.textview_courses);
        textViewRepeated = (TextView) findViewById(R.id.textview_repeated);

        disciplineGroupCategories = new ArrayList<>();
        disciplineGroups = new ArrayList<>();

        disciplineGroups = DisciplineGroup.retrieveOrSync(this);
        disciplineGroupCategories = DisciplineGroupCategory.retrieveOrSync(this);

        textViewCourses.setText(disciplineGroups.size() + "");
        textViewCategories.setText(disciplineGroupCategories.size() + "");
        checkRepeatedCourses();

        adapter = new MyRecyclerViewAdapter(disciplineGroupCategories);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        addActionListeners();
    }

    void addActionListeners() {
        buttonSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syncDisciplineGroupCategories();
                syncDisciplineGroups();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewCourses.setText(disciplineGroups.size() + "");
                textViewCategories.setText(disciplineGroupCategories.size() + "");
                checkRepeatedCourses();

                adapter = new MyRecyclerViewAdapter(disciplineGroupCategories);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
            }
        });
    }

    void checkRepeatedCourses() {
        repeated = 0;
        if(disciplineGroups.size() > 0) {
            for(int i = 0; i < disciplineGroups.size(); i++){
                for (int j = i + 1; j < disciplineGroups.size(); j++) {
                    if(disciplineGroups.get(i).getDisciplineGroup().equalsIgnoreCase(disciplineGroups.get(j).getDisciplineGroup())) {
                        i++;
                    }
                }
            }
        }
        textViewRepeated.setText(repeated + "");
    }

    List<DisciplineGroup> getDisciplineGroups(DisciplineGroupCategory category) {
        Realm realm = Realm.getInstance(MainActivity.config);
        List<DisciplineGroup> groups = realm.where(DisciplineGroup.class).equalTo("categoryId", category.getId()).findAll();
        return groups;
    }

    public void syncDisciplineGroups() {
        DisciplineGroup.sync(this);
    }

    public void syncDisciplineGroupCategories() {
        DisciplineGroupCategory.sync(this);
    }


    // Recycler View Adapter
    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
        List<DisciplineGroupCategory> categories;

        public MyRecyclerViewAdapter(List<DisciplineGroupCategory> categories) {
            this.categories = categories;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_layout_activity_courses, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.textViewCategoryTitle.setText(categories.get(position).getDisciplineGroupCategory());
            holder.textViewCategoryId.setText(categories.get(position).getId() + "");
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            List<DisciplineGroup> groups = getDisciplineGroups(categories.get(position));
            holder.textViewCourses.setText(groups.size() + "");
        }

        @Override
        public int getItemCount() {
            if(categories == null) return 0;
            else return categories.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView textViewCategoryId, textViewCategoryTitle, textViewCourses;
            public LinearLayout container;

            public MyViewHolder(View view) {
                super(view);

                textViewCategoryId = (TextView) view.findViewById(R.id.recyclerview_category_id);
                textViewCategoryTitle = (TextView) view.findViewById(R.id.recyclerview_category_title);
                textViewCourses = (TextView) view.findViewById(R.id.recyclerview_category_courses);
                container = (LinearLayout) view.findViewById(R.id.linearlayout_courses_activity_reyclerview_item_container);
            }
        }
    }
}
