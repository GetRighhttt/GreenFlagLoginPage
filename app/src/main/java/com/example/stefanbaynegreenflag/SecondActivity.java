package com.example.stefanbaynegreenflag;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This program is a login app that verifies certain specifications for emails
 * and passwords. If specifications are not met, authentication is not allowed,
 * and error messages will appear.
 */

public class SecondActivity extends AppCompatActivity {

    EditText editCreatePassword;
    EditText editEmailAddress;
    EditText editConfirmPassword;
    ImageButton buttonToConfirm;

    boolean isValidEmail, isValidPassword, isValidConfirm;

    // create variables and reference to invisible views that become visible if error
    TextView errorPassword;
    TextView errorEmail;
    ImageView redCross2;
    ImageView redCross3;
    ImageView redCross1;
    ImageView greenCheck1;
    ImageView greenCheck2;
    ImageView greenCheck3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        // create reference from XML file to Main file and find the view
        ImageView imageView1 = findViewById(R.id.back_button);
        buttonToConfirm= findViewById(R.id.button_to_confirm);
        editEmailAddress = findViewById(R.id.edit_email);
        editCreatePassword = findViewById(R.id.write_password);
        editConfirmPassword = findViewById(R.id.confirm_password);
        errorPassword = findViewById(R.id.error_message_password);
        errorEmail = findViewById(R.id.error_message_email);
        redCross2 = findViewById(R.id.red_cross2);
        redCross3 = findViewById(R.id.red_cross1);
        redCross1 = findViewById(R.id.red_cross3);
        greenCheck1 = findViewById(R.id.green_check1);
        greenCheck2 = findViewById(R.id.green_check2);
        greenCheck3 = findViewById(R.id.green_check3);


        // give back button functionality
        imageView1.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(SecondActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // TextWatcher to give functionality to invisible views when text is entered
        // This is where the methods will check to see if input is valid also
        TextWatcher loginTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                // Enabled/Disable button modifier.
                String usernameInput = editEmailAddress.getText().toString().trim();
                String passwordInput = editConfirmPassword.getText().toString().trim();
                String createPassword = editCreatePassword.getText().toString().trim();

                if(!usernameInput.isEmpty()) {
                    isValidEmail = validateEmail();
                    passwordThings(greenCheck1, redCross1, errorEmail, isValidEmail);
                }

                if(!passwordInput.isEmpty()){
                    isValidPassword = validatePassword(passwordInput);
                    passwordThings(greenCheck3, redCross3, errorPassword, isValidPassword);
                }

                if(!createPassword.isEmpty()){
                    isValidConfirm = validatePassword(createPassword);
                    passwordThings(greenCheck2, redCross2, errorPassword, isValidConfirm);
                }

                if(editCreatePassword.equals(editConfirmPassword)){
                    System.out.println("The Passwords are equal.");
                }
            }
        };


        // Pass in the textWatcher to each view
        editEmailAddress.addTextChangedListener(loginTextWatcher);
        editCreatePassword.addTextChangedListener(loginTextWatcher);
        editConfirmPassword.addTextChangedListener(loginTextWatcher);

        // set the backgrounds of the editText fields before the user inputs data
        editEmailAddress.setBackgroundResource(R.drawable.whiteborder);
        editCreatePassword.setBackgroundResource(R.drawable.whiteborder);
        editConfirmPassword.setBackgroundResource(R.drawable.whiteborder);

        // Set the functionality of the button
        buttonToConfirm.setOnClickListener(view -> {
            String usernameInput = editEmailAddress.getText().toString().trim();
            String passwordInput = editConfirmPassword.getText().toString().trim();
            String createPassword = editCreatePassword.getText().toString().trim();

            if(usernameInput.isEmpty() && passwordInput.isEmpty() && createPassword.isEmpty()) {
                becomeVisibile(redCross1, View.VISIBLE);
                becomeVisibile(redCross2, View.VISIBLE);
                becomeVisibile(redCross3, View.VISIBLE);
                becomeVisible2(errorEmail, View.VISIBLE);
                becomeVisible2(errorPassword, View.VISIBLE);
            }

            if(isValidConfirm && isValidEmail && isValidPassword) {
                confirmInput();
                editConfirmPassword.setBackgroundResource(R.drawable.greenboarder);
                editCreatePassword.setBackgroundResource(R.drawable.greenboarder);
                editEmailAddress.setBackgroundResource(R.drawable.greenboarder);
                Toast.makeText(this, "Email and Password confirmed.", Toast.LENGTH_SHORT).show();
            }

            EditText editText = editConfirmPassword;
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;
                    if (actionId == EditorInfo.IME_ACTION_GO) {
                        handled = true;
                    }
                    return handled;
                }
            });
        });

    }

    // Method to validate the email
    private boolean validateEmail() {
        String emailInput = editEmailAddress.getText().toString().trim();
        if (emailInput.isEmpty()) {
            becomeVisibile(redCross1, View.VISIBLE);
            becomeVisible2(errorEmail, View.VISIBLE);
            becomeVisibile(greenCheck1, View.INVISIBLE);
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            becomeVisibile(redCross1, View.VISIBLE);
            becomeVisible2(errorEmail, View.VISIBLE);
            becomeVisibile(greenCheck1, View.INVISIBLE);
            return false;
        } else {
            becomeVisibile(redCross1, View.INVISIBLE);
            becomeVisible2(errorEmail, View.INVISIBLE);
            becomeVisibile(greenCheck1, View.VISIBLE);
            editEmailAddress.setError(null);
            return true;
        }
    }

    // Method to set visibility to the passwords to reduce redundancy
    private void passwordThings(ImageView check, ImageView cross, TextView textView, boolean isOkay) {
        if(isOkay){
            becomeVisibile(check, View.VISIBLE);
            becomeVisible2(textView, View.INVISIBLE);
            becomeVisibile(cross, View.INVISIBLE);
        }else{
            becomeVisibile(cross, View.VISIBLE);
            becomeVisible2(textView, View.VISIBLE);
            becomeVisibile(check, View.INVISIBLE);
        }
    }

    // Method used to validate the password with certain specifications
    private boolean validatePassword(String password) {
        Pattern pattern;
        Matcher matcher;
        final String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,}$";
        pattern = Pattern.compile(passwordPattern);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    // Methods to determine visibility of the views depending on authentication
    // first method used to determine ImageView visibility
    public void becomeVisibile(ImageView imageView, int isVisible) {
        imageView.setVisibility(isVisible);
    }

    // second method used to determine visibility of Textview
    public void becomeVisible2(TextView textView, int isVisible) {
        textView.setVisibility(isVisible);
    }

    // Toast message after the email and password has been confirmed
    public void confirmInput() {
        String input = "Email: " + editEmailAddress.getText().toString();
        input += "\n";
        input += "Password: " + editConfirmPassword.getText().toString();
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }
}


