<%@ page import="model.Gun" %>
<%@ page import="java.util.HashSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Каталог</title>
</head>
<body>

<jsp:useBean id="guns" class="java.util.HashSet" scope="request"/>

<table>
    <tr>
        <th>Name</th>
        <th>Caliber</th>
    </tr>
    <% for (Gun gun: (HashSet<Gun>) guns) {%>
            <tr>
                <td><a href="/buy/?id=<%=gun.getId()%>"><%=gun.getName()%></a></td>
                <td><%=gun.getCaliber()%></td>
            </tr>
        <%}%>
</table>

</body>
</html>
