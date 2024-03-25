package edu.iu.c322.midterm.controllers;

import edu.iu.c322.midterm.model.Question;
import edu.iu.c322.midterm.model.Quiz;
import edu.iu.c322.midterm.repository.FileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/quizzes")
public class QuizController {

    private FileRepository fileRepository;
    public QuizController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    @PostMapping
    public int addQuiz(@RequestBody Quiz quiz){
        try{
            return fileRepository.addQuiz(quiz);
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public List<Quiz> findAllQuiz(){
        try{
            return fileRepository.findAllQuiz();
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/{id}")
    public Quiz findQuiz(@PathVariable int id){
        try{
            Quiz q = fileRepository.findQuiz(id);
            if(q == null){
                throw new ObjNotFoundEx(id);
            }
            return q;
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public int updateQuiz(@PathVariable int id, @RequestBody Quiz quiz) {
        try{
            int idr = fileRepository.findAndReplace(id, quiz);
            if(idr == -1){
                throw new ObjNotFoundEx(idr);
            }
            return idr;
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Could not find object with id")
    public static class ObjNotFoundEx extends RuntimeException{
        ObjNotFoundEx(int id){
            super("Could Not Find Quiz with id " + id);
        }

        public String getMessage(){
            return super.getMessage();
        }
    }
}
