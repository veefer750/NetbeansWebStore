<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mma" uri="/WEB-INF/murach.tld" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Product Maintenance</title>
        <link rel="stylesheet" href="<c:url value='/styles/main.css'/> ">
    </head>
    <body>
        <h1>Product</h1>
        <p><mma:ifEmptyMark color="blue" field=""/> marks required fields</p>
        <p><i>${message}</i></p>
        <form action="<c:url value='/productMaint'/>" 
              method="post" class="inline">
            <input type="hidden" name="action" value="updateProduct">
            
            <label class="pad_top">Code:</label>
            <input type="text" name="productCode" id="codeBox"
                   value="${product.code}">
            <mma:ifEmptyMark field="${product.code}"/><br>

            <label class="pad_top">Description:</label>
            <input type="text" name="description" 
                   value="${product.description}">
            <mma:ifEmptyMark field="${product.description}"/><br>

            <label class="pad_top">Price:</label>
            <input type="text" name="price" id="priceBox"
                   value="${product.priceNumberFormat}">
            <mma:ifEmptyMark field="${product.priceNumberFormat}"/><br>

            <label class="pad_top">&nbsp;</label>
            <input type="submit" value="Update Product" class="margin_left">
        </form>

        <form action="<c:url value='/productMaint?action=displayProducts'/>" 
              method="get" class="inline">
            <input type="submit" value="View Products">
        </form>
    </body>
</html>