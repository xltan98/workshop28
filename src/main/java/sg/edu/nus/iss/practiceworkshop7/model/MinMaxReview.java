package sg.edu.nus.iss.practiceworkshop7.model;

import java.util.List;

import org.bson.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MinMaxReview {

    private String rating;
    private List<Document> reviewList;
    private String timestamp;

    
}
