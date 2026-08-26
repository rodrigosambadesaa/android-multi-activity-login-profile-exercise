package com.example.multiactivityprofile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.multiactivityprofile.databinding.ActivityProfileBinding;

public final class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String mode = getIntent().getStringExtra(NavigationContract.EXTRA_MODE);
        if (NavigationContract.MODE_EDIT.equals(mode)) {
            UserProfile user = NavigationContract.readUser(getIntent());
            if (user == null) {
                Toast.makeText(this, R.string.missing_user, Toast.LENGTH_LONG).show();
                finish();
                return;
            }
            binding.heading.setText(R.string.update_profile);
            populate(user);
        } else if (NavigationContract.MODE_REGISTER.equals(mode)) {
            binding.heading.setText(R.string.register_heading);
        } else {
            Toast.makeText(this, R.string.invalid_profile_mode, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        binding.acceptButton.setOnClickListener(view -> submit());
        binding.cancelButton.setOnClickListener(view -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    private void populate(UserProfile user) {
        binding.nickInput.setText(user.getNick());
        binding.firstNameInput.setText(user.getFirstName());
        binding.lastNameInput.setText(user.getLastName());
        binding.genderGroup.check(UserProfile.GENDER_FEMALE.equals(user.getGender())
                ? R.id.genderFemale
                : R.id.genderMale);
    }

    private void submit() {
        String nick = binding.nickInput.getText().toString().trim();
        String firstName = binding.firstNameInput.getText().toString().trim();
        String lastName = binding.lastNameInput.getText().toString().trim();
        String gender = selectedGender();

        if (!ProfileValidator.isValid(nick, firstName, lastName, gender)) {
            Toast.makeText(this, R.string.review_profile_data, Toast.LENGTH_SHORT).show();
            return;
        }

        UserProfile user = new UserProfile(nick, firstName, lastName, gender);
        Intent data = new Intent().putExtra(NavigationContract.EXTRA_USER, user);
        setResult(RESULT_OK, data);
        finish();
    }

    @Nullable
    private String selectedGender() {
        int checkedId = binding.genderGroup.getCheckedRadioButtonId();
        if (checkedId == R.id.genderMale) {
            return UserProfile.GENDER_MALE;
        }
        if (checkedId == R.id.genderFemale) {
            return UserProfile.GENDER_FEMALE;
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        binding = null;
        super.onDestroy();
    }
}
