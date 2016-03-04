<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Product Maintenance</title>
        <link rel="stylesheet" href="<c:url value='/styles/main.css'/> ">
    </head>
    <body>
        <h1>Are you sure you want to delete this product?</h1>

        <label>Code:</label>
        <span>${product.code}</span><br>

        <label>Description:</label>
        <span>${product.description}</span><br>

        <label>Price:</label>
        <span>${product.priceNumberFormat}</span><br>

        <form action="" method="post" class="inline">
            <input type="hidden" name="action" value="deleteProduct">
            <input type="hidden" name="productCode" value="${product.code}">
            <input name="yesButton" type="submit" value="Yes" class="confirm_button">
        </form>
        
        <form action="" method="get" class="inline">
            <input type="hidden" name="action" value="displayProducts">
            <input type="submit" value="No" class="confirm_button">
        </form>            
    </body>
</html>