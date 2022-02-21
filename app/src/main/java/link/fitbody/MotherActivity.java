package link.fitbody;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MotherActivity extends AppCompatActivity implements OnFragmentSendMessageListener {

    Toast toast = null;

    public void showCalculatorToast(CharSequence message){
        LayoutInflater inflater = getLayoutInflater();
        // Inflate the Layout
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_layout));

        TextView text = (TextView) layout.findViewById(R.id.toast_message);
        // Set the Text to show in TextView
        text.setText(message);

        if(toast != null){
            toast.cancel();
        }

        toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void showInfoToast(CharSequence message){
        LayoutInflater inflater = getLayoutInflater();
        // Inflate the Layout
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));

        layout.setBackgroundColor(ContextCompat.getColor(this, R.color.toastBackgroundLight));

        TextView text = (TextView) layout.findViewById(R.id.toast_message);
        // Set the Text to show in TextView
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
        text.setText(message);

        if(toast != null){
            toast.cancel();
        }

        toast = new Toast(this);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    @Override
    public void onFragmentSendMessage(CharSequence message) {
        showCalculatorToast(message);
    }

    protected void changeActionBarText(String text){
        getSupportActionBar().setTitle(text);
    }

    protected void enableUpButton(){
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
