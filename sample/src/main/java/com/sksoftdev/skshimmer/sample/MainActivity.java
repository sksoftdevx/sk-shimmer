package com.sksoftdev.skshimmer.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sksoftdev.skshimmer.SKShimmerLayout;

public class MainActivity extends Activity {
    private int dp(int v) { return (int)(v * getResources().getDisplayMetrics().density + .5f); }
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(20), dp(24), dp(20), dp(20));
        root.setBackgroundColor(0xFFFFFFFF);
        TextView title = new TextView(this);
        title.setText("SK Shimmer");
        title.setTextSize(24);
        title.setTextColor(0xFF0A1F8F);
        root.addView(title, new LinearLayout.LayoutParams(-1, dp(50)));

        SKShimmerLayout shimmer = new SKShimmerLayout(this);
        shimmer.setShimmerDuration(1200);
        LinearLayout skeleton = new LinearLayout(this);
        skeleton.setOrientation(LinearLayout.VERTICAL);
        skeleton.setPadding(dp(14), dp(14), dp(14), dp(14));
        skeleton.setBackgroundColor(0xFFE5E7EB);
        View image = new View(this); image.setBackgroundColor(0xFFD1D5DB);
        skeleton.addView(image, new LinearLayout.LayoutParams(-1, dp(90)));
        View line1 = new View(this); line1.setBackgroundColor(0xFFD1D5DB);
        LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(dp(210), dp(16)); p1.topMargin = dp(14);
        skeleton.addView(line1, p1);
        View line2 = new View(this); line2.setBackgroundColor(0xFFD1D5DB);
        LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(dp(145), dp(12)); p2.topMargin = dp(9);
        skeleton.addView(line2, p2);
        shimmer.addView(skeleton, new LinearLayout.LayoutParams(-1, -2));
        LinearLayout.LayoutParams sp = new LinearLayout.LayoutParams(-1, -2); sp.topMargin = dp(18);
        root.addView(shimmer, sp);
        TextView note = new TextView(this); note.setText("Shimmer starts automatically."); note.setTextColor(0xFF4B5563);
        LinearLayout.LayoutParams np = new LinearLayout.LayoutParams(-1, dp(42)); np.topMargin = dp(14);
        root.addView(note, np);
        setContentView(root);
    }
}
