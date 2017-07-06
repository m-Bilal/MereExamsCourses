package com.mereexams.mereexamscourses.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Bilal on 06-Jul-17.
 */

public class DisciplineGroup {

    @SerializedName("id")
    private int id;

    @SerializedName("category_id")
    private int categoryId;

    @SerializedName("discipline_group")
    private String disciplineGroup;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDisciplineGroup() {
        return disciplineGroup;
    }

    public void setDisciplineGroup(String disciplineGroup) {
        this.disciplineGroup = disciplineGroup;
    }

    public class DisciplineGroupResponse {

        @SerializedName("broad_discipline_group_all")
        private List<DisciplineGroup> disciplineGroups;

        @SerializedName("status")
        private String status;

        public List<DisciplineGroup> getDisciplineGroups() {
            return disciplineGroups;
        }

        public void setDisciplineGroups(List<DisciplineGroup> disciplineGroups) {
            this.disciplineGroups = disciplineGroups;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
