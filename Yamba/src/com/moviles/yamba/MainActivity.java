package com.moviles.yamba;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = "StatusActivity";
	private EditText  editStatus;
	private Button buttonTweet;
	private TextView textCount; 
	private int defaultTextColor;
	Twitter twitter;
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        editStatus=(EditText) findViewById(R.id.editStatus);
        buttonTweet = (Button) findViewById(R.id.buttonTweet);
        textCount = (TextView) findViewById(R.id.textCount);
        
        buttonTweet.setOnClickListener(this);
        
        Log.d(TAG, "Create twitter object");
        twitter = new Twitter("student","password");
        twitter.setAPIRootUrl("http://yamba.marakana.com/api");
        Log.d(TAG, "Set twitter object API root URL");
        
        defaultTextColor = textCount.getTextColors().getDefaultColor(); //
        editStatus.addTextChangedListener(new TextWatcher(){ //
        			
        	public void afterTextChanged(Editable s) { 
        		int count = 140 - editStatus.length(); //
        		textCount.setText(Integer.toString(count));
        		textCount.setTextColor(Color.BLUE); //
        		if ((count < 25) && (9 < count))
        			textCount.setTextColor(Color.GRAY);
        		else if (count < 10)
        			textCount.setTextColor(Color.RED);
        		else
        			textCount.setTextColor(defaultTextColor);
        	}
        			
        	public void beforeTextChanged(CharSequence s, int start, int count, int after) { //
        			}
        			
        	public void onTextChanged(CharSequence s,int start, int before, int count) { //
        			}
        });
        			
        		
        
    }
    
    public void onClick(View View){	
    	String status=editStatus.getText().toString();
    	Log.d(TAG, "Tweet enviado ");
    	new PostTask().execute(status);
    }
    
    
    private final class PostTask extends AsyncTask<String, Integer, String> { 
    @Override
    	protected String doInBackground(String... statuses) { 
    	
    		try {
    			Twitter.Status status= twitter.updateStatus(statuses[0]); 
    			return status.text;
    		} catch (TwitterException e) {
    			Log.e(TAG, e.toString());
    			e.printStackTrace();
    			return "Failed to post to yamba service";
    		}
    	}
    
    protected void onProgressUpdate(Integer... values) {
    	super.onProgressUpdate(values);
    }
    
    @Override
    	protected void onPostExecute(String result) {
    		Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show(); 
    	}
    }	
    	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    
}
