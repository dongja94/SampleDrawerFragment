package com.example.dongja94.sampledrawerfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnMenuItemSelectedListener} interface
 * to handle interaction events.
 */
public class MenuFragment extends Fragment {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MenuFragment.MENU_ID_MAIN, MenuFragment.MENU_ID_SUB1, MenuFragment.MENU_ID_SUB2})
    public @interface MenuMode {}

    private OnMenuItemSelectedListener mListener;
    public static final int MENU_ID_MAIN = 1;
    public static final int MENU_ID_SUB1 = 2;
    public static final int MENU_ID_SUB2 = 3;

    private static final MenuItem[] MENU_ITEM = {new MenuItem(MENU_ID_MAIN, "Main"), new MenuItem(MENU_ID_SUB1, "sub1"),
            new MenuItem(MENU_ID_SUB2, "sub2")};

    ListView listView;
    ArrayAdapter<MenuItem> mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ArrayAdapter<MenuItem>(getContext(), android.R.layout.simple_list_item_1, MENU_ITEM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        listView = (ListView)view.findViewById(R.id.listView);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuItem item = (MenuItem)listView.getItemAtPosition(position);
                selectMenu(item.id);
            }
        });
        return view;
    }

    public void selectMenu(@MenuMode int menuId) {
        if (mListener != null) {
            mListener.onMenuItemSelected(menuId);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMenuItemSelectedListener) {
            mListener = (OnMenuItemSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMenuItemSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnMenuItemSelectedListener {
        void onMenuItemSelected(@MenuMode int menuId);
    }

    public static class MenuItem {
        String title;
        int id;
        public MenuItem(int id, String title) {
            this.title = title;
            this.id = id;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
