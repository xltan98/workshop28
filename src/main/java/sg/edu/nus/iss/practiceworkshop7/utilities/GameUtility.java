package sg.edu.nus.iss.practiceworkshop7.utilities;

import java.util.List;

import org.bson.Document;

import sg.edu.nus.iss.practiceworkshop7.model.Game;

public class GameUtility {
 
    public static Game toGame(Document doc){
        return new Game(
            doc.getInteger("gid"),
            doc.getString("name"),
            doc.getInteger("year"),
            doc.getInteger("ranking"),
            doc.getInteger("users_rated"),
            doc.getString("url"),
            doc.getString("image")
        );
    }
}
