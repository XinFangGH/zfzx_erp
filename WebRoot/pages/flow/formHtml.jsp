<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
	<c:when test="${not empty taskId}">
		<input type="hidden" name="taskId" value="${taskId}"/>
		<input type="hidden" name="${pkName}" value="${pkValue}"/>
		<textarea id="entity_${taskId}" cols="60" rows="12" style="display:none">${entityJson}</textarea>
		<textarea id="rightstask_${taskId}" cols="120" rows="12" style="display:none">${formRights}</textarea>
		<input type="hidden" id="subSetVarName_${taskId}" value="${subSetVarName}"/>
		<!--  <input type="hidden" id="subPkName_${taskId}" value="${subPkName}"/>-->
		<c:forEach var="subModel" items="${subModels}">
		     <input type="hidden" id="${subModel.entityName}_${taskId}" value="${subModel.primaryFieldName}"/>
		</c:forEach>
		<c:forEach var="subTable" items="${subTables}">
		     <input type="hidden" id="${subTable.tableKey}_${taskId}" value="${subTable.tableId}"/>
		</c:forEach>
	</c:when>
	<c:when test="${ not empty runId}">
	    <input type="hidden" name="${pkName}" value="${pkValue}"/>
		<textarea id="entity_${runId}" cols="60" rows="12" style="display:none">${entityJson}</textarea>
		<input type="hidden" id="subSetVarName_${runId}" value="${subSetVarName}"/>
		<c:forEach var="subModel" items="${subModels}">
		     <input type="hidden" id="${subModel.entityName}_${runId}" value="${subModel.primaryFieldName}"/>
		</c:forEach>
		<!--<input type="hidden" id="subPkName_${runId}" value="${subPkName}"/>-->
	</c:when>
	<c:otherwise>
		<input type="hidden" name="defId" value="${defId}"/>
		<textarea id="rightsdef_${defId}" cols="120" rows="12" style="display:none">${formRights}</textarea>
	</c:otherwise>	
</c:choose>
<input type="hidden" name="formDefId" value="${formDef.formDefId}"/>
${formDef.defHtml}