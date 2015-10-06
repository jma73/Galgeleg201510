package dk.jpeace.jan.galgeleg;

import android.view.View;

/**
 * Created by b012914 on 05-10-2015.
 */
public class GenerelUtilities {

    /* /
     * Get the name of a View
     * */
    public static String getResourceName(View v) {
        return v.getResources().getResourceName(v.getId());
    }

}
