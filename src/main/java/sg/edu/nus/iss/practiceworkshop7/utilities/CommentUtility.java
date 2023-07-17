package sg.edu.nus.iss.practiceworkshop7.utilities;

import org.bson.Document;

import sg.edu.nus.iss.practiceworkshop7.model.Comment;

public class CommentUtility {
    public static Comment toComment(Document doc){
        
        return new Comment(doc.getString("c_id"), doc.getString("user"),doc.getInteger("rating"),doc.getString("c_text"),doc.getInteger("gid"));
    }
    
}
