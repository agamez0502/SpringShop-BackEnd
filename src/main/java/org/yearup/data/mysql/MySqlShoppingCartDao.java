package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao
{
    // attributes
    private ProductDao productDao;

    // constructor
    public MySqlShoppingCartDao(DataSource dataSource, ProductDao productDao) {
        super(dataSource);
        this.productDao = productDao;
    }

    // get user by id
    @Override
    public ShoppingCart getByUserId(int userId)
    {
        // create ShoppingCart object
        ShoppingCart cart = new ShoppingCart();

        // this is a "try-with-resources" block
        // it ensures that the Connection, Statement, and ResultSet are closed automatically after we are done
        try (Connection conn = getConnection();

             // start prepared statement - tied to the open connection
             PreparedStatement prepStatement = conn.prepareStatement(
                     "SELECT product_id, quantity FROM shopping_cart WHERE user_id = ?"))
        {
             // set parameters
            prepStatement.setInt(1, userId);

            // execute the query
            ResultSet row = prepStatement.executeQuery();

            // loop through each row in the ResultSet
            while (row.next())
            {
                int productId = row.getInt("product_id");
                int quantity = row.getInt("quantity");

                Product product = productDao.getById(productId);

                // add and create the ShoppingCartItem object
                ShoppingCartItem item = new ShoppingCartItem();
                item.setProduct(product);
                item.setQuantity(quantity);
                cart.add(item);
            }
        }
        catch (SQLException e)
        {
            // if something goes wrong (SQL error), print the stack trace to help debug
            throw new RuntimeException("❌ Error loading cart", e);
        }
        // return the cart
        return cart;
    }

    // add items to shopping cart
    @Override
    public void add(int userId, int productId)
    {
        // this is a "try-with-resources" block
        // it ensures that the Connection, Statement, and ResultSet are closed automatically after we are done
        try (Connection conn = getConnection())
        {
            // start prepared statement - tied to the open connection
            PreparedStatement prepStatement = conn.prepareStatement(
                    "INSERT INTO shopping_cart (user_id, product_id, quantity) VALUES (?, ?, 1) ON DUPLICATE KEY UPDATE quantity = quantity + 1");

            // set parameters
            prepStatement.setInt(1, userId);
            prepStatement.setInt(2, productId);

            // execute the update to the query - adds items to cart
            prepStatement.executeUpdate();

        } catch (SQLException e)
        {
            // if something goes wrong (SQL error), print the stack trace to help debug
            throw new RuntimeException("❌ Error adding to cart", e);
        }
    }

    // update items in shopping cart
    @Override
    public void update(int userId, int productId, int quantity)
    {
        // this is a "try-with-resources" block
        // it ensures that the Connection, Statement, and ResultSet are closed automatically after we are done
        try (Connection conn = getConnection())
        {
            // start prepared statement - tied to the open connection
            PreparedStatement prepStatement = conn.prepareStatement(
                    "UPDATE shopping_cart SET quantity = ? WHERE user_id = ? AND product_id = ?");

            // set parameters
            prepStatement.setInt(1, quantity);
            prepStatement.setInt(2, userId);
            prepStatement.setInt(3, productId);


            // execute the update to the query - updates items in cart
            prepStatement.executeUpdate();

        } catch (SQLException e)
        {
            // if something goes wrong (SQL error), print the stack trace to help debug
            throw new RuntimeException("❌ Error updating cart", e);
        }
    }

    // clear shopping cart
    @Override
    public void clear(int userId)
    {
        // this is a "try-with-resources" block
        // it ensures that the Connection, Statement, and ResultSet are closed automatically after we are done
        try (Connection conn = getConnection())
        {
            // start prepared statement - tied to the open connection
            PreparedStatement prepStatement = conn.prepareStatement(
                    "DELETE FROM shopping_cart WHERE user_id = ?");

            // set parameters
            prepStatement.setInt(1, userId);

            // execute the update to the query - clears any items in the cart
            prepStatement.executeUpdate();

        } catch (SQLException e)
        {
            // if something goes wrong (SQL error), print the stack trace to help debug
            throw new RuntimeException("❌ Error clearing cart", e);
        }
    }
}