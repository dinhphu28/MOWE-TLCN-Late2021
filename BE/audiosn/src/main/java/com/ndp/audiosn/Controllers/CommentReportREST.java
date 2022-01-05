package com.ndp.audiosn.Controllers;

import java.util.ArrayList;
import java.util.List;

import com.ndp.audiosn.Entities.Article;
import com.ndp.audiosn.Entities.Comment;
import com.ndp.audiosn.Entities.CommentReport;
import com.ndp.audiosn.Models.CommentReport.CommentReportCreateModel;
import com.ndp.audiosn.Models.CommentReport.CommentReportItemModel;
import com.ndp.audiosn.Models.CommentReport.CommentReportUpdateModel;
import com.ndp.audiosn.Services.ArticleService;
import com.ndp.audiosn.Services.CommentReportService;
import com.ndp.audiosn.Services.CommentService;
import com.ndp.audiosn.Utils.DateTime.MyDateTimeUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/comment-reports")
public class CommentReportREST {
    @Autowired
    private CommentReportService commentReportService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private MyDateTimeUtils myDateTimeUtils;

    @GetMapping(
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> retrieveAllCommentReport(@RequestParam(value = "solved", required = true) Boolean solved) {
        ResponseEntity<Object> entity;

        List<CommentReport> commentReports = commentReportService.retrieveBySolved(solved);

        List<CommentReportItemModel> commentReportItemModels = new ArrayList<CommentReportItemModel>();

        for(CommentReport item : commentReports) {
            Comment tmpComment = commentService.retrieveOne(item.getCommentId());

            Article tmpArticle = articleService.retrieveOne(tmpComment.getArticleId());

            CommentReportItemModel tmpCRIModel = new CommentReportItemModel(item.getId(), item.getCommentId(), item.getAuthor(),
                item.getDate(), item.getTime(), item.getContent(), tmpComment.getAuthor(), tmpComment.getContent(),
                tmpArticle.getTitle(), tmpArticle.getUrl(), item.getSolved());

            commentReportItemModels.add(tmpCRIModel);
        }

        entity = new ResponseEntity<>(commentReportItemModels, HttpStatus.OK);

        return entity;
    }

    @GetMapping(
        value = "/{commentId}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> retrieveAllByCommentId(@PathVariable("commentId") Integer commentId) {
        ResponseEntity<Object> entity;

        List<CommentReport> commentReports = commentReportService.retrieveAllByCommentId(commentId);

        entity = new ResponseEntity<>(commentReports, HttpStatus.OK);

        return entity;
    }

    @PostMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> createNewReport(@RequestBody CommentReportCreateModel commentReport) {
        ResponseEntity<Object> entity;

        if(commentReport.getCommentId() == null || commentReport.getAuthor() == null || commentReport.getContent() == null) {

            entity = new ResponseEntity<>("{ \"Notice\": \"Failed\" }", HttpStatus.BAD_REQUEST);

        } else {
            CommentReport tmpCR = commentReport.toCommentReport(myDateTimeUtils.getCurrentDate(), myDateTimeUtils.getCurrentTime());

            CommentReport tmpSaved = commentReportService.createOne(tmpCR);

            if(tmpSaved == null) {
                entity = new ResponseEntity<>("{ \"Notice\": \"Failed\" }", HttpStatus.BAD_REQUEST);
            } else {
                entity = new ResponseEntity<>(tmpSaved, HttpStatus.CREATED);
            }
        }

        return entity;
    }

    @PutMapping(
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> markSolvedOrUnsolved(@PathVariable("id") Integer id, @RequestBody CommentReportUpdateModel commentReportUpdateModel) {
        ResponseEntity<Object> entity;

        CommentReport tmpToSave = commentReportService.retrieveOne(id);

        if(tmpToSave == null) {
            entity = new ResponseEntity<>("{ \"Notice\": \"Not found\" }", HttpStatus.NOT_FOUND);
        } else {
            tmpToSave.setSolved(commentReportUpdateModel.getSolved());

            CommentReport tmpSaved = commentReportService.updateOne(tmpToSave);

            if(tmpSaved == null) {
                entity = new ResponseEntity<>("{ \"Notice\": \"Failed\" }", HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                entity = new ResponseEntity<>(tmpSaved, HttpStatus.OK);
            }
        }

        return entity;
    }
}
