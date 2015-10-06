package dk.jpeace.jan.galgeleg;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class FragmentSpilslut extends Fragment {

    private boolean erSpilletVundet;
    private String spilleTid;
    private TextView textViewSpilSlutTid;
    private TextView textViewSpilSlutResultat;
    private ImageView imageViewSpilSlut;

//    private OnFragmentInteractionListener mListener;

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @return A new instance of fragment FragmentSpilslut.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static FragmentSpilslut newInstance(String spilleTid, boolean erSpilletVundet) {
//        FragmentSpilslut fragment = new FragmentSpilslut();
//        Bundle args = new Bundle();
//        args.putBoolean(ER_SPIL_VUNDET, erSpilletVundet);
//        args.putString(ARG_PARAM2, spilleTid);
//
//        fragment.setArguments(args);
//        return fragment;
//    }

    public FragmentSpilslut() {
        // Required empty public constructor
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        if (getArguments() != null) {
//            erSpilletVundet = getArguments().getBoolean(ER_SPIL_VUNDET);
//            spilleTid = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rod =  inflater.inflate(R.layout.fragment_spilslut, container, false);


//        String spilStatus = intent.getStringExtra(GalgelejActivity.ER_SPIL_VUNDET);
//        long spilleTid = intent.getLongExtra(GalgelejActivity.SPILLE_TID, -1);


        textViewSpilSlutTid = (TextView) rod.findViewById(R.id.textViewSpilSlutTid);
        textViewSpilSlutResultat = (TextView) rod.findViewById(R.id.textViewSpilSlutResultat);
        imageViewSpilSlut = (ImageView) rod.findViewById(R.id.imageViewSpilSlut);



        if (getArguments() != null) {
            erSpilletVundet = getArguments().getBoolean(FragmentSpillet.ER_SPIL_VUNDET);
            spilleTid = getArguments().getString(FragmentSpillet.SPILLE_TID);
        }
        Log.d("fragment_spilslut", "onCreateView");
        Log.d("fragment_spilslut", String.valueOf(erSpilletVundet));
        Log.d("fragment_spilslut", String.valueOf(spilleTid));

        textViewSpilSlutTid.setText("Du brugte " + spilleTid + " sekunder.");
        if(erSpilletVundet)
            textViewSpilSlutTid.setText("Tillykke - du gættede ordet!");
        else
            textViewSpilSlutTid.setText("Bedre held næste gang!");

        GalgelegUITools.OpdaterGalgeBillede(imageViewSpilSlut, ForsideActivity.galgelogik.getAntalForkerteBogstaver());

        return rod;
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
