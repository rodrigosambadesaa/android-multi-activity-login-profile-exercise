package com.example.multiactivityprofile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.multiactivityprofile.databinding.ActivityLoginBinding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class LoginActivity extends AppCompatActivity {
    private static final long LOGIN_DELAY_MILLIS = 600L;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private ActivityLoginBinding binding;
    private Future<?> loginTask;

    private final ActivityResultLauncher<Intent> registrationLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    UserProfile profile = NavigationContract.readUser(result.getData());
                    if (profile == null) {
                        Toast.makeText(this, R.string.invalid_profile_result, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(this, R.string.registration_success, Toast.LENGTH_SHORT).show();
                    openOrders(profile);
                } else if (result.getResultCode() == RESULT_CANCELED) {
                    Toast.makeText(this, R.string.registration_cancelled, Toast.LENGTH_SHORT).show();
                }
            }
    );

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginButton.setOnClickListener(view -> startLogin());
        binding.registerButton.setOnClickListener(view -> openRegistration());
    }

    private void startLogin() {
        if (binding == null || loginTask != null) {
            return;
        }

        String nick = binding.nickInput.getText().toString().trim();
        String password = binding.passwordInput.getText().toString();
        if (nick.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, R.string.credentials_required, Toast.LENGTH_SHORT).show();
            return;
        }

        setRunning(true);
        loginTask = executor.submit(() -> {
            try {
                boolean authenticated = DemoLoginService.authenticateWithDelay(
                        nick,
                        password,
                        LOGIN_DELAY_MILLIS
                );
                mainHandler.post(() -> finishLogin(authenticated, nick));
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        });
    }

    private void finishLogin(boolean authenticated, String nick) {
        if (!canUpdateUi()) {
            return;
        }
        loginTask = null;
        setRunning(false);

        if (!authenticated) {
            Toast.makeText(this, R.string.invalid_credentials, Toast.LENGTH_SHORT).show();
            return;
        }

        UserProfile demoUser = new UserProfile(
                nick,
                getString(R.string.demo_first_name),
                getString(R.string.demo_last_name),
                UserProfile.GENDER_MALE
        );
        openOrders(demoUser);
    }

    private void openRegistration() {
        Intent intent = new Intent(this, ProfileActivity.class)
                .putExtra(NavigationContract.EXTRA_MODE, NavigationContract.MODE_REGISTER);
        registrationLauncher.launch(intent);
    }

    private void openOrders(UserProfile profile) {
        Intent intent = new Intent(this, OrdersActivity.class)
                .putExtra(NavigationContract.EXTRA_USER, profile);
        startActivity(intent);
    }

    private void setRunning(boolean running) {
        if (binding == null) {
            return;
        }
        binding.progressBar.setVisibility(running ? View.VISIBLE : View.GONE);
        binding.loginButton.setEnabled(!running);
        binding.registerButton.setEnabled(!running);
        binding.nickInput.setEnabled(!running);
        binding.passwordInput.setEnabled(!running);
    }

    private boolean canUpdateUi() {
        return binding != null && !isFinishing() && !isDestroyed();
    }

    @Override
    protected void onDestroy() {
        Future<?> task = loginTask;
        if (task != null) {
            task.cancel(true);
            loginTask = null;
        }
        executor.shutdownNow();
        mainHandler.removeCallbacksAndMessages(null);
        binding = null;
        super.onDestroy();
    }
}
