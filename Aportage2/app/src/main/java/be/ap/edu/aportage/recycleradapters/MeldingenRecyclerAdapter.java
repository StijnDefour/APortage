package be.ap.edu.aportage.recycleradapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudinary.android.MediaManager;
import com.squareup.picasso.Picasso;

import java.util.List;

import be.ap.edu.aportage.R;
import be.ap.edu.aportage.models.Melding;

public class MeldingenRecyclerAdapter extends RecyclerView.Adapter<MeldingenRecyclerAdapter.ViewHolder> {


    private final Context context;
    private final LayoutInflater layoutInflater;

    public void setMeldingenList(List<Melding> meldingenList) {
        this.meldingenList = meldingenList;
    }

    private List<Melding> meldingenList;

    public MeldingenRecyclerAdapter(Context context, List<Melding> meldingenList) {
        this.context = context;
        this.meldingenList = meldingenList;
        this.layoutInflater = LayoutInflater.from(this.context);

        try {
            MediaManager.init(context);
        } catch (Exception e) {}
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
        viewHolder.melding_id = this.meldingenList.get(i)._id;
        viewHolder.locatie = melding.locatie;
        //todo load image from cloudinary
        String url = "https://res.cloudinary.com/dt6ae1zfh/image/upload/c_fit,w_150/meldingen/" + melding.getImgUrl() + ".jpg";
        Picasso.get().load(url).into(viewHolder.meldingFoto);
    }

    @Override
    public int getItemCount() {
        return this.meldingenList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView meldingFoto;
        private TextView meldingTitel;
        private TextView meldingBeschrijving;
        private FrameLayout meldingStatus;
        private String melding_id;
        private String[] locatie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            meldingFoto = (ImageView)itemView.findViewById(R.id.iv_melding_foto);
            meldingTitel = (TextView) itemView.findViewById(R.id.tv_melding_titel);
            meldingBeschrijving = (TextView) itemView.findViewById(R.id.tv_melding_beschrijving);
            meldingStatus = (FrameLayout) itemView.findViewById(R.id.fl_melding_status);
            registreerOnClickListener(itemView);
        }

        private void registreerOnClickListener(final View itemView) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, be.ap.edu.aportage.activities.Melding.class);
                    intent.putExtra("melding_titel", meldingTitel.getText());
                    intent.putExtra("melding_id", melding_id);

                    intent.putExtra(context.getResources().getString(R.string.campus_intent), locatie[0]);
                    intent.putExtra(context.getResources().getString(R.string.verdieping_intent), locatie[1]);
                    intent.putExtra(context.getResources().getString(R.string.lokaal_intent), locatie[2]);


                    context.startActivity(intent);
                    //((context.getClass()).finish();
                }
            });
        }
    };


    public void clearMeldingen() {
        int size = this.meldingenList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                meldingenList.remove(0);
            }
        }
    }

}
