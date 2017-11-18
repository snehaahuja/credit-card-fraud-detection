
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib
    prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" 
%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<title>Fraud Detection Engine</title>
<body>
<h1><center><font color="blue">Training Successfully Completed</font></center></h1>
<center><a href="/FinalProject1/test.html">Click here for Testing</a></center>
<br/><br/><br/>


<center><table>

<c:forEach items="${msg}" var="trainVar">

<tr>
<td><center>Epoch Number:</center></td><td><center>${trainVar.epoch}</center></td><td></td><td></td><td></td><td></td>
<td><center>Instance Number:</center></td><td><center>${trainVar.instanceNum}</center></td><td></td><td></td><td></td><td></td><td></td><td></td>
<td><center>Expected O/p:</center></td><td><center>${trainVar.in}</center></td><td></td><td></td><td></td><td></td><td></td><td></td>
<td><center>Actual O/p:</center></td><td><center>${trainVar.out}</center></td><td></td>


</tr> 
</c:forEach>
</table></center>
</body>
</html>