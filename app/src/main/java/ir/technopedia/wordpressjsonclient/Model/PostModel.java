package ir.technopedia.wordpressjsonclient.model;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user1 on 10/5/2016.
 */

public class PostModel extends SugarRecord {
    public static final int NO_Thumbnail = 1000;
    public static final int YES_Thumbnail = 2000;

    public int id, comment_count;
    public String url, title, content, date, author, comment_status, status, thumbnail, thumbnail_medium;
    public List<String> categories;
    public List<CommentModel> comments;
    public JSONArray categoryjsonlist, commentjsonlist;
    private int Type;

    public PostModel() {
        id = -1;
        url = title = content = date = author = comment_status = status = "";
        categories = new ArrayList<>();
        comments = new ArrayList<>();
    }

    public String getThumbnailExt() {
        // 显示图片,优先显示thumbnail_medium
        if (thumbnail_medium != null) return thumbnail_medium;
        else if(thumbnail != null) return thumbnail;
        else return null;
    }

    public int getType() {
        if (getThumbnailExt() == null) return NO_Thumbnail;
        else return YES_Thumbnail;
    }

    public void fromJson(JSONObject jsonObject) {
        try {
            id = jsonObject.getInt("id");
            url = jsonObject.getString("url");
            title = jsonObject.getString("title");
            status = jsonObject.getString("status");
            content = jsonObject.getString("content");
            date = jsonObject.getString("date");
            for (int i = 0; i < jsonObject.getJSONArray("categories").length(); i++) {
                categories.add(jsonObject.getJSONArray("categories").getJSONObject(i).getString("title"));
            }
            author = jsonObject.getJSONObject("author").getString("nickname");
            for (int i = 0; i < jsonObject.getJSONArray("comments").length(); i++) {
                CommentModel commentModel = new CommentModel();
                commentModel.fromJson(jsonObject.getJSONArray("comments").getJSONObject(i));
                comments.add(commentModel);
            }
            comment_count = jsonObject.getInt("comment_count");
            comment_status = jsonObject.getString("comment_status");
            thumbnail = jsonObject.getString("thumbnail");              // 缩略图[150x150]
            try {
                thumbnail_medium = jsonObject.getJSONObject("thumbnail_images").getJSONObject("medium").getString("url");   // 缩略图[300x300]
            } catch (JSONException e) {}
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("url", url);
            jsonObject.put("title", title);
            jsonObject.put("status", status);
            jsonObject.put("content", content);
            jsonObject.put("date", date);

            categoryjsonlist = new JSONArray();
            for (int i = 0; i < categories.size(); i++) {
                categoryjsonlist.put(new JSONObject().put("title", categories.get(i)));
            }
            jsonObject.put("categories", categoryjsonlist);

            JSONObject authorobj = new JSONObject();
            authorobj.put("nickname", author);
            jsonObject.put("author", authorobj);

            commentjsonlist = new JSONArray();
            for (int i = 0; i < comments.size(); i++) {
                commentjsonlist.put(comments.get(i).toJson());
            }
            jsonObject.put("comments", commentjsonlist);

            jsonObject.put("comment_count", comment_count);
            jsonObject.put("comment_status", comment_status);
            jsonObject.put("thumbnail", thumbnail);
            jsonObject.put("thumbnail_medium", thumbnail_medium);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
