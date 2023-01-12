package com.example.inputcontrols;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    DateFormat fmtDateAndTime = DateFormat.getDateTimeInstance();
    Calendar myCalendar = Calendar.getInstance();
    ProgressDialog dialog;
    Handler pHandler;
    int progress;
    private static final int MAX_PROGRESS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edit_text = (EditText)findViewById(R.id.editText);
        //edit_text.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});

        // Get a reference to the AutoCompleteTextView in the layout
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocompleteView);
        // Get the string array
        String[] countries = getResources().getStringArray(R.array.countries_array);
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> auto_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        textView.setAdapter(auto_adapter);

        CheckBox checkbox_cheese = (CheckBox) findViewById(R.id.checkbox_cheese);
        checkbox_cheese.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final TextView txtView=(TextView)findViewById(R.id.textView1);
                if (isChecked){
                    txtView.setText(" Cheese ");
                }else{
                    txtView.setText(" No Cheese ");
                }
            }
        });
        CheckBox checkbox_meat = (CheckBox) findViewById(R.id.checkbox_meat);
        checkbox_meat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final TextView txtView=(TextView)findViewById(R.id.textView1);
                if (isChecked){
                    txtView.setText(" Meat ");
                }else{
                    txtView.setText(" No Meat ");
                }
            }
        });

        RadioButton radio_pirates = (RadioButton) findViewById(R.id.radio_pirates);
        radio_pirates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked();
                final TextView txtView=(TextView)findViewById(R.id.textView1);
                if (checked){
                    txtView.setText(" Pirates ! ");
                }
            }
        });
        RadioButton radio_ninjas = (RadioButton) findViewById(R.id.radio_ninjas);
        radio_ninjas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked();
                final TextView txtView=(TextView)findViewById(R.id.textView1);
                if (checked){
                    txtView.setText(" Ninjas ! ");
                }
            }
        });

        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TextView text1 =(TextView)findViewById(R.id.textView2);
                if (isChecked) {
                    text1.setText("Toggle ON");
                } else {
                    text1.setText("Toggle OFF");
                }
            }
        });

        SwitchCompat sw = (SwitchCompat) findViewById(R.id.switchButton);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TextView text1 =(TextView)findViewById(R.id.textView2);
                if (isChecked) {
                    text1.setText("Switch ON");
                } else {
                    text1.setText("Switch OFF");
                }
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.planets_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, R.layout.spinner_text);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinner.setAdapter(adapter);

        Button btnDate = (Button) findViewById(R.id.btnChangeDate);
        btnDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, d,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Button btnTime = (Button) findViewById(R.id.btnChangeTime);
        btnTime.setOnClickListener(new View.OnClickListener() {
            public  void onClick(View v) {
                new TimePickerDialog(MainActivity.this, t,
                        myCalendar.get(Calendar.HOUR_OF_DAY),
                        myCalendar.get(Calendar.MINUTE), true).show();
            }
        });

        updateLabel();

        final TextView txtView = (TextView) findViewById(R.id.textRatingValue);
        final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        final Button btnShow = (Button) findViewById(R.id.btnShow);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                txtView.setText(String.valueOf(rating));
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,String.valueOf(ratingBar.getRating()),
                        Toast.LENGTH_SHORT).show();
            }
        });

        final Button progress_button = (Button) findViewById(R.id.progressBar);
        progress_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setTitle("Title");
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.setMax(MAX_PROGRESS);
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Hide",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,	int whichButton) {
                                /* User clicked Yes so do some stuff */
                            }
                        });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,	int whichButton) {
                                /* User clicked No so do some stuff */
                            }
                        });
                progress = 0;
                dialog.show();
                dialog.setProgress(0);
                pHandler.sendEmptyMessage(0);
            }
        });

        pHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (progress >= MAX_PROGRESS) {
                    dialog.dismiss();
                } else {
                    progress=progress+5;
                    dialog.incrementProgressBy(5);
                    pHandler.sendEmptyMessageDelayed(0, 100);
                }
            }
        };

    }//end onCreate()

/*
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        final TextView txtView=(TextView)findViewById(R.id.textView1);

        switch(view.getId()) {
            case R.id.checkbox_meat:
                if (checked){
                    txtView.setText(" Meat ");
                }else{
                    txtView.setText(" No Meat ");
                }
                break;
            case R.id.checkbox_cheese:
                if (checked){
                    txtView.setText(" Cheese ");
                }else{
                    txtView.setText(" No Cheese ");
                }
                break;
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        final TextView txtView=(TextView)findViewById(R.id.textView1);

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_pirates:
                if (checked)
                    txtView.setText(" Pirates ! ");
                break;
            case R.id.radio_ninjas:
                if (checked)
                    txtView.setText(" Ninjas ! ");
                break;
        }
    }

    public void onToggleClicked(View view) {
        boolean on = ((ToggleButton) view).isChecked();
        TextView text1 =(TextView)findViewById(R.id.textView2);

        if (on) {
            text1.setText("Toggle ON");
        } else {
            text1.setText("Toggle OFF");
        }
    }
*/
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendar.set(Calendar.MINUTE, minute);
            updateLabel();
        }
    };

    private void updateLabel() {
        TextView text2 =(TextView)findViewById(R.id.textView2);
        text2.setText(fmtDateAndTime.format(myCalendar.getTime()));
    }

}

class DecimalDigitsInputFilter implements InputFilter {
    private Pattern mPattern;
    DecimalDigitsInputFilter(int digits, int digitsAfterZero) {
        mPattern = Pattern.compile("[0-9]{0," + (digits - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?");
    }
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher matcher = mPattern.matcher(dest);
        if (!matcher.matches())
            return "";
        return null;
    }
}