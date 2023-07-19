package sg.edu.nus.iss.practiceworkshop7.repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.attoparser.dom.Comment;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation.AddFieldsOperationBuilder;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import sg.edu.nus.iss.practiceworkshop7.model.Game;

@Repository
public class GameRepository {
    @Autowired
    private MongoTemplate template;
    final String F_NAME="name";
    final String F_GID="gid";
    final String C_GAME="game";

    // db.game.find({
    //     name: {
    //         $regex:"samurai",
    //         $options: "i"
    //     }
    // })


    
    public Optional<List<Document>> findGameByName(String name){

        Criteria criteria = Criteria.where(F_NAME)
                                    .regex(name,"i");
        
        Query query = Query.query(criteria);

        List<Document> result= template.find(query,Document.class,C_GAME);
        if(result.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(result);
        
    }

    // db.game.find({
    //     gid: 
    //           45986
        
    // })

    public List<Document> getGameByGid(Integer gid){
        Criteria criteria= Criteria.where(F_GID).is(gid);
        Query query = Query.query(criteria);

        List<Document> result = template.find(query,Document.class,C_GAME);

        return result;

    }

//     printjson(
// db.game.aggregate([
// { $match:{gid:1}},
// {$lookup:{
//     from:'comment',
//     foreignField:'gid',
//     localField:"gid",
//     as:'comment_details'
// }},
// {$project:{game_id:"$gid",_id:0,name:1,year:1,rank:1,users_rated:1,url:1,thumbnail:'$image',reviews:'$comment_details.c_id',timestamp:'$$NOW',average: { $avg: "$users_rated" }}}
// ])
// )

// public Optional<Game> aggregateGameReview(Integer gid){
//     MatchOperation matchgameId = Aggregation.match(
//         Criteria.where("gid").is(gid)
//     );

//     LookupOperation lk= Aggregation.lookup("comment","gid","gid","comment_details");

//     ProjectionOperation pop= Aggregation.project("name","year","ranking","url");
// }


    public List<Document> getGameDetailsWithGid(Integer gid) {
        AggregationOperation match = Aggregation.match(Criteria.where("gid").is(gid));
        
        AggregationOperation lookup = Aggregation.lookup("comment", "gid", "gid", "comment_details");
        
        AggregationOperation project = Aggregation.project()
            .and("gid").as("game_id")
            .andExclude("_id")
            .and("name").as("name")
            .and("year").as("year")
            .and("ranking").as("rank")
            .and("users_rated").as("users_rated")
            .and("url").as("url")
            .and("image").as("thumbnail")
            
            .and(ArithmeticOperators.Divide.valueOf("users_rated").divideBy(1L)).as("average")
        
             .and(
                new AggregationExpression() {
                    @Override
                    public Document toDocument(AggregationOperationContext context) {
                        return new Document(
                            "$map", new Document()
                                .append("input", "$comment_details")
                                .append("as", "comment")
                                .append("in", new Document("$concat", Arrays.asList("review/", new Document("$toString", "$$comment.c_id"))))
                        );
                    }
                }
            ).as("reviews");

            AddFieldsOperationBuilder a = Aggregation.addFields();
        a.addFieldWithValue("timestamp", LocalDateTime.now());
        AddFieldsOperation newFieldOp = a.build();
           
        Aggregation aggregation = Aggregation.newAggregation(match, lookup, project,newFieldOp);
        AggregationResults<Document> result= template.aggregate(aggregation,"game", Document.class);

        return result.getMappedResults();
    }

//     printjson(
//   db.comment.aggregate([
//     {
//       $match: {
//         $and: [
//           { user: "PAYDIRT" },
//           { rating: { "$gt": 5 } }
//         ]
//       }
//     },
//     {
//       $lookup: {
//         from: "game",
//         localField: "gid",
//         foreignField: "gid",
//         as: "gameReviews"
//       }
//     },
//     {
//       $project: {
//         _id: 0,
//         c_id: 1,
//         user: 1,
//         rating: 1,
//         c_text: 1,
//         gid: 1,
//         game_name: { $arrayElemAt: ["$gameReviews.name", 0] }
//       }
//     }
//   ])
// );

public List<Document> aggegratesMinMaxGameReviews(String username,String rating){
    Criteria c= null;
    if(rating.toLowerCase().equals("highest")){
        c= new Criteria().andOperator(
            Criteria.where("user").is(username),
            Criteria.where("rating").gte(5));}
            
            else{
            c= new Criteria().andOperator(
                Criteria.where("user").is(username),
                Criteria.where("rating").lte(4)
            );

        }

        MatchOperation mOP= Aggregation.match(c);

        LookupOperation lOp= Aggregation.lookup("game","gid","gid","gameComment");

        ProjectionOperation pOP = Aggregation.project("_id","c_id","user","rating","c_text","gid").and("gameComment.name").as("game_name");

        LimitOperation limitOp = Aggregation.limit(50);
        
        // AddFieldsOperationBuilder a = Aggregation.addFields();
        // a.addFieldWithValue("timestamp", LocalDateTime.now());
        // a.addFieldWithValue("ratinglh",rating);
        // AddFieldsOperation newFieldOp = a.build();

        Aggregation pipeline= Aggregation.newAggregation(mOP,lOp,pOP,limitOp);

        AggregationResults<Document> comment =template.aggregate(pipeline,"comment",Document.class);

        return comment.getMappedResults();


    }

}







    



