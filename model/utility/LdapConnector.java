/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bootcamp.model.utility;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 *
 * @author oFNo's PC
 */
public class LdapConnector {
    
    public static Map<String, Object> getInfo(String username, String password, String role) {
        Map<String, Object> result = new TreeMap<String, Object>();
        boolean loginStat = false;
        String message = null;
        String url = "ldap://10.140.0.110:389";
        try {
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldap://10.140.0.110:389");
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, "uid=" + username + ",ou=People,ou="+ role +",dc=sit,dc=kmutt,dc=ac,dc=th");
            env.put(Context.SECURITY_CREDENTIALS, password);
            DirContext ctx = new InitialDirContext(env);
            loginStat = ctx != null;
            if (ctx != null) {
                ctx.close();
            }
        } catch(CommunicationException e){
            message = "Cannot connect to LDAP server.\n(connection timeout)";
        } catch (Exception e) {
            e.printStackTrace();
            message = "Username or Password is incorrect.";
        }
        result.put("result", loginStat);
        result.put("message", message);
        
        return result;
    }
    
}
