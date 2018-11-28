package be.ap.edu.aportage.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import be.ap.edu.aportage.R;

//import static android.support.v4.graphics.drawable.IconCompat.getResources;


public class MeldingenRecyclerAdapter extends RecyclerView.Adapter<MeldingenRecyclerAdapter.ViewHolder> {


    private final Context context;
    private final LayoutInflater layoutInflater;
    private final List<Melding> meldingenList;

    public MeldingenRecyclerAdapter(Context context, List<Melding> meldingenList) {
        this.context = context;
        this.meldingenList = meldingenList;
        this.layoutInflater = LayoutInflater.from(this.context);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.cv_melding_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Melding melding = this.meldingenList.get(i);
        viewHolder.meldingTitel.setText(melding.titel);
        viewHolder.meldingBeschrijving.setText(melding.omschrijving);
        //viewHolder.meldingStatus.setBackgroundColor(this.context.getResources().getInteger(melding.getKleurInt()));


    }

    @Override
    public int getItemCount() {
        return this.meldingenList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView meldingFoto;
        public TextView meldingTitel;
        public TextView meldingBeschrijving;
        public FrameLayout meldingStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            meldingFoto = (ImageView)itemView.findViewById(R.id.iv_melding_foto);
            meldingTitel = (TextView) itemView.findViewById(R.id.tv_melding_titel);
            meldingBeschrijving = (TextView) itemView.findViewById(R.id.tv_melding_beschrijving);
            meldingStatus = (FrameLayout) itemView.findViewById(R.id.fl_melding_status);
        }
    }
}
