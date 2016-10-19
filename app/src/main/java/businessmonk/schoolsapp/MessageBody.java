package businessmonk.schoolsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MessageBody extends AppCompatActivity {
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_body);
        Intent intent = getIntent();
        String body = intent.getStringExtra("body");
        title = intent.getStringExtra("title");
        TextView bodyView = (TextView) findViewById(R.id.msg_body);
        bodyView.setText(body);
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.msg_body,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.replay) {
            startActivity(new Intent(MessageBody.this,NewMessage.class).putExtra("title",title));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
