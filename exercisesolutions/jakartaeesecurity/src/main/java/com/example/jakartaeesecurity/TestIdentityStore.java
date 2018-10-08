package com.example.jakartaeesecurity;

import static java.util.Arrays.asList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import javax.security.enterprise.identitystore.IdentityStore;

/**
 *
 * @author David R. Heffelfinger <dheffelfinger@ensode.com>
 */
@ApplicationScoped
public class TestIdentityStore implements IdentityStore {

    private static final Map<String, String> unsecureStore = new HashMap<>();

    public TestIdentityStore() {
        // Don't do this at home, highly unsecure!
        unsecureStore.put("david", "DAVID");
        unsecureStore.put("ed", "ED");
        unsecureStore.put("lisa", "LISA");
        unsecureStore.put("michael", "MICHAEL");
    }

    public CredentialValidationResult validate(UsernamePasswordCredential usernamePasswordCredential) {

        String caller = usernamePasswordCredential.getCaller();
        String pwd = usernamePasswordCredential.getPasswordAsString();

        // just for the exercise, assign the role based on the user name
        String role = "foo";
        if (caller.contains("a")) {
            role = "bar";
        } // else foo!

        if (usernamePasswordCredential.compareTo(caller, unsecureStore.get(caller))) {
            // return a VALID result with the caller and the set of groups s/he belongs to.
            return new CredentialValidationResult(caller, new HashSet<>(asList(role)));
        }
        return INVALID_RESULT;

    }

}
