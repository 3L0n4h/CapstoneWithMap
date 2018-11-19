package hci.com.tentativecapstoneui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class PostSummary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_summary);

        String post_id = getIntent().getExtras().getString("postId");
        Toast.makeText(PostSummary.this, post_id, Toast.LENGTH_SHORT).show();
    }
}
