# icetag
icetag

示例:

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/ice" prefix="i"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    <div>icetag demo</div>
    <i:date/>
    <i:date format="yyyy-MM-dd"/>
    <i:for list="${list}" var="li" index="id">
        <div>
            <span>${id}</span>
            <div>${li}</div>
        </div>
    </i:for>
</body>
</html>
