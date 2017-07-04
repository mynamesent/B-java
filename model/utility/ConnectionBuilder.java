    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bootcamp.model.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jilapat
 */
public class ConnectionBuilder {
    
    public static Connection mainConnection = null;
    
    public static Connection getConnection(){
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Properties property = new Properties();
            property.put("user", "jilapat_bootcamp");
            property.put("password", "bootcamp");
            property.put("useEncoding", "true");
            property.put("characterEncoding", "UTF-8");
            con  =  DriverManager.getConnection("jdbc:mysql://th.jilapat.com:3306/jilapat_bootcamp",property);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionBuilder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex){
            Logger.getLogger(ConnectionBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
    
}
