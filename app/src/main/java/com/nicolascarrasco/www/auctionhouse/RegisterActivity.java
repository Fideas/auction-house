package com.nicolascarrasco.www.auctionhouse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    private UserRegisterTask mRegisterTask = null;

    private EditText mUserView;
    private EditText mEmailView;
    private EditText mPasswordView;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUserView = (EditText) findViewById(R.id.name);
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        TextView loginLink = (TextView) findViewById(R.id.link_login);
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button userRegisterButton = (Button) findViewById(R.id.register_button);
        userRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Replace with some real registration logic
                attemptRegister();
            }
        });

        mProgressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getString(R.string.registering_dialog));
    }

    public void attemptRegister() {

        if (mRegisterTask != null) {
            return;
        }

        mUserView.setError(null);
        mEmailView.setError(null);
        mPasswordView.setError(null);

        String user = mUserView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(user)) {
            mUserView.setError(getString(R.string.error_field_required));
            focusView = mUserView;
            cancel = true;
        } else if (!Utilities.isUserValid(user)) {
            mUserView.setError(getString(R.string.error_invalid_user));
            focusView = mUserView;
            cancel = true;
        }

        //Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !Utilities.isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!Utilities.isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            Utilities.showProgress(mProgressDialog, true);
            mRegisterTask = new UserRegisterTask(email);
            mRegisterTask.execute((Void) null);

        }
    }

    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private String mEmail;

        public UserRegisterTask(String email) {
            mEmail = email;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            //This method will always login the user, however there is no real registration
            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra(Utilities.USER_EXTRA_KEY, mEmail);
            startActivity(intent);
        }

        @Override
        protected void onCancelled() {
            mRegisterTask = null;
            Utilities.showProgress(mProgressDialog, false);
        }
    }
}
