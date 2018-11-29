package be.ap.edu.aportage.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import be.ap.edu.aportage.R;

public class LokalenRecyclerAdapter extends RecyclerView.Adapter<LokalenRecyclerAdapter.ViewHolder> {

    private final Context context;
    private final List<Integer> lokalenList;
    private final LayoutInflater layoutInflater;

    public LokalenRecyclerAdapter(Context context, List<Integer> lokalenList) {
        this.context = context;
        this.lokalenList = lokalenList;
        this.layoutInflater = LayoutInflater.from(this.context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.listitem_lokaal, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        int lokaalNummer = this.lokalenList.get(i);
        viewHolder.verdiepTitel.setText(lokaalNummer);
    }

    @Override
    public int getItemCount() {
        return this.lokalenList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView verdiepTitel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            verdiepTitel = (TextView) itemView.findViewById(R.id.tv_verdiep_titel);
        }
    }
}
