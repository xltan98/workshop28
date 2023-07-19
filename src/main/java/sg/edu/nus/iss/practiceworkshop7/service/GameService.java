package sg.edu.nus.iss.practiceworkshop7.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.practiceworkshop7.exception.ResourceNotFoundException;
import sg.edu.nus.iss.practiceworkshop7.model.Comment;
import sg.edu.nus.iss.practiceworkshop7.model.Game;
import sg.edu.nus.iss.practiceworkshop7.model.Review;
import sg.edu.nus.iss.practiceworkshop7.repository.CommentRepository;
import sg.edu.nus.iss.practiceworkshop7.repository.GameRepository;
import sg.edu.nus.iss.practiceworkshop7.utilities.CommentUtility;
import sg.edu.nus.iss.practiceworkshop7.utilities.GameUtility;
import sg.edu.nus.iss.practiceworkshop7.utilities.ReviewUtility;

@Service
public class GameService {
    @Autowired
    GameRepository gRepo;

    @Autowired 
    CommentRepository cRepo;

    public Optional<List<Game>> getGameListByName(String name){
        Optional<List<Document>>result=gRepo.findGameByName(name);
        List <Game> gameList=new ArrayList<>();

        for(Document doc:result.get()){
            Game game = GameUtility.toGame(doc);
            gameList.add(game);
        }

        if(result.isEmpty()){



        return Optional.empty();
        }

        return Optional.of(gameList);

    }

    public Optional<Game> getGameByGid(Integer gid){
        List<Document> docs=gRepo.getGameByGid(gid);
        List<Game> games=new ArrayList<>();

       

        for(Document doc:docs){
           Game game= GameUtility.toGame(doc);
           games.add(game);
        }

         if(games.isEmpty()){            
            throw new ResourceNotFoundException("Game gid does not exist");
            // return Optional.empty();
            
        }
            return Optional.of(games.get(0));
        }
    

        
    
    public Optional<List<Comment>>getCommentByGid(Integer gid){
        Optional<List<Document>> docs=cRepo.getCommentByGid(gid);
        List<Comment> commentList= new ArrayList<>();
        if(docs.isEmpty()){
            return Optional.empty();
        }

        for(Document doc:docs.get()){
            Comment comment=CommentUtility.toComment(doc);
            commentList.add(comment);


        }

        return Optional.of(commentList);


    }

    public void insertComment(Comment comment){
        comment.setCId(UUID.randomUUID().toString().substring(0,9));
        ObjectId objId=cRepo.insertComment(comment);
    }

    public Optional<Review> getAGameByGid(Integer gid){
        	List<Document> result=gRepo.getGameDetailsWithGid(gid);
            List<Review> gameList= new ArrayList<>();
            
			for(Document doc:result){
			Review r=ReviewUtility.toReview(doc);
            gameList.add(r);
			
			}

           Review optReview= gameList.get(0);

           if(gameList.isEmpty()){
            return Optional.empty();
           }
           return Optional.of(optReview);
    }

    public List<Document> minmax(String user,String rating){
        return gRepo.aggegratesMinMaxGameReviews(user, rating);
    }
    
}
