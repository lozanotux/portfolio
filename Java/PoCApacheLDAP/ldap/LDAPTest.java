package soajp.ldap;

import java.io.IOException;
import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;

public class LDAPTest {
    
    public static void main(String[] args) throws LdapException, CursorException, IOException{
        LdapConnection connection = new LdapNetworkConnection( "localhost", 10389 );
        connection.bind("uid=admin,ou=system", "secret");
        
        EntryCursor cursor = connection.search( "ou=system", "(objectclass=*)", SearchScope.ONELEVEL, "*" );
        
        while (cursor.next()) {
            Entry entry = cursor.get();
            
            System.out.println(entry.toString());
        }
        
        connection.unBind();
        connection.close();
    }
    
}
