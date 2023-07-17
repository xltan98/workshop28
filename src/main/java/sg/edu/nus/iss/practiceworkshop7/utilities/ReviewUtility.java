package sg.edu.nus.iss.practiceworkshop7.utilities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import sg.edu.nus.iss.practiceworkshop7.model.Review;

public class ReviewUtility {

    public static Review toReview(Document doc){
        Review r= new Review();
        List<String> review=(List<String>)doc.get("reviews");
        r.setReviews(review);
        r.setGid(doc.getInteger("game_id"));
        r.setName(doc.getString("name"));
        r.setYear(doc.getInteger("year"));
        r.setRanking(doc.getInteger("rank"));
        r.setUsersRated(doc.getInteger("users_rated"));
        r.setUrl(doc.getString("url"));
        r.setImage(doc.getString("thumbnail"));
        r.setTimestamp(LocalDateTime.now());

        return r;



    }
    
}
