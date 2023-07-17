package sg.edu.nus.iss.practiceworkshop7.repository;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.practiceworkshop7.model.Comment;

@Repository
public class CommentRepository {

    @Autowired
    MongoTemplate template;

    final private String F_GID="gid";
    final private String C_COMMENT="comment";
    final private String F_CID="c_id";
    final private String F_USER="user";
    final private String F_RATING="rating";
    final private String F_CTEXT="c_text";

 


    // db.comment.find({
    //     gid: 
    //           45986
        
    // })
    
    public Optional<List<Document>> getCommentByGid(Integer gid){
        Criteria criteria= Criteria.where(F_GID).is(gid);
        Query query = Query.query(criteria)
        .with(Sort.by(Direction.DESC,"rating"));

        List<Document> result = template.find(query,Document.class,C_COMMENT);

        if(result.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(result);

    }

    //db.comment.insert({})

    public void insertComment(Comment comment){
        Document doc= new Document();
        doc.put(F_CID,comment.getCId());
        doc.put(F_CTEXT,comment.getCText());
        doc.put(F_GID,comment.getGid());
        doc.put(F_RATING,comment.getRating());
        doc.put(F_USER,comment.getUser());

        Document newdoc= template.insert(doc,C_COMMENT);
    


    }
}
