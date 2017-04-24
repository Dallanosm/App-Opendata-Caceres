package dallanosm.icopendataapp.roots;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import dallanosm.icopendataapp.R;

public class LeyendaAdapter extends RecyclerView.Adapter<LeyendaAdapter.ViewHolder> {

    List<LeyendaItem> items;

    public LeyendaAdapter(List<LeyendaItem> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leyenda_item, parent, false);
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

        private final LinearLayout icon;

        private final TextView title;

        public ViewHolder(View itemView) {
            super(itemView);

            icon = (LinearLayout) itemView.findViewById(R.id.icon);
            title = (TextView) itemView.findViewById(R.id.title);
        }

        public void bind(LeyendaItem item) {
            title.setText(item.getTitle());
            icon.setBackgroundColor(item.getColor());
        }
    }
}
