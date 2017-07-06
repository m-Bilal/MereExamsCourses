package com.mereexams.mereexamscourses.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Bilal on 06-Jul-17.
 */

public class DisciplineGroupCategory {

    @SerializedName("id")
    private int id;

    @SerializedName("discipline_group_category")
    private String disciplineGroupCategory;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisciplineGroupCategory() {
        return disciplineGroupCategory;
    }

    public void setDisciplineGroupCategory(String disciplineGroupCategory) {
        this.disciplineGroupCategory = disciplineGroupCategory;
    }

    public class DisciplineGroupCategoryResponse {
        @SerializedName("broad_discipline_group_category_all")
        private List<DisciplineGroupCategory> disciplineGroupCategories;

        @SerializedName("status")
        private String success;

        public List<DisciplineGroupCategory> getDisciplineGroupCategories() {
            return disciplineGroupCategories;
        }

        public void setDisciplineGroupCategories(List<DisciplineGroupCategory> disciplineGroupCategories) {
            this.disciplineGroupCategories = disciplineGroupCategories;
        }

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }
    }
}
