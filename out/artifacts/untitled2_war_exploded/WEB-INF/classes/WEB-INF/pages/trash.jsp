<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Trash</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
  <h3>Advertisements in Trash</h3>

  <form class="form-inline" role="form" action="/SpringMVC/trash" method="post">
    <input type="text" class="form-control" name="pattern" placeholder="Search">
    <input type="submit" class="btn btn-default" value="Search">
  </form>

  <form action="/SpringMVC/process_checked" method="post">
    <table class="table table-striped">
      <thead>
      <tr>
        <td><b>Checkbox</b></td>
        <td><b>Photo</b></td>
        <td><b>Name</b></td>
        <td><b>Short Desc</b></td>
        <td><b>Long Desc</b></td>
        <td><b>Phone</b></td>
        <td><b>Price</b></td>
      </tr>
      </thead>
      <c:forEach items="${advs}" var="adv">
        <tr>
          <td><input type=checkbox name="selectrow[]" value="${adv.id}"></td>
          <td><img height="40" width="40" src="/SpringMVC/image/${adv.photo.id}" /></td>
          <td>${adv.name}</td>
          <td>${adv.shortDesc}</td>
          <td>${adv.longDesc}</td>
          <td>${adv.phone}</td>
          <td>${adv.price}</td>
        </tr>
      </c:forEach>
    </table>
    <input type="submit" name="submit" class="button2-credit" value="delete" title="Delete checked"/>
    <input type="submit" name="submit" class="button2-credit" value="restore" title="Restore checked"/>
  </form>

  <form class="form-inline" role="form" action="/SpringMVC/" method="post">
    <input type="submit" class="btn btn-primary" value="Go home!">
  </form>
</div>
</body>
</html>