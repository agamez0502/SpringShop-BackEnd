package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.models.Profile;
import org.yearup.data.ProfileDao;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class MySqlProfileDao extends MySqlDaoBase implements ProfileDao
{
    public MySqlProfileDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public Profile create(Profile profile)
    {
        String sql = "INSERT INTO profiles (user_id, first_name, last_name, phone, email, address, city, state, zip) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, profile.getUserId());
            ps.setString(2, profile.getFirstName());
            ps.setString(3, profile.getLastName());
            ps.setString(4, profile.getPhone());
            ps.setString(5, profile.getEmail());
            ps.setString(6, profile.getAddress());
            ps.setString(7, profile.getCity());
            ps.setString(8, profile.getState());
            ps.setString(9, profile.getZip());

            ps.executeUpdate();

            return profile;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    // get profile by user id
    @Override
    public Profile getByUserId(int userId)
    {
        // this is a "try-with-resources" block
        // it ensures that the Connection, Statement, and ResultSet are closed automatically after we are done
        try (Connection conn = getConnection())
        {
            // start prepared statement - tied to the open connection
            PreparedStatement prepStatement = conn.prepareStatement(
                    "SELECT * FROM profiles WHERE user_id = ?");

            // set parameters
            prepStatement.setInt(1, userId);

            // execute the query
            ResultSet row = prepStatement.executeQuery();

            // loop through each row in the ResultSet
            if (row.next())
            {

                // return the profile
                return mapRow(row);
            }

        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        // if no profile is found
        return null;
    }

    // update user profile
    @Override
    public void update(int userId, Profile profile)
    {
        // this is a "try-with-resources" block
        // it ensures that the Connection, Statement, and ResultSet are closed automatically after we are done
        try (Connection conn = getConnection())
        {
            // start prepared statement - tied to the open connection
            PreparedStatement prepStatement = conn.prepareStatement(
                    "UPDATE profiles SET first_name = ?, last_name = ?, phone = ?, email = ?, address = ?, city = ?, state = ?, zip = ? WHERE user_id = ?");

            // set parameters
            prepStatement.setString(1, profile.getFirstName());
            prepStatement.setString(2, profile.getLastName());
            prepStatement.setString(3, profile.getPhone());
            prepStatement.setString(4, profile.getEmail());
            prepStatement.setString(5, profile.getAddress());
            prepStatement.setString(6, profile.getCity());
            prepStatement.setString(7, profile.getState());
            prepStatement.setString(8, profile.getZip());
            prepStatement.setInt(9, userId);

            // execute the update to the query - updates profile
            prepStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    private Profile mapRow(ResultSet row) throws SQLException
    {
        return new Profile(
                row.getInt("user_id"),
                row.getString("first_name"),
                row.getString("last_name"),
                row.getString("phone"),
                row.getString("email"),
                row.getString("address"),
                row.getString("city"),
                row.getString("state"),
                row.getString("zip"));
    }
}