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

public class VerdiepingenRecyclerAdapter extends RecyclerView.Adapter<VerdiepingenRecyclerAdapter.ViewHolder>{

    private final Context context;
    private final List<Verdiep> verdiepLijst;
    private final LayoutInflater layoutInflater;

    public VerdiepingenRecyclerAdapter(Context context, List<Verdiep> verdiepLijst) {
        this.context = context;
        this.verdiepLijst = verdiepLijst;
        this.layoutInflater = LayoutInflater.from(this.context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewItem = this.layoutInflater.inflate(R.layout.cv_verdiep_item, viewGroup, false);
        return new ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Verdiep verdiep = this.verdiepLijst.get(i);
        viewHolder.verdiepNummer.setText(String.format("%02d", verdiep.verdiepnr ));
    }

    @Override
    public int getItemCount() {
        return this.verdiepLijst.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView verdiepNummer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.verdiepNummer = (TextView)itemView.findViewById(R.id.tv_verdiep_nummer);
        }
    }
}
