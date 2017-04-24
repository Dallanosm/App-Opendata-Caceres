package dallanosm.icopendataapp.roots;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dallanosm.icopendataapp.R;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    List<Site> items;

    public ListAdapter(List<Site> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;

        private final CardView card;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            card = (CardView) itemView.findViewById(R.id.card);
        }

        public void bind(Site site) {
            title.setText(site.getTitle());
            card.setBackgroundColor(site.getColor());
        }
    }
}
