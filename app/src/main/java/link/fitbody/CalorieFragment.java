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
import android.widget.Spinner;
import android.widget.TextView;

import link.fitbody.core.Health;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalorieFragment extends Fragment implements View.OnClickListener {

    Button calculateBtn;

    EditText ageEditText;
    RadioGroup genderRadioGroup;
    RadioButton maleRadioButton;
    RadioButton femaleRadioButton;
    EditText heightEditText;
    EditText weightEditText;
    Spinner activitySpinner;


    public CalorieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_calorie, container, false);

        calculateBtn = (Button) view.findViewById(R.id.calculateButton);
        calculateBtn.setOnClickListener(this);

        ageEditText = (EditText) view.findViewById(R.id.ageEditText);
        genderRadioGroup = (RadioGroup) view.findViewById(R.id.genderRadioGroup);
        heightEditText = (EditText) view.findViewById(R.id.heightEditText);
        weightEditText = (EditText) view.findViewById(R.id.weightEditText);
        activitySpinner = (Spinner) view.findViewById(R.id.activitySpinner);

        maleRadioButton = (RadioButton) view.findViewById(R.id.maleRadioButton);
        femaleRadioButton = (RadioButton) view.findViewById(R.id.femaleRadioButton);

        return view;
    }

    @Override
    public void onClick(View v) {
        String age = ageEditText.getText().toString();
        String weight = weightEditText.getText().toString();
        String height = heightEditText.getText().toString();
        int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();
        int selectedActivityIndex = activitySpinner.getSelectedItemPosition();

        if (age.equals("") || weight.equals("") || height.equals("") || selectedGenderId == -1 || selectedActivityIndex == -1) {
            if(listener != null){
                listener.onFragmentSendMessage("Please enter all values.");
            }
        } else {

            int ageNum = Integer.valueOf(age);
            double weightNum = Double.valueOf(weight);
            double heightNum = Double.valueOf(height);
            String gender = "";

            if (selectedGenderId == maleRadioButton.getId()) {
                gender = "M";
            } else if (selectedGenderId == femaleRadioButton.getId()) {
                gender = "F";
            }

            String[] spinnerValues = getResources().getStringArray(R.array.spinner_values);
            double activity = Double.valueOf(spinnerValues[selectedActivityIndex]);

            Health health = new Health();

            int result = health.calculateCalorie(gender, ageNum, heightNum, weightNum, activity);

            if (result != -1) {
                Spannable spannable = new SpannableString("You need " + result + " calories/day to maintain your weight.");
                spannable.setSpan(new ForegroundColorSpan(Color.rgb(239, 106, 144)), 9, 9 + String.valueOf(result).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                if(listener != null){
                    listener.onFragmentSendMessage(spannable);
                }
            }
        }
    }

    OnFragmentSendMessageListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (OnFragmentSendMessageListener) context;
    }
}
