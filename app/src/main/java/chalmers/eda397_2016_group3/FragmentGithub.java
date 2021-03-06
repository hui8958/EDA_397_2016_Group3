package chalmers.eda397_2016_group3;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.hudomju.swipe.OnItemClickListener;
import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.SwipeableItemClickListener;
import com.hudomju.swipe.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by N10 on 4/19/2016.
 */
public class FragmentGithub extends Fragment {


    ArrayList<String> list = new ArrayList<String>();

    public  List<String> lDataSet=null;
    EditText edit=null;
    /** Declaring an ArrayAdapter to set items to ListView */

    MyBaseAdapter adapter;

    private static final int TIME_TO_AUTOMATICALLY_DISMISS_ITEM = 1000;

    Context c;
    public static Fragment newInstance(Context context) {
        FragmentGithub f = new FragmentGithub();


        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.definition_of_done, null);

        Log.d("debugeditn10","view");

        final EditText edit=(EditText)root.findViewById(R.id.txtItem);
        Button btn = (Button) root.findViewById(R.id.btnAdd);


        init((RecyclerView) root.findViewById(R.id.recycler_view));
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                list.add(edit.getText().toString());
                adapter.updateList(list);
                adapter.notifyDataSetChanged();
                Log.d("debugeditn10", "asd");

            }
        });


        return root;
    }


    private void init(RecyclerView recyclerView) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
         adapter = new MyBaseAdapter(list);

        recyclerView.setAdapter(adapter);
        final SwipeToDismissTouchListener<RecyclerViewAdapter> touchListener =
                new SwipeToDismissTouchListener<>(
                        new RecyclerViewAdapter(recyclerView),
                        new SwipeToDismissTouchListener.DismissCallbacks<RecyclerViewAdapter>() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onPendingDismiss(RecyclerViewAdapter recyclerView, int position) {

                            }

                            @Override
                            public void onDismiss(RecyclerViewAdapter view, int position) {
                                adapter.remove(position);
                            }
                        });
        touchListener.setDismissDelay(TIME_TO_AUTOMATICALLY_DISMISS_ITEM);
        recyclerView.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        recyclerView.setOnScrollListener((RecyclerView.OnScrollListener) touchListener.makeScrollListener());
        recyclerView.addOnItemTouchListener(new SwipeableItemClickListener(getActivity().getBaseContext(),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (view.getId() == R.id.txt_delete) {
                            touchListener.processPendingDismisses();
                        } else if (view.getId() == R.id.txt_undo) {
                            touchListener.undoPendingDismiss();
                        } else { // R.id.txt_data
                            Toast.makeText(getActivity().getBaseContext(), "Position " + position, LENGTH_SHORT).show();
                        }
                    }
                }));
    }

    static class MyBaseAdapter extends RecyclerView.Adapter<MyBaseAdapter.MyViewHolder> {

        private static final int SIZE = 10;


        private  List<String> mDataSet = new ArrayList<>();

        MyBaseAdapter(List<String> dataSet) {

            mDataSet=dataSet;

        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.dataTextView.setText(mDataSet.get(position));
        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }

        public void updateList(List<String> dataSet) {

            this.mDataSet=dataSet;
        }

        public void remove(int position) {
            mDataSet.remove(position);
            notifyItemRemoved(position);
        }



        static class MyViewHolder extends RecyclerView.ViewHolder {

            TextView dataTextView;
            MyViewHolder(View view) {
                super(view);
                dataTextView = (TextView) view.findViewById(R.id.txt_data);
            }
        }
    }





}
