package link.fitbody;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import link.fitbody.core.Health;

/**
 * A simple {@link Fragment} subclass.
 */
public class BodyTypeFragment extends Fragment implements View.OnClickListener {

    Button calculateBtn;

    EditText bust;
    EditText waist;
    EditText hip;

    TextView descriptionText;

    public BodyTypeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_body_type, container, false);
        calculateBtn = (Button) view.findViewById(R.id.calculateButton);
        calculateBtn.setOnClickListener(this);

        bust = (EditText) view.findViewById(R.id.bustEditText);
        waist = (EditText) view.findViewById(R.id.waistEditText);
        hip = (EditText) view.findViewById(R.id.hipEditText);

        //descriptionText = (TextView) view.findViewById(R.id.description_text);
        //descriptionText.setMovementMethod(new ScrollingMovementMethod());

        return view;
    }


    @Override
    public void onClick(View v) {
        // need to enter all values if not than message is shown
        if (bust.getText().toString().equals("") || waist.getText().toString().equals("") || hip.getText().toString().equals("")) {
            if(listener != null){
                listener.onFragmentSendMessage("Please enter all values.");
            }

        } else {
            //
            double bustCm = Double.valueOf(bust.getText().toString());
            double waistCm = Double.valueOf(waist.getText().toString());
            double hipCm = Double.valueOf(hip.getText().toString());

            Health health = new Health();
            String bodyType = health.calculateBodyType(bustCm, waistCm, hipCm);

            Spannable spannable = new SpannableString("Your body type is: " + bodyType);
            spannable.setSpan(new ForegroundColorSpan(Color.rgb(239, 106, 144)), 19, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            if(listener != null){
                listener.onFragmentSendMessage(spannable);
            }

        }
    }

    OnFragmentSendMessageListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (OnFragmentSendMessageListener)context;
    }

}
