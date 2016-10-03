package businessmonk.schoolsapp.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by one on 3/12/15.
 */
public class EditTextContent extends EditText {

    public EditTextContent(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EditTextContent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextContent(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Fonts/calibri.ttf");
            setTypeface(tf);
        }
    }

}