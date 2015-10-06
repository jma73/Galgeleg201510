package dk.jpeace.jan.galgeleg;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentForside extends Fragment implements View.OnClickListener {

    private Button buttonStartSpil;

    public FragmentForside() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("FragmentForside", "fragmentet FragmentForside blev vist!");

        // Inflate the layout for this fragment

        View rod =  inflater.inflate(R.layout.fragment_forside, container, false);


        buttonStartSpil = (Button)rod.findViewById(R.id.buttonStartSpil);
        buttonStartSpil.setOnClickListener(this);


        TextView jjj = (TextView) rod.findViewById(R.id.textViewTest);
        jjj.setText("så er vi her!!! in the fragment....");

        return rod;
    }


    @Override
    public void onClick(View view) {

        Log.d("FragmentForside", "in onClick!");

        if(view == buttonStartSpil)
        {
            Log.d("FragmentForside", "Knappen buttonStartSpil blev trykket!");

            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentindhold, new FragmentSpillet())
                    .addToBackStack(null)
                    // .setTransition(8)
                    .commit();
        }
    }
}
