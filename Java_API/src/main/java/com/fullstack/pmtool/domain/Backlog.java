package com.fullstack.pmtool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.mapping.FetchProfile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Backlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private Integer PTsequence= 0;
    private String projectIdentifier;

    //one to one with project i.e one project one backlog
    @OneToOne(fetch = FetchType.EAGER )
    @JoinColumn(name="project_id", nullable = false)
    @JsonIgnore
    private Project project;
    // one to many with project task
    @OneToMany(cascade = CascadeType.REFRESH ,fetch = FetchType.EAGER,mappedBy = "backlog" , orphanRemoval = true)
     private List<ProjectTask> projectTasks =new ArrayList<>();

    public List<ProjectTask> getProjectTasks() {
        return projectTasks;
    }

    public void setProjectTasks(List<ProjectTask> projectTasks) {
        this.projectTasks = projectTasks;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Backlog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPTsequence() {
        return PTsequence;
    }

    public void setPTsequence(Integer PTsequence) {
        this.PTsequence = PTsequence;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }
}
