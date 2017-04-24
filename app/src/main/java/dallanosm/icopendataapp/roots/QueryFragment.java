package dallanosm.icopendataapp.roots;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dallanosm.icopendataapp.R;

public class QueryFragment extends Fragment {

    private List<LeyendaItem> items;

    private String titleValue;

    private String queryValue;

    private TextView title;

    private RecyclerView list;

    private TextView query;

    private SwipeRefreshLayout swipeLayout;

    public static QueryFragment newInstance(String title, String query, List<LeyendaItem> items) {
        QueryFragment fragment = new QueryFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("query", query);
        args.putParcelableArrayList("items", (ArrayList<? extends Parcelable>) items);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.items = getArguments().getParcelableArrayList("items");
        this.titleValue = getArguments().getString("title");
        this.queryValue = getArguments().getString("query");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_query, container, false);
        this.title = (TextView) view.findViewById(R.id.title);
        this.query = (TextView) view.findViewById(R.id.query);
        this.list = (RecyclerView) view.findViewById(R.id.list);
        this.swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title.setText(titleValue);
        query.setText(queryValue);

        LeyendaAdapter adapter = new LeyendaAdapter(items);
        list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        list.setAdapter(adapter);

        this.swipeLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Testing", Toast.LENGTH_SHORT).show();
                        swipeLayout.setRefreshing(false);
                    }
                }, 3000);

            }
        });

    }

}
