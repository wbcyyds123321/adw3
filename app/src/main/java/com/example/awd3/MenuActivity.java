package com.example.awd3;

import android.annotation.SuppressLint;
import android.app.Application;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Method;

public class MenuActivity extends AppCompatActivity {
    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.menuTest), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setContentView(R.layout.activity_menu);
        mTextView=findViewById(R.id.menuTest);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_optionmenu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.font_size_small) {
            mTextView.setTextSize(10);
            return true;
        } else if (itemId == R.id.font_size_medium) {
            mTextView.setTextSize(16);
            return true;
        } else if (itemId == R.id.font_size_large) {
            mTextView.setTextSize(20);
            return true;
        } else if (itemId == R.id.menu_common) {
            Toast.makeText(this, "您点击了普通菜单项", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.font_color_red) {
            mTextView.setTextColor(Color.RED);
            return true;
        } else if (itemId == R.id.font_color_black) {
            mTextView.setTextColor(Color.BLACK);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}