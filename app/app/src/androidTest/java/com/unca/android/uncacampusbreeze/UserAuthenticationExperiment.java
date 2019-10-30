package com.unca.android.uncacampusbreeze;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import org.junit.Test;

public class UserAuthenticationExperiment {

    static final private String DEBUG_TAG = "UserAuthEx";

    @Test
    public void signInAnon() {
        Log.d(DEBUG_TAG, "Entering signInAnon().");
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Log.d(DEBUG_TAG, "No user associated with this app instance.");
            mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(DEBUG_TAG, "signInAnonymously:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d(DEBUG_TAG, "signInAnonymously:failure", task.getException());
                    }
                }
            });
        } else {
            Log.d(DEBUG_TAG, "A user is already associated with this app instance.");
        }
        Log.d(DEBUG_TAG, "Exiting signInAnon().");
    }
}
