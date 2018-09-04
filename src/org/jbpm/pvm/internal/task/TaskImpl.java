/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jbpm.pvm.internal.task;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.jbpm.api.Execution;
import org.jbpm.api.JbpmException;
import org.jbpm.api.model.Event;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Swimlane;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.client.ClientExecution;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.history.HistoryEvent;
import org.jbpm.pvm.internal.history.events.TaskComplete;
import org.jbpm.pvm.internal.history.events.TaskDelete;
import org.jbpm.pvm.internal.history.events.TaskSkip;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.model.ScopeInstanceImpl;
import org.jbpm.pvm.internal.session.DbSession;
import org.jbpm.pvm.internal.util.EqualsUtil;
import org.jbpm.pvm.internal.util.Priority;

import com.zhiwei.credit.model.flow.ProcessRun;

/**
 * is one task instance that can be assigned to an actor (read: put in someone's
 * task list) and that can trigger the continuation of execution of the token
 * upon completion.
 * 
 * @author Tom Baeyens
 * @author Ronald van Kuijk
 */
public class TaskImpl extends ScopeInstanceImpl implements OpenTask, Assignable {

	private static final long serialVersionUID = 1L;

	protected boolean isNew;
	protected String name;
	protected String description;

	protected String assignee;
	protected Set<ParticipationImpl> participations = new HashSet<ParticipationImpl>();

	protected String formResourceName;
	protected Date createTime;
	protected Date duedate;
	protected Integer progress;
	protected boolean isSignalling;

	protected int priority = Priority.NORMAL;

	protected String taskDefinitionName;
	protected TaskDefinitionImpl taskDefinition;

	protected ExecutionImpl execution;
	protected ExecutionImpl processInstance;

	// local storage of the execution id such that it
	// can be lazily loaded when not needed.
	protected String executionId;

	protected String activityName;

	protected SwimlaneImpl swimlane;

	protected TaskImpl superTask;
	protected Set<TaskImpl> subTasks;

	protected Long executionDbid;
	protected Long superTaskDbid;

	protected ProcessRun processRun;

	public TaskImpl() {
		this.state = Task.STATE_OPEN;
	}

	// parent for variable lookup
	// ///////////////////////////////////////////////

	@Override
	public ScopeInstanceImpl getParentVariableScope() {
		return execution;
	}

	@Override
	public TaskImpl getTask() {
		return this;
	}

	// assignment
	// ///////////////////////////////////////////////////////////////

	public void take(String userId) {
		if (assignee != null) {
			throw new JbpmException("task already taken by " + this.assignee);
		}
		setAssignee(userId, true);
	}

	public void setAssignee(String userId) {
		setAssignee(userId, false);
	}

	public void setAssignee(String assignee, boolean propagateToSwimlane) {
		this.assignee = assignee;
		if (propagateToSwimlane) {
			propagateAssigneeToSwimlane();
		}
		if (execution != null) {
			execution.fire(Event.ASSIGN, execution.getActivity());
		}
	}

	protected void propagateAssigneeToSwimlane() {
		if (swimlane != null) {
			swimlane.setAssignee(assignee);
		}
	}

	// participations
	// /////////////////////////////////////////////////////////////

	// TODO: Why does it return the impl. not the interface?
	public Set<ParticipationImpl> getParticipations() {
		return participations;
	}

	public Set<ParticipationImpl> getAllParticipants() {
		Set<ParticipationImpl> allRoles = new HashSet<ParticipationImpl>();
		if (!participations.isEmpty()) {
			allRoles = new HashSet<ParticipationImpl>(participations);
		}
		if (swimlane != null) {
			allRoles.addAll(swimlane.getParticipations());
		}
		return allRoles;
	}

	public void addCandidateGroup(String groupId) {
		addParticipation(null, groupId, Participation.CANDIDATE);
	}

	public void addCandidateUser(String userId) {
		addParticipation(userId, null, Participation.CANDIDATE);
	}

	public Participation addParticipation(String userId, String groupId,
			String type) {
		return addParticipant(new ParticipationImpl(userId, groupId, type));
	}

	private Participation addParticipant(ParticipationImpl participation) {
		participation.setTask(this);
		participations.add(participation);
		return participation;
	}

	public void removeParticipant(ParticipationImpl participation) {
		if (participation == null) {
			throw new JbpmException("participant is null");
		}
		if (participations.remove(participation)) {
			participation.setTask(null);
		}
	}

	// completion
	// ///////////////////////////////////////////////////////////////

	public void complete() {
		complete(TaskConstants.NO_TASK_OUTCOME_SPECIFIED);
	}

	public void complete(String outcome) {
		historyTaskComplete(outcome);

		DbSession dbSession = EnvironmentImpl.getFromCurrent(DbSession.class,
				false);
		if (dbSession != null) {
			dbSession.delete(this);
		}

		if (isSignalling()) {
			ClientExecution execution = getExecution();
			execution.signal(outcome);
		}

		if (superTask != null) {
			superTask.subTaskComplete(this, outcome);
		}
	}

	protected void subTaskComplete(TaskImpl subTask, String outcome) {
	}

	public void delete(String reason) {
		historyTaskDelete(reason);
	}

	public void skip(String outcome) {
		if (outcome == null || outcome.equals("")) {
			outcome = TaskConstants.NO_TASK_OUTCOME_SPECIFIED;
		}

		historyTaskSkip(outcome);

		DbSession dbSession = EnvironmentImpl.getFromCurrent(DbSession.class,
				false);
		if (dbSession != null) {
			dbSession.delete(this);
		}
	}

	// state
	// ////////////////////////////////////////////////////////////////////

	public boolean isCompleted() {
		if (Task.STATE_OPEN.equals(state) || Task.STATE_SUSPENDED.equals(state)) {
			return false;
		}
		return true;
	}

	// subtasks
	// /////////////////////////////////////////////////////////////////

	public Set<Task> getSubTasks() {
		return subTasks != null ? Collections.<Task> unmodifiableSet(subTasks)
				: Collections.<Task> emptySet();
	}

	public TaskImpl createSubTask() {
		DbSession dbSession = EnvironmentImpl.getFromCurrent(DbSession.class);
		TaskImpl subTask = dbSession.createTask();
		if (subTasks == null) {
			subTasks = new HashSet<TaskImpl>();
		}
		addSubTask(subTask);
		return subTask;
	}

	public TaskImpl createSubTask(String name) {
		// TODO look up the task definition in the current task's
		// subtask definitions and in the process's task definitions
		TaskImpl subtask = createSubTask();
		subtask.setName(name);
		return subtask;
	}

	public TaskImpl addSubTask(TaskImpl subtask) {
		if (subTasks == null) {
			subTasks = new HashSet<TaskImpl>();
		}
		subtask.setSuperTask(this);
		subTasks.add(subtask);
		return subtask;
	}

	public void removeSubTask(Task subtask) {
		if (subtask == null) {
			throw new JbpmException("subtask is null");
		}
		if ((subTasks != null) && (subTasks.remove(subtask))) {
			((TaskImpl) subtask).setSuperTask(null);
		}
	}

	// equals
	// ///////////////////////////////////////////////////////////////////
	// hack to support comparing hibernate proxies against the real objects
	// since this always falls back to ==, we don't need to overwrite the
	// hashcode
	@Override
	public boolean equals(Object o) {
		return EqualsUtil.equals(this, o);
	}

	@Override
	public String toString() {
		return "Task(" + name + ")";
	}

	public String getLifeCycleResource() {
		// the default lifecycle can be overridden in subclasses
		return "jbpm.task.lifecycle.xml";
	}

	// modified getters and setters
	// /////////////////////////////////////////////
	public void setProgress(Integer progress) {
		if ((progress < 0) || (progress > 100)) {
			throw new JbpmException(
					"task progress is a percentage (integer) and must be expressed between 0 and 100");
		}
		this.progress = progress;
	}

	public void cancelExecution(String signal) {
		if (execution != null) {
			execution.end("cancel");
		}
	}

	public void historyTaskDelete(String reason) {
		if (execution != null) {
			HistoryEvent.fire(new TaskDelete(this, reason), execution);
		}
	}

	public void historyTaskComplete(String outcome) {
		if (execution != null) {
			HistoryEvent.fire(new TaskComplete(outcome), execution);
		}
	}

	public void historyTaskSkip(String outcome) {
		if (execution != null) {
			HistoryEvent.fire(new TaskSkip(outcome), execution);
		}
	}

	public void signalExecution(String signalName) {
		if (execution != null) {
			execution.signal(signalName);
		}
	}

	// special getters and setters
	// //////////////////////////////////////////////

	public TaskDefinitionImpl getTaskDefinition() {
		if ((taskDefinition == null) && (taskDefinitionName != null)
				&& (execution != null)) {
			ProcessDefinitionImpl processDefinition = execution
					.getProcessDefinition();
			taskDefinition = processDefinition
					.getTaskDefinition(taskDefinitionName);
		}

		return taskDefinition;
	}

	// customized getters and setters //////////////////////////////////////////

	public String getId() {
		return Long.toString(dbid);
	}

	public void setTaskDefinition(TaskDefinitionImpl taskDefinition) {
		this.taskDefinition = taskDefinition;
		this.taskDefinitionName = taskDefinition.getName();
	}

	public void setExecution(ExecutionImpl execution) {
		this.execution = execution;
		this.executionId = execution.getId();
		this.activityName = execution.getActivityName();
	}

	// getters and setters
	// //////////////////////////////////////////////////////

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date create) {
		this.createTime = create;
	}

	public Date getDuedate() {
		return duedate;
	}

	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}

	@Override
	public ExecutionImpl getExecution() {
		return execution;
	}

	public void setExecution(Execution execution) {
		this.execution = (ExecutionImpl) execution;
	}

	public String getAssignee() {
		return assignee;
	}

	public Swimlane getSwimlane() {
		return swimlane;
	}

	public void setSwimlane(SwimlaneImpl swimlane) {
		this.swimlane = swimlane;
	}

	public TaskImpl getSuperTask() {
		return superTask;
	}

	public void setSuperTask(TaskImpl superTask) {
		this.superTask = superTask;
	}

	public Integer getProgress() {
		return progress;
	}

	public Long getExecutionDbid() {
		return executionDbid;
	}

	public void setExecutionDbid(Long executionDbid) {
		this.executionDbid = executionDbid;
	}

	public Long getSuperTaskDbid() {
		return superTaskDbid;
	}

	public void setSuperTaskDbid(Long parentTaskDbid) {
		this.superTaskDbid = parentTaskDbid;
	}

	public void setParticipations(Set<ParticipationImpl> participations) {
		this.participations = participations;
	}

	public String getExecutionId() {
		return executionId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public void setSubTasks(Set<TaskImpl> subTasks) {
		this.subTasks = subTasks;
	}

	public ExecutionImpl getProcessInstance() {
		return processInstance;
	}

	public void setProcessInstance(ExecutionImpl processInstance) {
		this.processInstance = processInstance;
	}

	public boolean isSignalling() {
		return isSignalling;
	}

	public void setSignalling(boolean isSignalling) {
		this.isSignalling = isSignalling;
	}

	public String getFormResourceName() {
		return formResourceName;
	}

	public void setFormResourceName(String form) {
		this.formResourceName = form;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public void setDbid(long dbid) {
		this.dbid = dbid;
	}

	public ProcessRun getProcessRun() {
		return processRun;
	}

	public void setProcessRun(ProcessRun processRun) {
		this.processRun = processRun;
	}

}
