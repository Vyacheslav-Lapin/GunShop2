<%@ page import="model.Gun" %>
<%@ page import="java.util.HashSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/gunshop.tld" prefix="gunshop"%>
<html>
<head>
    <title>Каталог</title>
</head>
<body>

<%--<jsp:useBean id="guns" class="java.util.HashSet" scope="request"/>--%>

<table>
    <tr>
        <th>Name</th>
        <th>Caliber</th>
    </tr>

    ${gunshop:getList(requestScope["guns"])}

</table>

</body>
</html>
