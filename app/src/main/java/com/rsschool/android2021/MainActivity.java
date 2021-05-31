package com.rsschool.android2021;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity
        implements SecondFragment.OnBackPressedListener, FirstFragment.OnGenerateClickListener {

    private final String SECOND_FRAGMENT_TAG = "SecondFragment";

    private OnReceiveResultListener listener = null;
    private OnSystemBackPressedListener backPressedListener = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            openFirstFragment(0);
        }
    }

    private void openFirstFragment(int previousNumber) {
        final Fragment firstFragment = FirstFragment.newInstance(previousNumber);
        if (firstFragment instanceof OnReceiveResultListener) {
            listener = (OnReceiveResultListener)firstFragment;
        }
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, firstFragment);
        transaction.commit();
    }

    private void openSecondFragment(int min, int max) {
        final Fragment secondFragment = SecondFragment.newInstance(min, max);
        if (secondFragment instanceof OnSystemBackPressedListener) {
            backPressedListener = (OnSystemBackPressedListener)secondFragment;
        }
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, secondFragment);
        transaction.addToBackStack(SECOND_FRAGMENT_TAG);
        transaction.commit();
    }

    @Override
    public void onGenerateClick(int min, int max) {
        openSecondFragment(min, max);
    }

    @Override
    public void onBackPressed(Integer result) {
        getSupportFragmentManager().popBackStack();
        if (result != null) {
            listener.onReceiveResult(result);
        }
    }

    @Override
    public void onBackPressed() {
        if (backPressedListener != null) {
            backPressedListener.onSystemBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}
