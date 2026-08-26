package com.example.multiactivityprofile;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.multiactivityprofile.databinding.ActivityOrdersBinding;

public final class OrdersActivity extends AppCompatActivity {
    private static final Uri CONTACT_URI = Uri.parse("https://www.evay.net/");

    private ActivityOrdersBinding binding;
    private UserProfile user;

    private final ActivityResultLauncher<Intent> profileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    UserProfile updated = NavigationContract.readUser(result.getData());
                    if (updated == null) {
                        Toast.makeText(this, R.string.invalid_profile_result, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    user = updated;
                    renderUser();
                    Toast.makeText(this, R.string.profile_updated, Toast.LENGTH_SHORT).show();
                } else if (result.getResultCode() == RESULT_CANCELED) {
                    Toast.makeText(this, R.string.profile_update_cancelled, Toast.LENGTH_SHORT).show();
                }
            }
    );

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        user = NavigationContract.readUser(getIntent());
        if (user == null) {
            Toast.makeText(this, R.string.missing_user, Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        renderUser();
    }

    private void renderUser() {
        if (binding == null || user == null) {
            return;
        }
        int welcomeRes = UserProfile.GENDER_FEMALE.equals(user.getGender())
                ? R.string.welcome_female
                : R.string.welcome_male;
        binding.userHeading.setText(getString(
                R.string.orders_heading,
                user.getFirstName(),
                user.getNick(),
                getString(welcomeRes)
        ));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.orders_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_profile) {
            Intent intent = new Intent(this, ProfileActivity.class)
                    .putExtra(NavigationContract.EXTRA_MODE, NavigationContract.MODE_EDIT)
                    .putExtra(NavigationContract.EXTRA_USER, user);
            profileLauncher.launch(intent);
            return true;
        }
        if (item.getItemId() == R.id.action_contact) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, CONTACT_URI));
            } catch (ActivityNotFoundException exception) {
                Toast.makeText(this, R.string.no_browser, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        binding = null;
        super.onDestroy();
    }
}
