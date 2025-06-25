package org.yearup.data.mysql;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao
{
    public MySqlCategoryDao(DataSource dataSource)
    {
        super(dataSource);
    }

    // get all categories
    @Override
    public List<Category> getAllCategories()
    {
        // create an empty list to hold all the categories
        List<Category> categories = new ArrayList<>();

        // this is a "try-with-resources" block
        // it ensures that the Connection, Statement, and ResultSet are closed automatically after we are done
        try (Connection connection = getConnection();

             // start prepared statement - tied to the open connection
             PreparedStatement prepStatement = connection.prepareStatement(
                     "SELECT * FROM categories");

             // execute the query
             ResultSet row = prepStatement.executeQuery())
        {
            // loop through each row in the ResultSet
            while (row.next())
            {
                // add and create the Categories object to our list
                Category category = mapRow(row);
                categories.add(category);
            }

        } catch (SQLException e)
        {
            // if something goes wrong (SQL error), print the stack trace to help debug
            throw new RuntimeException("❌ Error getting all categories", e);
        }
        // return the list of Categories
        return categories;
    }

    // get category by id
    @Override
    public Category getById(int categoryId)
    {
        // this is a "try-with-resources" block
        // it ensures that the Connection, Statement, and ResultSet are closed automatically after we are done
        try (Connection conn = getConnection())
        {
             // start prepared statement - tied to the open connection
             PreparedStatement prepStatement = conn.prepareStatement(
                     "SELECT * FROM categories WHERE category_id = ?");

            // set parameters
            prepStatement.setInt(1, categoryId);

            // execute the query
            ResultSet row = prepStatement.executeQuery();

            // loop through each row in the ResultSet
            if (row.next()) {

                // return the Category
                return mapRow(row);
            }

        } catch (SQLException e)
        {
            // if something goes wrong (SQL error), print the stack trace to help debug
            throw new RuntimeException("❌ Error getting category by ID", e);
        }
        // if no category is found
        return null;
    }

    // create a new category
    @Override
    public Category create(Category category)
    {
        // this is a "try-with-resources" block
        // it ensures that the Connection, Statement, and ResultSet are closed automatically after we are done
        try (Connection conn = getConnection())
        {
             // start prepared statement - tied to the open connection
             PreparedStatement prepStatement = conn.prepareStatement(
                     "INSERT INTO categories (name, description) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);

            // set parameters
            prepStatement.setString(1, category.getName());
            prepStatement.setString(2, category.getDescription());

            // execute the update to the query - inserts a row to the db
            int rowsAffected = prepStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Retrieve the generated keys
                ResultSet generatedKeys = prepStatement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    // Retrieve the auto-incremented ID
                    int orderId = generatedKeys.getInt(1);

                    // get the newly inserted category
                    return getById(orderId);
                }
            }

        } catch (SQLException e)
        {
            // if something goes wrong (SQL error), print the stack trace to help debug
            throw new RuntimeException("❌ Error creating category", e);
        }
        // if no category is found
        return null;
    }

    // update category
    @Override
    public void update(int categoryId, Category category)
    {
        // this is a "try-with-resources" block
        // it ensures that the Connection, Statement, and ResultSet are closed automatically after we are done
        try (Connection conn = getConnection())
        {
             // start prepared statement - tied to the open connection
             PreparedStatement prepStatement = conn.prepareStatement(
                     "UPDATE categories SET name = ?, description = ? WHERE category_id = ?");

            // set parameters
            prepStatement.setString(1, category.getName());
            prepStatement.setString(2, category.getDescription());
            prepStatement.setInt(3, categoryId);

            // execute the update to the query - updates a row in the db
            prepStatement.executeUpdate();

        } catch (SQLException e)
        {
            // if something goes wrong (SQL error), print the stack trace to help debug
            throw new RuntimeException("❌ Error updating category", e);
        }
    }

    // delete category
    @Override
    public void delete(int categoryId)
    {
        // this is a "try-with-resources" block
        // it ensures that the Connection, Statement, and ResultSet are closed automatically after we are done
        try (Connection conn = getConnection())
        {
             // start prepared statement - tied to the open connection
             PreparedStatement prepStatement = conn.prepareStatement(
                     "DELETE FROM categories WHERE category_id = ?");

            // set parameters
            prepStatement.setInt(1, categoryId);

            // execute the update to the query - deletes a row in the db
            prepStatement.executeUpdate();

        } catch (SQLException e)
        {
            // if something goes wrong (SQL error), print the stack trace to help debug
            throw new RuntimeException("❌ Error deleting category", e);
        }
    }

    private Category mapRow(ResultSet row) throws SQLException
    {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category()
        {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }
}
