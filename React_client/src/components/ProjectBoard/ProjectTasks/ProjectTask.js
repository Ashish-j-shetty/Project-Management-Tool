import React, { Component } from "react";
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import { deleteProjectTask } from "../../../actions/backlogActions";
import PropTypes from "prop-types";

class ProjectTask extends Component {
  delete(backlog_id, pt_id) {
    this.props.deleteProjectTask(backlog_id, pt_id);
  }

  render() {
    const { project_tasks } = this.props;

    let priorityString;
    let priorityClass;

    if (project_tasks.priority === 1) {
      priorityClass = "bg-danger text-light";
      priorityString = "HIGH";
    }
    if (project_tasks.priority === 2) {
      priorityClass = "bg-warning text-light";
      priorityString = "Medium";
    }
    if (project_tasks.priority === 3) {
      priorityClass = "bg-info text-light";
      priorityString = "Low";
    }

    return (
      <div className="card mb-1 bg-light">
        <div className={`card-header text-primary ${priorityClass}`}>
          ID: {project_tasks.projectSequence} -- Priority:{priorityString}
        </div>
        <div className="card-body bg-light">
          <h5 className="card-title">{project_tasks.summary}</h5>
          <p className="card-text text-truncate ">
            {project_tasks.acceptanceCriteria}
          </p>
          <Link
            to={`/updateProjectTask/${project_tasks.projectIdentifier}/${project_tasks.projectSequence}`}
            className="btn btn-primary"
          >
            View / Update
          </Link>

          <button
            className="btn btn-danger ml-4"
            onClick={this.delete.bind(
              this,
              project_tasks.projectIdentifier,
              project_tasks.projectSequence
            )}
          >
            Delete
          </button>
        </div>
      </div>
    );
  }
}

ProjectTask.propTypes = {
  deleteProjectTask: PropTypes.func.isRequired
};

export default connect(
  null,
  { deleteProjectTask }
)(ProjectTask);
