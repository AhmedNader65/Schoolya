package businessmonk.schoolsapp.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by one on 3/12/15.
 */
public class TextViewSubtitle extends TextView {

    public TextViewSubtitle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewSubtitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewSubtitle(Context context) {
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