package ir.technopedia.wordpressjsonclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.technopedia.wordpressjsonclient.model.PostModel;
import ir.technopedia.wordpressjsonclient.R;

/**
 * Created by TheLoneWolf on 9/3/2016.
 */
public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PostModel> mDataset;
    private Context context;

    public PostAdapter(Context context, List<PostModel> myDataset) {
        this.mDataset = myDataset;
        this.context = context;
    }

    /** * 渲染具体的ViewHolder
     * @param parent   ViewHolder的容器
     * @param viewType 一个标志，我们根据该标志可以实现渲染不同类型的ViewHolder
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == PostModel.YES_Thumbnail) {
            return new PostWithImgHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_post_img, parent, false));
        } else if (viewType == PostModel.NO_Thumbnail) {
            return new PostNoImgHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_post, parent, false));
        }
        return null;
    }

    /**
     * 决定元素的布局使用哪种类型
     * @param position 数据源的下标
     * @return 一个int型标志，传递给onCreateViewHolder的第二个参数 */
    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position).getType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder mholder, int position) {
        if (mholder instanceof PostWithImgHolder) {
            PostWithImgHolder holder = (PostWithImgHolder)mholder;
            holder.title.setText(mDataset.get(position).title);

            if (mDataset.get(position).categories.size() > 0) {
                holder.category.setVisibility(View.VISIBLE);
                holder.category.setText(mDataset.get(position).categories.get(0));
            } else {
                holder.category.setVisibility(View.GONE);
            }

            int count = mDataset.get(position).comment_count;
            if (!mDataset.get(position).comment_status.equals("open") && !(count > 0)) {
                holder.comments.setVisibility(View.GONE);
                holder.comments_img.setVisibility(View.GONE);
            } else {
                holder.comments.setVisibility(View.VISIBLE);
                holder.comments_img.setVisibility(View.VISIBLE);
                holder.comments.setText(count + "");
            }
            Picasso.with(context)
                    .load(mDataset.get(position).getThumbnailExt())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.img);
        } else if (mholder instanceof PostNoImgHolder) {
            PostNoImgHolder holder = (PostNoImgHolder)mholder;
            holder.title.setText(mDataset.get(position).title);

            if (mDataset.get(position).categories.size() > 0) {
                holder.category.setVisibility(View.VISIBLE);
                holder.category.setText(mDataset.get(position).categories.get(0));
            } else {
                holder.category.setVisibility(View.GONE);
            }

            int count = mDataset.get(position).comment_count;
            if (!mDataset.get(position).comment_status.equals("open") && !(count > 0)) {
                holder.comments.setVisibility(View.GONE);
                holder.comments_img.setVisibility(View.GONE);
            } else {
                holder.comments.setVisibility(View.VISIBLE);
                holder.comments_img.setVisibility(View.VISIBLE);
                holder.comments.setText(count + "");
            }
            Picasso.with(context)
                    .load(mDataset.get(position).getThumbnailExt())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.img);
        }
    }

    public void addItem(PostModel dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void update(List<PostModel> list) {
        mDataset = list;
        notifyDataSetChanged();
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class PostWithImgHolder extends RecyclerView.ViewHolder {
        TextView title, comments, category;
        ImageView comments_img, img;
        public PostWithImgHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            comments = (TextView) itemView.findViewById(R.id.comment_count);
            category = (TextView) itemView.findViewById(R.id.category);
            img = (ImageView) itemView.findViewById(R.id.img);
            comments_img = (ImageView) itemView.findViewById(R.id.comment_count_img);
        }
    }

    public static class PostNoImgHolder extends RecyclerView.ViewHolder {
        TextView title, comments, category;
        ImageView comments_img, img;
        public PostNoImgHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            comments = (TextView) itemView.findViewById(R.id.comment_count);
            category = (TextView) itemView.findViewById(R.id.category);
            img = (ImageView) itemView.findViewById(R.id.img);
            comments_img = (ImageView) itemView.findViewById(R.id.comment_count_img);
        }
    }
}