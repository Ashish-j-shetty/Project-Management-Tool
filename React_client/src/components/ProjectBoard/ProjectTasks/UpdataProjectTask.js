import React, { Component } from "react";
import { connect } from "react-redux";
import { Link } from "react-router-dom";
import {
  getProjectTask,
  updateProjectTask
} from "../../../actions/backlogActions";
import PropTypes from "prop-types";
import classnames from "classnames";

class UpdataProjectTask extends Component {
  constructor() {
    super();

    this.state = {
      projectSequence: "",
      acceptanceCriteria: "",
      status: "",
      priority: "",
      dueDate: "",
      projectIdentifier: "",
      summary: "",
      created_At: "",
      id: "",
      errors: {}
    };
  }

  static getDerivedStateFromProps(nextProp, state) {
    if (nextProp.errors !== state.errors) {
      return { errors: nextProp.errors };
    }

    if (nextProp.project_task.id !== state.id) {
      const {
        projectSequence,
        acceptanceCriteria,
        status,
        priority,
        dueDate,
        projectIdentifier,
        summary,
        created_At,
        id
      } = nextProp.project_task;

      return {
        projectSequence,
        acceptanceCriteria,
        status,
        priority,
        dueDate,
        projectIdentifier,
        summary,
        created_At,
        id
      };
    }
  }

  componentDidMount() {
    const { backlog_id, id } = this.props.match.params;
    this.props.getProjectTask(backlog_id, id, this.props.history);
  }

  onChange = evt => {
    this.setState({
      [evt.target.name]: evt.target.value
    });
  };
  onSubmit = evt => {
    evt.preventDefault();

    const updatedProjectTask = {
      id: this.state.id,
      projectSequence: this.state.projectSequence,
      acceptanceCriteria: this.state.acceptanceCriteria,
      status: this.state.status,
      priority: this.state.priority,
      dueDate: this.state.dueDate,
      projectIdentifier: this.state.projectIdentifier,
      summary: this.state.summary,
      created_At: this.state.created_At
    };

    this.props.updateProjectTask(
      this.state.projectIdentifier,
      this.state.projectSequence,
      updatedProjectTask,
      this.props.history
    );
  };

  render() {
    const { errors } = this.state;
    return (
      <div className="add-PBI">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <Link
                to={`/projectBoard/:${this.state.projectIdentifier}`}
                className="btn btn-light"
              >
                Back to Project Board
              </Link>
              <h4 className="display-4 text-center">Update Project Task</h4>
              <p className="lead text-center">
                Project Name: {this.state.projectIdentifier} | Project Code:
                {this.state.projectSequence}
              </p>
              <form onSubmit={this.onSubmit}>
                <div className="form-group">
                  <input
                    type="text"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.Summary
                    })}
                    name="summary"
                    value={this.state.summary}
                    onChange={this.onChange}
                    placeholder="Project Task summary"
                  />
                  {errors.Summary && (
                    <div className="invalid-feedback">{errors.Summary}</div>
                  )}
                </div>
                <div className="form-group">
                  <textarea
                    className="form-control form-control-lg"
                    placeholder="Acceptance Criteria"
                    name="acceptanceCriteria"
                    value={this.state.acceptanceCriteria}
                    onChange={this.onChange}
                  ></textarea>
                </div>
                <h6>Due Date</h6>
                <div className="form-group">
                  <input
                    type="date"
                    className="form-control form-control-lg"
                    name="dueDate"
                    value={this.state.dueDate}
                    onChange={this.onChange}
                  />
                </div>
                <div className="form-group">
                  <select
                    className="form-control form-control-lg"
                    name="priority"
                    value={this.state.priority}
                    onChange={this.onChange}
                  >
                    <option value={0}>Select Priority</option>
                    <option value={1}>High</option>
                    <option value={2}>Medium</option>
                    <option value={3}>Low</option>
                  </select>
                </div>

                <div className="form-group">
                  <select
                    className="form-control form-control-lg"
                    name="status"
                    value={this.state.status}
                    onChange={this.onChange}
                  >
                    <option value="">Select Status</option>
                    <option value="TO_DO">TO DO</option>
                    <option value="IN_PROGRESS">IN PROGRESS</option>
                    <option value="DONE">DONE</option>
                  </select>
                </div>

                <input
                  type="submit"
                  className="btn btn-primary btn-block mt-4"
                />
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

UpdataProjectTask.propTypes = {
  getProjectTask: PropTypes.func.isRequired,
  project_task: PropTypes.object.isRequired,
  updateProjectTask: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  project_task: state.backlog.project_task,
  errors: state.error
});

export default connect(
  mapStateToProps,
  { getProjectTask, updateProjectTask }
)(UpdataProjectTask);
