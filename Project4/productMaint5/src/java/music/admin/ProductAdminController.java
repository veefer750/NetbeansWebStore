package music.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import music.business.Product;
import music.data.ProductDB;

public class ProductAdminController extends HttpServlet {
    
    /* Comment this method out when using this class with a database
     * instead of a text file.
     */
    /*@Override
    public void init() {
        ProductIO.init(getServletContext()
                .getRealPath("/WEB-INF/products.txt"));
    }*/

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "displayProducts";  // default action
        }

        // perform action and set URL to appropriate page
        String url = "/index.jsp";
        if (action.equals("displayProducts")) {
            url = displayProducts(request, response);
        } else if (action.equals("displayProduct")) {
            url = displayProduct(request, response);
        } else if (action.equals("addProduct")) {
            url = "/product.jsp";
        } else if (action.equals("deleteProduct")) {
            url = deleteProduct(request, response);
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "displayProducts";  // default action
        }

        // perform action and set URL to appropriate page
        String url = "/index.jsp";
        if (action.equals("updateProduct")) {
            url = updateProduct(request, response);
        } else if (action.equals("deleteProduct")) {
            url = deleteProduct(request, response);
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    private String displayProducts(HttpServletRequest request,
            HttpServletResponse response) {
        List<Product> products = (List) ProductDB.selectProducts();
        request.setAttribute("products", products);
        return "/products.jsp";
    }

    private String displayProduct(HttpServletRequest request,
            HttpServletResponse response) {

        String productCode = request.getParameter("productCode");
        Product product;
        if (productCode == null || productCode.isEmpty()) {
            product = new Product();
        } else {
            product = ProductDB.selectProduct(productCode);
        }

        request.setAttribute("product", product);
        return "/product.jsp";
    }

    private String addProduct(HttpServletRequest request,
            HttpServletResponse response) {
            Product product = new Product();
            product.setCode(request.getParameter("productCode"));
            product.setDescription(request.getParameter("productDescription"));
            product.setPrice(Double.parseDouble(request.getParameter("productPrice")));
            ProductDB.insertProduct(product);
        return "/product.jsp";
    }

    private String updateProduct(HttpServletRequest request,
            HttpServletResponse response) {

        String productCode = (String) request.getParameter("productCode");
        String description = (String) request.getParameter("description");
        String priceString = (String) request.getParameter("price");

        double price;
        try {
            price = Double.parseDouble(priceString);
        } catch (NumberFormatException e) {
            price = 0;
        }

        Product product = (Product) request.getAttribute("product");
        if (product == null) {
            product = new Product();
        }
        product.setCode(productCode);
        product.setDescription(description);
        product.setPrice(price);
        request.setAttribute("product", product);

        String message = "";
        if (product.getPrice() <= 0) {
            message = "You must enter a positive number for the price without "
                    + "any currency symbols.";
        }
        if (product.getDescription().length() == 0) {
            message = "You must enter a description for the product.";
        }
        if (product.getCode().length() == 0) {
            message = "You must enter a code for the product.";
        }
        request.setAttribute("message", message);

        String url;
        if (message.isEmpty()) {
            if (ProductDB.exists(product.getCode())) {
                ProductDB.updateProduct(product);
            } else {
                ProductDB.insertProduct(product);
            }
            url = displayProducts(request, response);
        } else {
            url = "/product.jsp";
        }
        return url;
    }
    
    private String deleteProduct(HttpServletRequest request,
            HttpServletResponse response) {

        String productCode = request.getParameter("productCode");
        Product product = ProductDB.selectProduct(productCode);
        request.setAttribute("product", product);
        
        String url;
        String yesButton = request.getParameter("yesButton");
        if (yesButton != null) {
            ProductDB.deleteProduct(product.getCode());
            url = displayProducts(request, response);
        } else {
            url = "/confirm_product_delete.jsp";
        }
        return url;
    }    
}