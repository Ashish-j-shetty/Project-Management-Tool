package com.fullstack.pmtool.web;

import com.fullstack.pmtool.domain.Project;
import com.fullstack.pmtool.services.MapValidationErrorService;
import com.fullstack.pmtool.services.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private MapValidationErrorService mapValidationErrorService;


    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project , BindingResult result, Principal principal){

        ResponseEntity<?> errorMap=   mapValidationErrorService.mapValidationService(result);
        if(errorMap!=null)
            return errorMap;
        projectService.saveOrUpdateProject(project,principal.getName());
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId){
        Project project =projectService.findProjectByIdentifier(projectId);
        return  new ResponseEntity<Project>(project,HttpStatus.OK);
    }
    @GetMapping("/allProjects")
    public Iterable<Project> findAllProjects( Principal principal){
        return  projectService.findAllProjects(principal.getName());
    }
    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProjectById(@PathVariable String projectId){
        projectService.deleteProjectById(projectId);
        return new ResponseEntity<String>("Project '"+projectId+"'is deleted successfully",HttpStatus.OK);
    }
}
