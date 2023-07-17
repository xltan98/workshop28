package sg.edu.nus.iss.practiceworkshop7.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import sg.edu.nus.iss.practiceworkshop7.model.Comment;
import sg.edu.nus.iss.practiceworkshop7.model.Game;
import sg.edu.nus.iss.practiceworkshop7.service.GameService;

@Controller
public class GameController {
    @Autowired 
    GameService gServ;

    @GetMapping("/")
    public String getGameName(){
        return"index";
        
    }

    @GetMapping("/search")
    public String getSearchResult(@RequestParam String gameName,Model model){
        Optional<List<Game>> gameList=gServ.getGameListByName(gameName);
        //System.out.printf(">>>%s",gameList);

        model.addAttribute("gameList", gameList.get());
        model.addAttribute("gameName",gameName);


        return "search";

    }

    @GetMapping("/game/{gid}")
    public String getGameByGid(@PathVariable Integer gid,Model model ){
        //get game by gid
        Optional<Game> game = gServ.getGameByGid(gid);
        // System.out.println("Is game empty? " + game.isEmpty());
       
        model.addAttribute("game", game.get());

        Optional<List<Comment>> commentList= gServ.getCommentByGid(gid);
        //post comment
        Comment newcomment = new Comment();
        // newcomment.setGid(gid);
        model.addAttribute("newComment",newcomment);
        // gServ.insertComment(null, gid);



        //list of comment

        model.addAttribute("commentList",commentList.get());

        System.out.printf(">>>>>%s\n",commentList);



        return "gamedetails";
    }

    @PostMapping("/post")
    public String postComment(@ModelAttribute Comment comment){
        gServ.insertComment(comment);
        Integer gid= comment.getGid();

        return "redirect:/game/"+gid;
    }
    
}
