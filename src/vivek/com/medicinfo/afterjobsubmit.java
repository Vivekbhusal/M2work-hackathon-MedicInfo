package vivek.com.medicinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class afterjobsubmit extends Activity {

	String err_msg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.afterjobsubmit);
		
		Bundle extras = getIntent().getExtras();
        if (extras != null) {
            err_msg = extras.getString("error_msg");
         }
        else
        {
        	Intent i = new Intent(getApplicationContext(), logout.class);
        	startActivity(i);
        	finish();
        }
        
        TextView error = (TextView) findViewById(R.id.msg);
        error.setText(err_msg);
        ImageView startjob = (ImageView) findViewById(R.id.anotherjob);
        startjob.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent job = new Intent(getApplicationContext(), beforestartingjob.class);
				startActivity(job);
				finish();
				
			}
		});
	}
	

}
