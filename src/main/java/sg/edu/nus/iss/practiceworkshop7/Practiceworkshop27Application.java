package sg.edu.nus.iss.practiceworkshop7;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sg.edu.nus.iss.practiceworkshop7.model.Comment;
import sg.edu.nus.iss.practiceworkshop7.model.Game;
import sg.edu.nus.iss.practiceworkshop7.model.Review;
import sg.edu.nus.iss.practiceworkshop7.repository.GameRepository;
import sg.edu.nus.iss.practiceworkshop7.service.GameService;
import sg.edu.nus.iss.practiceworkshop7.utilities.ReviewUtility;

@SpringBootApplication
public class Practiceworkshop27Application implements CommandLineRunner {
	@Autowired
	private GameRepository gRepo;

	@Autowired
	private GameService gServ;

	public static void main(String[] args) {
		SpringApplication.run(Practiceworkshop27Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// for(Game game:gServ.getGameListByName("samurai").get()){
		// 	System.out.printf(">>>>%s",game);
		// }

		// for(Comment game:gServ.getCommentByGid(1).get()){
		// 	System.out.printf(">>>>%s",game);
		// }
		 
        // System.out.println("Is game empty? " + gServ.getGameByGid(10000).isEmpty());

			List<Document> result=gRepo.getGameDetailsWithGid(1);
			for(Document doc:result){
			Review r=ReviewUtility.toReview(doc);
			System.out.printf(">>>>%s",r);
			}

			// for(Document doc:result){
			// 	System.out.printf(">>>>>%s\n",doc);
			// }
		
	}

}
