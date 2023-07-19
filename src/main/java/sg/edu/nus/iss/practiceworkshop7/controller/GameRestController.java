package sg.edu.nus.iss.practiceworkshop7.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.edu.nus.iss.practiceworkshop7.model.MinMaxReview;
import sg.edu.nus.iss.practiceworkshop7.model.Review;
import sg.edu.nus.iss.practiceworkshop7.service.GameService;
import sg.edu.nus.iss.practiceworkshop7.utilities.ReviewUtility;

@RestController
public class GameRestController {
    @Autowired
    GameService gServ;

    @GetMapping ("/game/{gid}/reviews")
    public ResponseEntity<Review> getReview(@PathVariable Integer gid){
        Optional<Review> r=gServ.getAGameByGid(gid);

        return ResponseEntity.ok().body(r.get());

    }
    @GetMapping("/highest")
    public ResponseEntity<String> minmax(String user){
        JsonObject result=getResultFromRatedComment(user,"highest");

        return ResponseEntity.ok().body(result.toString());
        
    }

    private JsonObject getResultFromRatedComment(String user,String rating){
        List<Document> docList=gServ.minmax(user, rating);

        JsonObjectBuilder b = Json.createObjectBuilder();

        MinMaxReview rr = new MinMaxReview();
        rr.setRating(rating);
        rr.setTimestamp(LocalDateTime.now().toString());
        rr.setReviewList(docList);

        b.add("result",ReviewUtility.toJSON(rr));
        JsonObject result=b.build();
        return result;
    }
    


    
}
