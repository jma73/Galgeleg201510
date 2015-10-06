package dk.jpeace.jan.galgeleg;

import android.util.Log;
import android.widget.ImageView;

/**
 * Created by b012914 on 30-09-2015.
 */
public class GalgelegUITools {


    public static void OpdaterGalgeBillede(ImageView imageViewetFS, int antalForkerteBogstaver)
    {
        int antalForkerte = antalForkerteBogstaver;
        Log.v("jj", (String.valueOf(antalForkerte)));
        switch (antalForkerte)
        {
            case 0:
                imageViewetFS.setImageResource(R.drawable.galge);
                break;
            case 1:
                imageViewetFS.setImageResource(R.drawable.forkert1);
                break;
            case 2:
                imageViewetFS.setImageResource(R.drawable.forkert2);
                break;
            case 3:
                imageViewetFS.setImageResource(R.drawable.forkert3);
                break;
            case 4:
                imageViewetFS.setImageResource(R.drawable.forkert4);
                break;
            case 5:
                imageViewetFS.setImageResource(R.drawable.forkert5);
                break;
            case 6:
                imageViewetFS.setImageResource(R.drawable.forkert6);
                break;
            default:
                imageViewetFS.setImageResource(R.drawable.forkert6);
        }
    }
}
