package businessmonk.schoolsapp.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by one on 3/12/15.
 */
public class TextViewContent extends TextView {

    public TextViewContent(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewContent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewContent(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Fonts/century-gothic.ttf");
            setTypeface(tf);
        }
    }

}