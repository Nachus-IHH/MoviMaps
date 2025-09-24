package com.example.movimaps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.FaqViewHolder> {

    private List<FaqItem> faqItems;

    public FaqAdapter(List<FaqItem> faqItems) {
        this.faqItems = faqItems;
    }

    @NonNull
    @Override
    public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_faq, parent, false);
        return new FaqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaqViewHolder holder, int position) {
        FaqItem faqItem = faqItems.get(position);
        holder.bind(faqItem);
    }

    @Override
    public int getItemCount() {
        return faqItems.size();
    }

    public class FaqViewHolder extends RecyclerView.ViewHolder {
        private TextView tvQuestion;
        private TextView tvAnswer;

        public FaqViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            tvAnswer = itemView.findViewById(R.id.tvAnswer);
        }

        public void bind(FaqItem faqItem) {
            tvQuestion.setText(faqItem.getQuestion());
            tvAnswer.setText(faqItem.getAnswer());

            // Toggle answer visibility
            tvAnswer.setVisibility(faqItem.isExpanded() ? View.VISIBLE : View.GONE);

            itemView.setOnClickListener(v -> {
                faqItem.setExpanded(!faqItem.isExpanded());
                tvAnswer.setVisibility(faqItem.isExpanded() ? View.VISIBLE : View.GONE);
            });
        }
    }
}

