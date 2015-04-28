package com.zachklipp.phrasestylingexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;
import android.widget.TextView;
import com.squareup.phrase.Phrase;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

public class MainActivity extends AppCompatActivity {
  private CharSequence pattern;
  private int aColor;
  private EditText aText;
  private EditText bText;
  private TextView output;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // getText() automatically parses tags and returns a SpannedString.
    pattern = getResources().getText(R.string.pattern);

    aText = (EditText) findViewById(R.id.a);
    bText = (EditText) findViewById(R.id.b);
    TextWatcher changeWatcher = new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Do nothing.
      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
        updateOutput();
      }

      @Override public void afterTextChanged(Editable s) {
        // Do nothing.
      }
    };
    aText.addTextChangedListener(changeWatcher);
    bText.addTextChangedListener(changeWatcher);

    aColor = getResources().getColor(R.color.a_color);
    output = (TextView) findViewById(R.id.output);

    updateOutput();
  }

  private void updateOutput() {
    CharSequence styledText = Phrase.from(pattern) //
        .put("a", colorText(aText.getText(), aColor)) //
        .put("b", bText.getText()) //
        .format();
    output.setText(styledText);
  }

  private CharSequence colorText(CharSequence text, int color) {
    Spannable colored = new SpannableString(text.toString());
    colored.setSpan(new ForegroundColorSpan(color), 0, text.length(), SPAN_INCLUSIVE_INCLUSIVE);
    return colored;
  }
}
