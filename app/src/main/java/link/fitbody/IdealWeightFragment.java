package link.fitbody;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import link.fitbody.core.Health;


/**
 * A simple {@link Fragment} subclass.
 */
public class IdealWeightFragment extends Fragment implements View.OnClickListener {

    Button calculateBtn;

    EditText ageText;
    RadioGroup genderRadioGroup;
    EditText heightText;

    RadioButton maleRadioButton;
    RadioButton femaleRadioButton;


    public IdealWeightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.activity_ideal_weight, container, false);
        calculateBtn = (Button) view.findViewById(R.id.calculateButton);
        calculateBtn.setOnClickListener(this);

        ageText = (EditText)view.findViewById(R.id.ageEditText);
        genderRadioGroup = (RadioGroup)view.findViewById(R.id.genderRadioGroup);
        heightText = (EditText)view.findViewById(R.id.heightEditText);

        maleRadioButton = (RadioButton)view.findViewById(R.id.maleRadioButton);
        femaleRadioButton = (RadioButton)view.findViewById(R.id.femaleRadioButton);
        return view;
    }

    @Override
    public void onClick(View v) {
        String age = ageText.getText().toString();

        if(age.equals("")){
            if(listener != null){
                listener.onFragmentSendMessage("Please enter your age.");
            }
        } else {
            int ageNumeric = Integer.valueOf(age);

            if(ageNumeric < 18){
                if(listener != null){
                    listener.onFragmentSendMessage("You must be over 18.");
                }
            } else {
                if(heightText.getText().toString().equals("")){
                    if(listener != null){
                        listener.onFragmentSendMessage("Please, enter your height.");
                    }
                } else {

                    String gender = "";

                    int selectedId = genderRadioGroup.getCheckedRadioButtonId();
                    if (selectedId != -1) {
                        if (selectedId == maleRadioButton.getId()) {
                            gender = "M";
                        } else if (selectedId == femaleRadioButton.getId()) {
                            gender = "F";
                        }
                    }

                    double height = Double.valueOf(heightText.getText().toString());

                    if(!gender.equals("")){
                        Health health = new Health();
                        double result = health.calculateIdealWeight(gender, height);

                        Spannable spannable = new SpannableString("Your ideal weight is " + (int)result + "kg.");
                        spannable.setSpan(new ForegroundColorSpan(Color.rgb(239, 106, 144)), 21, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        if(listener != null){
                            listener.onFragmentSendMessage(spannable);
                        }
                    }
                }
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
