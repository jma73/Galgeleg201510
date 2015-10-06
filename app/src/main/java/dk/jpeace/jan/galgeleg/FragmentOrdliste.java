package dk.jpeace.jan.galgeleg;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOrdliste extends Fragment implements AdapterView.OnItemClickListener {


    public FragmentOrdliste() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rod = inflater.inflate(R.layout.fragment_ordliste, container, false);
        ListView listView = (ListView) rod.findViewById(R.id.listViewOrdliste);
        listView.setOnItemClickListener(this);
        listView.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, ForsideActivity.galgelogik.muligeOrd()));

        ShowMuligeOrdCount(rod);
        return rod;
    }

    private void ShowMuligeOrdCount(View rod) {
        int antalOrd = ForsideActivity.galgelogik.muligeOrd().size();
        TextView textViewListId = (TextView) rod.findViewById(R.id.textViewListId);
        textViewListId.setText("" + antalOrd);
    }


    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p/>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(getActivity(), "Klik på " + position + ", id=" + id, Toast.LENGTH_SHORT).show();

        ArrayList<String> ordliste = ForsideActivity.galgelogik.muligeOrd();

        String valgtOrd = ordliste.get((int) id);
        Log.d("FragmentOrdliste", "Valgt ord:" + id  + valgtOrd);

        ForsideActivity.galgelogik.nulstilMedSelvalgtOrd(valgtOrd);
    }
}
